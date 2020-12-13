package com.lys.app.service.impl;

import com.lys.app.dao.TaskGroupDao;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.service.TaskGroupService;
import com.lys.protobuf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class TaskGroupServiceImpl implements TaskGroupService
{
	@Autowired
	private TaskGroupDao taskGroupDao = null;

	@Override
	public SResponse_GetTaskGroupList GetTaskGroupList(SRequest_GetTaskGroupList request)
	{
		SResponse_GetTaskGroupList response = new SResponse_GetTaskGroupList();
		response.taskGroupList = taskGroupDao.getList();
		return response;
	}

	@Override
	public SResponse_AddModifyTaskGroup AddModifyTaskGroup(SRequest_AddModifyTaskGroup request)
	{
		SResponse_AddModifyTaskGroup response = new SResponse_AddModifyTaskGroup();
		if (StringUtils.isEmpty(request.taskGroup.id))
		{
			STaskGroup taskGroup = new STaskGroup();
			taskGroup.id = UUID.randomUUID().toString();
			taskGroup.name = request.taskGroup.name;
			taskGroup.important = request.taskGroup.important;
			taskGroup.difficulty = request.taskGroup.difficulty;
			taskGroup.cover = request.taskGroup.cover;
			taskGroup.sort = System.currentTimeMillis();
			taskGroupDao.insert(taskGroup);
		}
		else
		{
			STaskGroup taskGroup = taskGroupDao.get(request.taskGroup.id);
			if (taskGroup == null)
				throw new ApiRuntimeException("任务组不存在");
			taskGroup.name = request.taskGroup.name;
			taskGroup.important = request.taskGroup.important;
			taskGroup.difficulty = request.taskGroup.difficulty;
			taskGroup.cover = request.taskGroup.cover;
			taskGroup.sort = request.taskGroup.sort;
			taskGroupDao.update(taskGroup);
		}
		return response;
	}

	@Override
	public SResponse_SwapTaskGroup SwapTaskGroup(SRequest_SwapTaskGroup request)
	{
		SResponse_SwapTaskGroup response = new SResponse_SwapTaskGroup();

		STaskGroup taskGroup1 = taskGroupDao.get(request.taskGroup1.id);
		if (taskGroup1 == null)
			throw new ApiRuntimeException("任务组不存在");

		STaskGroup taskGroup2 = taskGroupDao.get(request.taskGroup2.id);
		if (taskGroup2 == null)
			throw new ApiRuntimeException("任务组不存在");

		Long sort = taskGroup1.sort;
		taskGroup1.sort = taskGroup2.sort;
		taskGroup2.sort = sort;

		taskGroupDao.update(taskGroup1);
		taskGroupDao.update(taskGroup2);

		return response;
	}

	@Override
	public SResponse_DeleteTaskGroup DeleteTaskGroup(SRequest_DeleteTaskGroup request)
	{
		SResponse_DeleteTaskGroup response = new SResponse_DeleteTaskGroup();
		taskGroupDao.delete(request.taskGroupId);
		return response;
	}
}