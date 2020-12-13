package com.lys.app.service.impl;

import com.lys.app.dao.LiveDao;
import com.lys.app.service.LiveService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class LiveServiceImpl implements LiveService
{
	@Autowired
	private LiveDao liveDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		liveDao.createTable();
	}

	@Override
	public SResponse_LiveGetAll LiveGetAll(SRequest_LiveGetAll request)
	{
		SResponse_LiveGetAll response = new SResponse_LiveGetAll();
		response.lives = liveDao.getList();
		return response;
	}

	@Override
	public SResponse_LiveGetList LiveGetList(SRequest_LiveGetList request)
	{
		SResponse_LiveGetList response = new SResponse_LiveGetList();
		response.lives = liveDao.find(request.userId);
		return response;
	}

	@Override
	public SResponse_LiveAddModify LiveAddModify(SRequest_LiveAddModify request)
	{
		SResponse_LiveAddModify response = new SResponse_LiveAddModify();
		if (StringUtils.isEmpty(request.live.id))
		{
			request.live.id = UUID.randomUUID().toString();
			liveDao.insert(request.live);
		}
		else
		{
			liveDao.update(request.live);
		}
		return response;
	}

	@Override
	public SResponse_LiveDelete LiveDelete(SRequest_LiveDelete request)
	{
		SResponse_LiveDelete response = new SResponse_LiveDelete();
		liveDao.delete(request.id);
		return response;
	}

	@Override
	public SResponse_LiveCopy LiveCopy(SRequest_LiveCopy request)
	{
		SResponse_LiveCopy response = new SResponse_LiveCopy();
		SLiveTask live = liveDao.get(request.id);
		if (live != null)
		{
			live.id = UUID.randomUUID().toString();
			live.type = SLiveType.Private;
			live.userIds.clear();
			liveDao.insert(live);
		}
		return response;
	}
}