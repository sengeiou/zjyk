package com.lys.app.service.impl;

import com.lys.app.dao.TaskDao;
import com.lys.app.manager.RongHelper;
import com.lys.app.manager.SVNManager;
import com.lys.app.messages.TaskMessage;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.service.TaskService;
import com.lys.protobuf.*;
import com.lys.utils.FsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tmatesoft.svn.core.SVNException;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService
{
	@Autowired
	private TaskDao taskDao = null;

	@Autowired
	private RongHelper rongHelper = null;

	@Autowired
	private SVNManager svnManager = null;

	@Value("${url.root}")
	private String urlRoot = null;

	@Override
	public SResponse_FindTask FindTask(SRequest_FindTask request)
	{
		SResponse_FindTask response = new SResponse_FindTask();
		List<SPTask> tasks = taskDao.find(request.name, request.group);
		if (tasks.size() > 0)
			response.task = tasks.get(0);
		return response;
	}

	@Override
	public SResponse_GetTask GetTask(SRequest_GetTask request)
	{
		SResponse_GetTask response = new SResponse_GetTask();
		response.task = taskDao.get(request.taskId);
		return response;
	}

	@Override
	public SResponse_GetTaskList GetTaskList(SRequest_GetTaskList request)
	{
		SResponse_GetTaskList response = new SResponse_GetTaskList();
		response.tasks = taskDao.getList(request.userId, request.group, request.overType, request.sortType, request.createTime, request.overTime, request.prev, request.pageSize);
		return response;
	}

	@Override
	public SResponse_CreateTask CreateTask(SRequest_CreateTask request)
	{
		SResponse_CreateTask response = new SResponse_CreateTask();

		SPTask task = new SPTask();
		task.id = UUID.randomUUID().toString();
		task.userId = request.userId;
		task.type = request.type;
		task.jobType = request.jobType;
		task.group = request.group;
		task.name = request.name;
		task.createTime = System.currentTimeMillis();
		taskDao.insert(task);

		response.task = task;

		return response;
	}

	@Override
	public SResponse_SendTask SendTask(SRequest_SendTask request)
	{
		SResponse_SendTask response = new SResponse_SendTask();

		for (String taskId : request.taskIds)
		{
			SPTask srcTask = taskDao.get(taskId);
			if (srcTask != null)
			{
				for (String userId : request.userIds)
				{
					SPTask task = new SPTask();
					task.id = UUID.randomUUID().toString();
					task.userId = userId;
					task.sendUser = new SUser();
					task.sendUser.id = (!StringUtils.isEmpty(request.sendUserId) ? request.sendUserId : srcTask.userId);
					task.type = srcTask.type;
					task.jobType = srcTask.jobType;
					task.group = srcTask.group;
					task.name = srcTask.name;
					task.note = srcTask.note;
					task.text = srcTask.text;
					task.createTime = System.currentTimeMillis();
					taskDao.insert(task);

					response.tasks.add(task);

					synchronized (svnManager)
					{
						svnManager.copy(srcTask.userId, srcTask.id, task.userId, task.id, "copy by server");
					}

//					rongHelper.sendSystemMessage(new TaskMessage(task), task.userId);
				}
			}
		}

		return response;
	}

	@Override
	public SResponse_DeleteTask DeleteTask(SRequest_DeleteTask request)
	{
		SResponse_DeleteTask response = new SResponse_DeleteTask();

		SPTask task = taskDao.get(request.taskId);
		if (task != null)
		{
			taskDao.delete(request.taskId);
			try
			{
				synchronized (svnManager)
				{
					svnManager.deleteIfExists(task.userId, task.id, "delete by server");
				}
			}
			catch (SVNException e)
			{
				e.printStackTrace();
			}
		}

		return response;
	}

	@Override
	public SResponse_GetTaskFileVersion GetTaskFileVersion(SRequest_GetTaskFileVersion request)
	{
		return null;
	}

	public static final String VideoLocal = "local:";
	public static final String VideoNet = "net:";

	@Override
	public SResponse_GetTaskForWeb GetTaskForWeb(SRequest_GetTaskForWeb request)
	{
		SResponse_GetTaskForWeb response = new SResponse_GetTaskForWeb();

		SPTask task = taskDao.get(request.id);
		if (task == null)
			throw new ApiRuntimeException("任务不存在");

		task.timesForWeb++;
		taskDao.update(task);

		String userId = task.userId;

		synchronized (svnManager)
		{
			SVNManager.SvnTaskResult result = svnManager.updateTask(false, userId, request.id);
			if (result.resultCode != SVNManager.ResultCode_Success)
				throw new ApiRuntimeException(result.resultCode + " : " + result.errorMsg);
		}

		File taskDir = svnManager.getTaskDir(userId, request.id);
		if (!taskDir.exists())
			throw new ApiRuntimeException(taskDir + " not exists");

		File pagesetFile = new File(taskDir, "pageset.json");
		if (!pagesetFile.exists())
			throw new ApiRuntimeException(pagesetFile + " not exists");

		SNotePageSet pageset = SNotePageSet.load(FsUtils.readText(pagesetFile));

		response.userId = userId;
		response.id = request.id;
		response.urlRoot = urlRoot + "lys.tasks";
		response.name = task.name;
		response.count = pageset.pages.size();

		if (!StringUtils.isEmpty(request.page))
		{
			response.singlePage = true;
			Integer index = null;
			for (int i = 0; i < pageset.pages.size(); i++)
			{
				SNotePage page = pageset.pages.get(i);
				if (page.pageDir.equals(request.page))
				{
					index = i;
					break;
				}
			}
			if (index == null)
				throw new ApiRuntimeException(request.page + " not exists");

			SNotePage page = pageset.pages.get(index);

			if (index - 1 >= 0)
				response.prevPage = pageset.pages.get(index - 1).pageDir;

			if (index + 1 < pageset.pages.size())
				response.nextPage = pageset.pages.get(index + 1).pageDir;

			File dir = new File(taskDir, page.pageDir);
			if (!dir.exists())
				throw new ApiRuntimeException(dir + " not exists");

			File boardFile = new File(dir, "board.json");
			if (!boardFile.exists())
				throw new ApiRuntimeException(boardFile + " not exists");

			SBoardConfig board = SBoardConfig.load(FsUtils.readText(boardFile));

			SPageData pageData = new SPageData();
			pageData.index = index;
			if (new File(dir, "big_video.mp4").exists())
				pageData.bigVideo = VideoLocal;
			if (new File(dir, "big_video.txt").exists())
				pageData.bigVideo = VideoNet + FsUtils.readText(new File(dir, "big_video.txt"));
			pageData.hasBoard = new File(dir, "board.png").exists();
			pageData.page = page;
			pageData.board = board;
			response.pageDatas.add(pageData);
		}
		else
		{
			response.singlePage = false;
			for (int i = 0; i < pageset.pages.size(); i++)
			{
				SNotePage page = pageset.pages.get(i);

				File dir = new File(taskDir, page.pageDir);
				if (!dir.exists())
					throw new ApiRuntimeException(dir + " not exists");

				File boardFile = new File(dir, "board.json");
				if (!boardFile.exists())
					throw new ApiRuntimeException(boardFile + " not exists");

				SBoardConfig board = SBoardConfig.load(FsUtils.readText(boardFile));

				SPageData pageData = new SPageData();
				pageData.index = i;
				if (new File(dir, "big_video.mp4").exists())
					pageData.bigVideo = VideoLocal;
				if (new File(dir, "big_video.txt").exists())
					pageData.bigVideo = VideoNet + FsUtils.readText(new File(dir, "big_video.txt"));
				pageData.hasBoard = new File(dir, "board.png").exists();
				pageData.page = page;
				pageData.board = board;
				response.pageDatas.add(pageData);
			}
		}

		return response;
	}

	@Override
	public SResponse_SetTaskState SetTaskState(SRequest_SetTaskState request)
	{
		SResponse_SetTaskState response = new SResponse_SetTaskState();
		SPTask task = taskDao.get(request.taskId);
		if (task == null)
			throw new ApiRuntimeException("任务不存在");
		task.state = request.state;
		taskDao.update(task);
		return response;
	}

	@Override
	public SResponse_SetTaskNote SetTaskNote(SRequest_SetTaskNote request)
	{
		SResponse_SetTaskNote response = new SResponse_SetTaskNote();
		SPTask task = taskDao.get(request.taskId);
		if (task == null)
			throw new ApiRuntimeException("任务不存在");
		task.note = request.note;
		taskDao.update(task);
		return response;
	}

	@Override
	public SResponse_ModifyTask ModifyTask(SRequest_ModifyTask request)
	{
		SResponse_ModifyTask response = new SResponse_ModifyTask();
		SPTask task = taskDao.get(request.taskId);
		if (task == null)
			throw new ApiRuntimeException("任务不存在");
		task.group = request.group;
		task.name = request.name;
		task.type = request.type;
		task.jobType = request.jobType;
		taskDao.update(task);
		return response;
	}

	@Override
	public SResponse_ModifyTaskComment ModifyTaskComment(SRequest_ModifyTaskComment request)
	{
		SResponse_ModifyTaskComment response = new SResponse_ModifyTaskComment();
		SPTask task = taskDao.get(request.taskId);
		if (task == null)
			throw new ApiRuntimeException("任务不存在");
		task.comment = request.comment;
		taskDao.update(task);
		return response;
	}

	@Override
	public SResponse_AddTaskScore AddTaskScore(SRequest_AddTaskScore request)
	{
		SResponse_AddTaskScore response = new SResponse_AddTaskScore();
		if (true)
			throw new ApiRuntimeException("接口已废弃");
		return response;
	}

	@Override
	public SResponse_SetTaskOpen SetTaskOpen(SRequest_SetTaskOpen request)
	{
		SResponse_SetTaskOpen response = new SResponse_SetTaskOpen();
		SPTask task = taskDao.get(request.taskId);
		if (task == null)
			throw new ApiRuntimeException("任务不存在");
		task.open = request.open;
		taskDao.update(task);
		return response;
	}

	@Override
	public SResponse_RandomOpenTask RandomOpenTask(SRequest_RandomOpenTask request)
	{
		SResponse_RandomOpenTask response = new SResponse_RandomOpenTask();
		if (true)
			throw new ApiRuntimeException("接口已废弃");
		return response;
	}
}