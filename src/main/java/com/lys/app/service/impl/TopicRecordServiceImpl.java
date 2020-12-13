package com.lys.app.service.impl;

import com.lys.app.dao.TopicRecordDao;
import com.lys.app.service.TopicRecordService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TopicRecordServiceImpl implements TopicRecordService
{
	@Autowired
	private TopicRecordDao topicRecordDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		topicRecordDao.createTable();
	}

	@Override
	public SResponse_TopicRecordGetList TopicRecordGetList(SRequest_TopicRecordGetList request)
	{
		SResponse_TopicRecordGetList response = new SResponse_TopicRecordGetList();
		response.topicRecords = topicRecordDao.getList(request.userId, request.type, request.time, request.prev, request.pageSize);
		return response;
	}

	@Override
	public SResponse_TopicRecordGet TopicRecordGet(SRequest_TopicRecordGet request)
	{
		SResponse_TopicRecordGet response = new SResponse_TopicRecordGet();
		response.topicRecord = topicRecordDao.get(request.userId, request.topicId);
		return response;
	}

	@Override
	public SResponse_TopicRecordSetFav TopicRecordSetFav(SRequest_TopicRecordSetFav request)
	{
		SResponse_TopicRecordSetFav response = new SResponse_TopicRecordSetFav();
		STopicRecord topicRecord = topicRecordDao.get(request.userId, request.topicId);
		if (topicRecord != null)
		{
			if (request.fav == 1)
			{
				topicRecord.fav = request.fav;
				topicRecordDao.update(topicRecord);
			}
			else
			{
				if (topicRecord.result == 0)
				{
					topicRecordDao.delete(request.userId, request.topicId);
					topicRecord = null;
				}
				else
				{
					topicRecord.fav = request.fav;
					topicRecordDao.update(topicRecord);
				}
			}
		}
		else
		{
			if (request.fav == 1)
			{
				topicRecord = new STopicRecord();
				topicRecord.userId = request.userId;
				topicRecord.topicId = request.topicId;
				topicRecord.fav = request.fav;
				topicRecord.result = 0;
				topicRecord.time = System.currentTimeMillis();
				topicRecordDao.insert(topicRecord);
			}
		}
		response.topicRecord = topicRecord;
		return response;
	}

	@Override
	public SResponse_TopicRecordSetResult TopicRecordSetResult(SRequest_TopicRecordSetResult request)
	{
		SResponse_TopicRecordSetResult response = new SResponse_TopicRecordSetResult();
		STopicRecord topicRecord = topicRecordDao.get(request.userId, request.topicId);
		if (topicRecord != null)
		{
			topicRecord.result = request.result;
			topicRecordDao.update(topicRecord);
		}
		else
		{
			topicRecord = new STopicRecord();
			topicRecord.userId = request.userId;
			topicRecord.topicId = request.topicId;
			topicRecord.fav = 0;
			topicRecord.result = request.result;
			topicRecord.time = System.currentTimeMillis();
			topicRecordDao.insert(topicRecord);
		}
		response.topicRecord = topicRecord;
		return response;
	}

	@Override
	public SResponse_TopicRecordDelete TopicRecordDelete(SRequest_TopicRecordDelete request)
	{
		SResponse_TopicRecordDelete response = new SResponse_TopicRecordDelete();
		topicRecordDao.delete(request.userId, request.topicId);
		return response;
	}
}