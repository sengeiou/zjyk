package com.lys.app.service.impl;

import com.lys.app.dao.ExamRecordDao;
import com.lys.app.dao.ExamPointDao;
import com.lys.app.service.ExamRecordService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class ExamRecordServiceImpl implements ExamRecordService
{
	@Autowired
	private ExamRecordDao examRecordDao = null;

	@Autowired
	private ExamPointDao examPointDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		examRecordDao.createTable();
	}

	@Override
	public SResponse_ExamRecordGetAll ExamRecordGetAll(SRequest_ExamRecordGetAll request)
	{
		SResponse_ExamRecordGetAll response = new SResponse_ExamRecordGetAll();
		response.records = examRecordDao.getList();
		return response;
	}

	@Override
	public SResponse_ExamRecordAddModify ExamRecordAddModify(SRequest_ExamRecordAddModify request)
	{
		SResponse_ExamRecordAddModify response = new SResponse_ExamRecordAddModify();
		if (StringUtils.isEmpty(request.record.id))
		{
			request.record.id = UUID.randomUUID().toString();
			examRecordDao.insert(request.record);
		}
		else
		{
			examRecordDao.update(request.record);
		}
		return response;
	}

	@Override
	public SResponse_ExamRecordDelete ExamRecordDelete(SRequest_ExamRecordDelete request)
	{
		SResponse_ExamRecordDelete response = new SResponse_ExamRecordDelete();
		examRecordDao.delete(request.id);
		return response;
	}

	@Override
	public SResponse_ExamRecordDetail ExamRecordDetail(SRequest_ExamRecordDetail request)
	{
		SResponse_ExamRecordDetail response = new SResponse_ExamRecordDetail();
//		for (SExamRecordTopic topic : request.topics)
//		{
//			if (!StringUtils.isEmpty(topic.pointId))
//				topic.point = examPointDao.get(topic.pointId);
//		}
//		response.topics = request.topics;
		return response;
	}
}