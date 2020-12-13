package com.lys.app.service.impl;

import com.lys.app.dao.EventDao;
import com.lys.app.service.EventService;
import com.lys.protobuf.SRequest_AddEvent;
import com.lys.protobuf.SRequest_GetEventList;
import com.lys.protobuf.SResponse_AddEvent;
import com.lys.protobuf.SResponse_GetEventList;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EventServiceImpl implements EventService
{
	@Autowired
	private EventDao eventDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		eventDao.createTable();
	}

	@Override
	public SResponse_AddEvent AddEvent(SRequest_AddEvent request)
	{
		SResponse_AddEvent response = new SResponse_AddEvent();
		request.event.time = System.currentTimeMillis();
		eventDao.insert(request.event);
		return response;
	}

	@Override
	public SResponse_GetEventList GetEventList(SRequest_GetEventList request)
	{
		SResponse_GetEventList response = new SResponse_GetEventList();
		response.events = eventDao.getList(request.userId, request.actions, request.targets);
		return response;
	}
}