package com.lys.app.controller;

import com.lys.app.dao.LiveDao;
import com.lys.app.dao.UserDao;
import com.lys.protobuf.SLiveTask;
import com.lys.protobuf.SUser;
import com.lys.utils.FsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/live")
public class LiveController
{
	@Autowired
	private LiveDao liveDao = null;

	@Autowired
	private UserDao userDao = null;

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath = null;

	private String liveDirName = "live";

	@GetMapping("/page")
	public String manager()
	{
		return "jsp/live";
	}

	private String convertUserId(Map<String, String> cache, String userId)
	{
		if (!cache.containsKey(userId))
		{
			SUser user = userDao.getById(userId);
			if (user != null)
				cache.put(userId, user.name);
			else
				cache.put(userId, userId);
		}
		return cache.get(userId);
	}

	@RequestMapping("/getList")
	@ResponseBody
	public List<SLiveTask> getList()
	{
		System.out.println(this.getClass().getSimpleName() + String.format(" getList ..."));
		List<SLiveTask> list = liveDao.getList();
		if (list != null)
		{
			Map<String, String> cache = new HashMap<>();
			for (SLiveTask item : list)
			{
				item.actorId = convertUserId(cache, item.actorId);

				List<String> userIds = new ArrayList<>();
				for (String userId : item.userIds)
				{
					userIds.add(convertUserId(cache, userId));
				}
				item.userIds = userIds;
			}
		}
		return list;
	}

//	@RequestMapping("/add")
//	@ResponseBody
//	public SLiveTask add(SLiveTask live)
//	{
//		System.out.println(this.getClass().getSimpleName() + String.format(" add %s ...", live.getName()));
//		return liveService.add(live);
//	}
//
//	@RequestMapping("/modify")
//	@ResponseBody
//	public SLiveTask modify(SLiveTask live)
//	{
//		System.out.println(this.getClass().getSimpleName() + String.format(" modify %s %s ...", live.getId(), live.getName()));
//		return liveService.modify(live);
//	}

//	@PostMapping("/submit")
//	@ResponseBody
//	public SLiveTask submit(String id, String name, Integer type, String cover, String video, Integer zhanPercent, Integer lookCount, Long sort, Integer invalid)
//	{
//		SLiveTask live = null;
//		System.out.println(this.getClass().getSimpleName() + String.format(" submit %s ...", name));
//		if (StringUtils.isEmpty(id))
//		{
//			// add
//			live = new SLiveTask();
//			live.setName(name);
//			live.setType(type);
//			live.setCover(cover);
//			live.setVideo(video);
//			live.setZhanPercent(zhanPercent);
//			live.setLookCount(lookCount);
//			live.setInvalid(invalid);
//			live = liveService.add(live);
//		}
//		else
//		{
//			// modify
//			live = liveService.getById(id);
//			if (live != null)
//			{
//				live.setName(name);
//				live.setType(type);
//				live.setCover(cover);
//				live.setVideo(video);
//				live.setZhanPercent(zhanPercent);
//				live.setLookCount(lookCount);
//				live.setSort(sort);
//				live.setInvalid(invalid);
//				live = liveService.modify(live);
//			}
//		}
//		return live;
//	}

	@PostMapping("/submit")
	@ResponseBody
	public SLiveTask submit(String id, String actorId, String name, String des, Part coverFile, Part videoFile, String taskId, Integer type, String[] userIds, //
							@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "MM/dd/yyyy HH:mm") Date startTime)
	{
		SLiveTask live = null;
		System.out.println(this.getClass().getSimpleName() + String.format(" submit %s ...", name));
		if (StringUtils.isEmpty(id))
		{
			// add
			if (!StringUtils.isEmpty(coverFile.getSubmittedFileName()) && !StringUtils.isEmpty(videoFile.getSubmittedFileName()))
			{
				try
				{
					live = new SLiveTask();
					live.id = UUID.randomUUID().toString();
					live.actorId = actorId;
					live.name = name;
					live.des = des;

					File liveDir = new File(uploadPath, liveDirName + "/" + live.id);
					if (!liveDir.exists())
						liveDir.mkdirs();

					String coverSuffix = FsUtils.getSuffix(coverFile.getSubmittedFileName()).toLowerCase();
					String coverPath = liveDirName + "/" + live.id + "/" + "cover" + coverSuffix;
					coverFile.write(coverPath);
					live.cover = coverPath;

					String videoSuffix = FsUtils.getSuffix(videoFile.getSubmittedFileName()).toLowerCase();
					String videoPath = liveDirName + "/" + live.id + "/" + "video" + videoSuffix;
					videoFile.write(videoPath);
					live.video = videoPath;

					live.taskId = taskId;
					live.type = type;
					live.userIds = Arrays.asList(userIds);
					live.startTime = startTime.getTime();
					liveDao.insert(live);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			// modify
			try
			{
				live = liveDao.get(id);
				if (live != null)
				{
					live.actorId = actorId;
					live.name = name;
					live.des = des;

					File liveDir = new File(uploadPath, liveDirName + "/" + live.id);
					if (!liveDir.exists())
						liveDir.mkdirs();

					if (!StringUtils.isEmpty(coverFile.getSubmittedFileName()))
					{
						String coverSuffix = FsUtils.getSuffix(coverFile.getSubmittedFileName()).toLowerCase();
						String coverPath = liveDirName + "/" + live.id + "/" + "cover" + coverSuffix;
						coverFile.write(coverPath);
						live.cover = coverPath;
					}

					if (!StringUtils.isEmpty(videoFile.getSubmittedFileName()))
					{
						String videoSuffix = FsUtils.getSuffix(videoFile.getSubmittedFileName()).toLowerCase();
						String videoPath = liveDirName + "/" + live.id + "/" + "video" + videoSuffix;
						videoFile.write(videoPath);
						live.video = videoPath;
					}

					live.taskId = taskId;
					live.type = type;
					live.userIds = Arrays.asList(userIds);
					live.startTime = startTime.getTime();
					liveDao.update(live);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return live;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id)
	{
		System.out.println(this.getClass().getSimpleName() + String.format(" delete %s ...", id));
		liveDao.delete(id);
		File liveDir = new File(uploadPath, liveDirName + "/" + id);
		FsUtils.delete(liveDir);
	}
}