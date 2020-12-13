package com.lys.app.controller;

import com.lys.app.manager.OssUtils;
import com.lys.app.service.AppService;
import com.lys.protobuf.SApp;
import com.lys.utils.ApkParser;
import com.lys.utils.FsUtils;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/app")
public class AppController
{
	@Autowired
	private AppService appService = null;

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath = null;

	private String apkDirName = "apk";

	@GetMapping("/page")
	public String manager()
	{
//		LOG.v(this.getClass().getSimpleName() + " manager ...");
//		List<SApp> appList = appService.getList();
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("jsp/xxx");
//		mv.addObject("appList", appList);
//		return mv;
		return "jsp/app";
	}

	@RequestMapping("/get")
	@ResponseBody
	public SApp get(String pkgName, String channel)
	{
		LOG.v(this.getClass().getSimpleName() + String.format(" get %s %s ...", pkgName, channel));
		return appService.get(pkgName, channel);
	}

	@RequestMapping("/getList")
	@ResponseBody
	public List<SApp> getList()
	{
		LOG.v(this.getClass().getSimpleName() + " getList ...");
		return appService.getList();
	}

	@RequestMapping("/set")
	@ResponseBody
	public SApp set(@RequestBody SApp app)
	{
		LOG.v(this.getClass().getSimpleName() + " set ...");
		return appService.set(app);
	}

	@PostMapping("/submit")
	@ResponseBody
	public SApp submit(Part apkFile)
	{
		SApp result = null;
		LOG.v(this.getClass().getSimpleName() + String.format(" submit %s ...", apkFile.getSubmittedFileName()));
		if (!StringUtils.isEmpty(apkFile.getSubmittedFileName()))
		{
			try
			{
				File tmpFile = new File(uploadPath, "tmp-" + UUID.randomUUID().toString());
				apkFile.write(tmpFile.toString());

				Map<String, String> apkInfoMap = ApkParser.parser(tmpFile);

				String pkgName = apkInfoMap.get("packageName");
				String channel = apkInfoMap.get("UMENG_CHANNEL");
				if (channel == null)
					channel = "";

				File apkDir = new File(uploadPath, apkDirName);
				if (!apkDir.exists())
					apkDir.mkdirs();

				File file = new File(apkDir, pkgName + "_" + channel + ".apk");
				if (file.exists())
					file.delete();

				tmpFile.renameTo(file);

				if (file.exists())
				{
					String url = OssUtils.doUpload(OssUtils.ZjykFile, file, String.format("apk/%s", file.getName()));

					SApp app = get(pkgName, channel);
					if (app == null)
						app = new SApp();
					app.pkgName = pkgName;
					app.channel = channel;
					app.versionCode = Integer.valueOf(apkInfoMap.get("versionCode"));
					app.versionName = apkInfoMap.get("versionName");
					app.probability = 1f;
					app.name = apkInfoMap.get("label");
					app.size = apkFile.getSize();
//					app.apkUrl = apkDirName + "/" + file.getName();
					app.apkUrl = url;
					app.icoUrl = "";
					app.des = "描述描述";

					result = appService.set(app);
				}

//				tmpFile.delete();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

//	@PostMapping("/submit")
//	@ResponseBody
//	public SApp submit(String pkgName, String channel, String versionCode, String versionName, Part apkFile)
//	{
//		LOG.v(this.getClass().getSimpleName() + String.format(" submit %s %s %s %s %s ...", pkgName, channel, versionCode, versionName, apkFile.getSubmittedFileName()));
//		SApp app = get(pkgName, channel);
//		if (app == null)
//			app = new SApp();
//		app.setPkgName(pkgName);
//		app.setChannel(channel);
//		app.setVersionCode(Integer.valueOf(versionCode));
//		app.setVersionName(versionName);
//		if (!StringUtils.isEmpty(apkFile.getSubmittedFileName()))
//		{
//			try
//			{
//				String md5 = MD5.calcMD5(apkFile.getInputStream()).toLowerCase();
//				String suffix = FsUtils.getSuffix(apkFile.getSubmittedFileName()).toLowerCase();
//				apkFile.write(md5 + suffix);
//				app.setSize(apkFile.getSize());
//				app.setApkUrl(md5 + suffix);
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//				return null;
//			}
//		}
//		return appService.set(app);
//	}

	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String pkgName, String channel)
	{
		LOG.v(this.getClass().getSimpleName() + String.format(" delete %s %s ...", pkgName, channel));
		appService.delete(pkgName, channel);
		File file = new File(uploadPath, apkDirName + "/" + pkgName + "_" + channel + ".apk");
		FsUtils.delete(file);
	}
}