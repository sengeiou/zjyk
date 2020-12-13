package com.lys.app.controller;

import com.lys.app.AppApplication;
import com.lys.app.manager.OssUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.protobuf.SNetPicInfo;
import com.lys.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController
{
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath = null;

	@GetMapping("/page")
	public String uploadpage()
	{
		return "jsp/upload";
	}

	@PostMapping("/uploadstr")
	@ResponseBody
	public Map<String, Object> uploadstr(String filestr, String path, String type)
	{
		if (AppApplication.stop || AppApplication.startTime == 0)
			return result(0, null, "服务器正在维护");
		if (!StringUtils.isEmpty(filestr))
		{
			LOG.v(this.getClass().getSimpleName() + String.format(" uploadstr %s ...", path));
			try
			{
				File dstFile = new File(uploadPath, path);
				File dstDir = dstFile.getParentFile();
				if (!dstDir.exists())
					dstDir.mkdirs();
				if ("base64".equals(type))
					FsUtils.writeBytes(dstFile, CommonUtils.base64Decode(filestr));
				else
					FsUtils.writeText(dstFile, filestr);
				return result(200, path, "上传成功");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result(0, null, "上传失败");
	}

	@PostMapping("/upload")
	@ResponseBody
	public Map<String, Object> upload(Part file, String path)
	{
		if (AppApplication.stop || AppApplication.startTime == 0)
			return result(0, null, "服务器正在维护");
		if (!StringUtils.isEmpty(file.getSubmittedFileName()))
		{
			if (StringUtils.isEmpty(path))
				path = file.getSubmittedFileName();
			LOG.v(this.getClass().getSimpleName() + String.format(" upload %s ...", path));
			try
			{
				File dstFile = new File(uploadPath, path);
				File dstDir = dstFile.getParentFile();
				if (!dstDir.exists())
					dstDir.mkdirs();
				file.write(dstFile.toString());
				return result(200, path, "上传成功");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result(0, null, "上传失败");
	}

	private static final Object lock = new Object();

	private List<SNetPicInfo> netPics = null;
	private Map<String, SNetPicInfo> netPicMap = new HashMap<>();

	@PostMapping("/cloud")
	@ResponseBody
	public Map<String, Object> upload(Part file, String md5, String suffix)
	{
		if (AppApplication.stop || AppApplication.startTime == 0)
			return result(0, null, "服务器正在维护");
		if (!StringUtils.isEmpty(file.getSubmittedFileName()))
		{
			File dstFile = new File(uploadPath, "/插图/云空间/" + md5 + suffix);

//			File jsonFile = new File(uploadPath, "/插图/云空间/list.json");
			File jsonFileRaw = new File(uploadPath, "/插图/云空间/list.json.raw");

			synchronized (lock)
			{
				try
				{
					if (netPics == null)
					{
						jsonFileRaw.delete();

						while (!jsonFileRaw.exists())
						{
							FsUtils.createDir(jsonFileRaw.getParentFile());
							HttpUtils.doDownload("http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/插图/云空间/list.json.raw", jsonFileRaw);
						}

						netPics = SNetPicInfo.loadList(JsonHelper.getJSONArray(FsUtils.readText(jsonFileRaw)));
						for (SNetPicInfo netPic : netPics)
						{
							netPicMap.put(netPic.name + netPic.type, netPic);
						}
					}

					int pos = dstFile.getName().lastIndexOf('.');
					String type = dstFile.getName().substring(pos);
					String name = dstFile.getName().substring(0, pos);

					if (netPicMap.containsKey(name + type))
					{
						SNetPicInfo netPic = netPicMap.get(name + type);
						netPics.remove(netPic);
						netPics.add(0, netPic);

						FsUtils.writeText(jsonFileRaw, SNetPicInfo.saveList(netPics).toString());

						String path = jsonFileRaw.toString().replace('\\', '/').substring(uploadPath.length());
						LOG.v(path);
						OssUtils.doUploadUntil(OssUtils.ZjykFile, jsonFileRaw, path);

						return result(200, path, "上传成功");
					}
					else
					{
						FsUtils.createDir(dstFile.getParentFile());
						file.write(dstFile.toString());

						File smallFile = new File(uploadPath, "/插图/云空间/small/" + name + ".png");
						FsUtils.createDir(smallFile.getParentFile());

						BufferedImage image = ImageUtil.readImage(dstFile.toString());
						BufferedImage smallImage = ImageUtil.scaleImageMax(image, 200);
						ImageUtil.writeImage(smallImage, smallFile.toString());

						String rootUrl = "http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/插图/云空间";

						SNetPicInfo netPic = new SNetPicInfo();
						netPic.isMovie = false;
						netPic.type = type;
						netPic.name = name;
						netPic.smallUrl = String.format("%s/small/%s", rootUrl, smallFile.getName());
						netPic.smallWidth = smallImage.getWidth();
						netPic.smallHeight = smallImage.getHeight();
						netPic.bigUrl = String.format("%s/%s", rootUrl, dstFile.getName());
						netPic.bigWidth = image.getWidth();
						netPic.bigHeight = image.getHeight();
						netPics.add(0, netPic);
						netPicMap.put(netPic.name + netPic.type, netPic);

//						FsUtils.createDir(jsonFile.getParentFile());
						FsUtils.createDir(jsonFileRaw.getParentFile());

//						FsUtils.writeText(jsonFile, LOGJson.getStr(SNetPicInfo.saveList(netPics).toString()));
						FsUtils.writeText(jsonFileRaw, SNetPicInfo.saveList(netPics).toString());

						String path = dstFile.toString().replace('\\', '/').substring(uploadPath.length());
						LOG.v(path);
						OssUtils.doUploadUntil(OssUtils.ZjykFile, dstFile, path);

						path = smallFile.toString().replace('\\', '/').substring(uploadPath.length());
						LOG.v(path);
						OssUtils.doUploadUntil(OssUtils.ZjykFile, smallFile, path);

						path = jsonFileRaw.toString().replace('\\', '/').substring(uploadPath.length());
						LOG.v(path);
						OssUtils.doUploadUntil(OssUtils.ZjykFile, jsonFileRaw, path);

						return result(200, path, "上传成功");
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return result(0, null, "上传失败");
	}

	private Map<String, Object> result(Integer code, String data, String msg)
	{
		Map<String, Object> result = new HashMap<>();
		result.put("code", code);
		result.put("data", data);
		result.put("msg", msg);
		return result;
	}
}