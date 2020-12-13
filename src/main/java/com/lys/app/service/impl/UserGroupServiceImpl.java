package com.lys.app.service.impl;

import com.lys.app.dao.UserGroupDao;
import com.lys.app.service.UserGroupService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class UserGroupServiceImpl implements UserGroupService
{
	@Autowired
	private UserGroupDao userGroupDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		userGroupDao.createTable();
	}

	@Override
	public SResponse_UserGroupGetAll UserGroupGetAll(SRequest_UserGroupGetAll request)
	{
		SResponse_UserGroupGetAll response = new SResponse_UserGroupGetAll();
		response.userGroups = userGroupDao.getList();
		return response;
	}

	@Override
	public SResponse_UserGroupAddModify UserGroupAddModify(SRequest_UserGroupAddModify request)
	{
		SResponse_UserGroupAddModify response = new SResponse_UserGroupAddModify();
		if (StringUtils.isEmpty(request.userGroup.id))
		{
			request.userGroup.id = UUID.randomUUID().toString();
			userGroupDao.insert(request.userGroup);
		}
		else
		{
			userGroupDao.update(request.userGroup);
		}
		return response;
	}

	@Override
	public SResponse_UserGroupDelete UserGroupDelete(SRequest_UserGroupDelete request)
	{
		SResponse_UserGroupDelete response = new SResponse_UserGroupDelete();
		userGroupDao.delete(request.id);
		return response;
	}
}