package com.lys.app.controller;

import com.lys.app.dao.TaskDao;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/task")
public class TaskController
{
//	private static final Integer pageSize = 20;

	@Autowired
	private TaskDao taskDao = null;

//	@Value("${spring.servlet.multipart.location}")
//	private String uploadPath = null;
//
//	private String apkDirName = "apk";

	@GetMapping("/page")
	public String manager()
	{
		return "jsp/task";
	}

	//	@RequestMapping("/get")
//	@ResponseBody
//	public SApp get(String pkgName, String channel)
//	{
//		LOG.v(this.getClass().getSimpleName() + String.format(" get %s %s ...", pkgName, channel));
//		return appService.get(pkgName, channel);
//	}
//
	@RequestMapping("/search")
	@ResponseBody
	public Map<String, Object> search(Integer type, Integer jobType, Integer timesForWeb, //
									  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy年MM月dd日") Date beginTime, //
									  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy年MM月dd日") Date endTime, //
									  String keywords, Integer page, Integer rows)
	{
		LOG.v(this.getClass().getSimpleName() + String.format(" search ..."));

		LOG.v("type	" + type);
		LOG.v("jobType	" + jobType);
		LOG.v("timesForWeb	" + timesForWeb);
		LOG.v("beginTime	" + beginTime);
		LOG.v("endTime	" + endTime);
		LOG.v("keywords	" + keywords);
		LOG.v("page	" + page);
		LOG.v("rows	" + rows);

//		if (type == null)
//			type = -1;
//		if (jobType == null)
//			jobType = -1;

		List<String> keywordList = new ArrayList<>();
		if (!StringUtils.isEmpty(keywords))
		{
			for (String keyword : keywords.split("\\s+"))
			{
				if (!StringUtils.isEmpty(keyword))
					keywordList.add(keyword);
			}
		}
		LOG.v(keywordList);

		Map<String, Object> result = new HashMap<>();
		result.put("total", taskDao.searchCount(type, jobType, timesForWeb, beginTime != null ? beginTime.getTime() : null, endTime != null ? endTime.getTime() : null, keywordList));
		result.put("rows", taskDao.search(type, jobType, timesForWeb, beginTime != null ? beginTime.getTime() : null, endTime != null ? endTime.getTime() : null, keywordList, (page - 1) * rows, rows));
		return result;
	}

//	@RequestMapping("/set")
//	@ResponseBody
//	public SApp set(@RequestBody SApp app)
//	{
//		LOG.v(this.getClass().getSimpleName() + " set ...");
//		return appService.set(app);
//	}
//
//	@PostMapping("/submit")
//	@ResponseBody
//	public SApp submit(Part apkFile)
//	{
//		SApp result = null;
//		LOG.v(this.getClass().getSimpleName() + String.format(" submit %s ...", apkFile.getSubmittedFileName()));
//		if (!StringUtils.isEmpty(apkFile.getSubmittedFileName()))
//		{
//			try
//			{
//				File tmpFile = new File(uploadPath, "tmp-" + UUID.randomUUID().toString());
//				apkFile.write(tmpFile.toString());
//
//				Map<String, String> apkInfoMap = ApkParser.parser(tmpFile);
//
//				String pkgName = apkInfoMap.get("packageName");
//				String channel = apkInfoMap.get("UMENG_CHANNEL");
//				if (channel == null)
//					channel = "";
//
//				File apkDir = new File(uploadPath, apkDirName);
//				if (!apkDir.exists())
//					apkDir.mkdirs();
//
//				File file = new File(apkDir, pkgName + "_" + channel + ".apk");
//				if (file.exists())
//					file.delete();
//
//				tmpFile.renameTo(file);
//
//				if (file.exists())
//				{
//					SApp app = get(pkgName, channel);
//					if (app == null)
//						app = new SApp();
//					app.pkgName = pkgName;
//					app.channel = channel;
//					app.versionCode = Integer.valueOf(apkInfoMap.get("versionCode"));
//					app.versionName = apkInfoMap.get("versionName");
//					app.probability = 1f;
//					app.name = apkInfoMap.get("label");
//					app.size = apkFile.getSize();
//					app.apkUrl = apkDirName + "/" + file.getName();
//					app.icoUrl = "";
//					app.des = "描述描述";
//
//					result = appService.set(app);
//				}
//
////				tmpFile.delete();
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
//
////	@PostMapping("/submit")
////	@ResponseBody
////	public SApp submit(String pkgName, String channel, String versionCode, String versionName, Part apkFile)
////	{
////		LOG.v(this.getClass().getSimpleName() + String.format(" submit %s %s %s %s %s ...", pkgName, channel, versionCode, versionName, apkFile.getSubmittedFileName()));
////		SApp app = get(pkgName, channel);
////		if (app == null)
////			app = new SApp();
////		app.setPkgName(pkgName);
////		app.setChannel(channel);
////		app.setVersionCode(Integer.valueOf(versionCode));
////		app.setVersionName(versionName);
////		if (!StringUtils.isEmpty(apkFile.getSubmittedFileName()))
////		{
////			try
////			{
////				String md5 = MD5.calcMD5(apkFile.getInputStream()).toLowerCase();
////				String suffix = FsUtils.getSuffix(apkFile.getSubmittedFileName()).toLowerCase();
////				apkFile.write(md5 + suffix);
////				app.setSize(apkFile.getSize());
////				app.setApkUrl(md5 + suffix);
////			}
////			catch (IOException e)
////			{
////				e.printStackTrace();
////				return null;
////			}
////		}
////		return appService.set(app);
////	}
//
//	@RequestMapping("/delete")
//	@ResponseBody
//	public void delete(String pkgName, String channel)
//	{
//		LOG.v(this.getClass().getSimpleName() + String.format(" delete %s %s ...", pkgName, channel));
//		appService.delete(pkgName, channel);
//		File file = new File(uploadPath, apkDirName + "/" + pkgName + "_" + channel + ".apk");
//		FsUtils.delete(file);
//	}
}