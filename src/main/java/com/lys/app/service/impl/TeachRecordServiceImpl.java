package com.lys.app.service.impl;

import com.lys.app.dao.TeachRecordDao;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.service.TeachRecordService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeachRecordServiceImpl implements TeachRecordService
{
	@Autowired
	private TeachRecordDao teachRecordDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		teachRecordDao.createTable();
	}

	@Override
	public SResponse_TeachStart TeachStart(SRequest_TeachStart request)
	{
		SResponse_TeachStart response = new SResponse_TeachStart();

		if (teachRecordDao.find(request.teachId).size() > 0)
			throw new ApiRuntimeException("teachId已存在");

		long startTime = System.currentTimeMillis();

		{
			STeachRecord teachRecord = new STeachRecord();
			teachRecord.teachId = request.teachId;
			teachRecord.userId = request.userId;
			teachRecord.isHost = true;
			teachRecord.targetCount = request.targetIds.size();
			teachRecord.targetIds = request.targetIds;
			teachRecord.taskId = request.taskId;
			teachRecord.startTime = startTime;
			teachRecordDao.insert(teachRecord);
		}

		for (String userId : request.targetIds)
		{
			List<String> targetIds = new ArrayList<>();
			targetIds.add(request.userId);

			STeachRecord teachRecord = new STeachRecord();
			teachRecord.teachId = request.teachId;
			teachRecord.userId = userId;
			teachRecord.isHost = false;
			teachRecord.targetCount = targetIds.size();
			teachRecord.targetIds = targetIds;
			teachRecord.taskId = request.taskId;
			teachRecord.startTime = startTime;
			teachRecordDao.insert(teachRecord);
		}

		return response;
	}

	@Override
	public SResponse_TeachOverByTeacher TeachOverByTeacher(SRequest_TeachOverByTeacher request)
	{
		SResponse_TeachOverByTeacher response = new SResponse_TeachOverByTeacher();
		long overTime = System.currentTimeMillis();
		teachRecordDao.updateOverTime(request.teachId, request.userId, overTime);
		teachRecordDao.updateTeachPages(request.teachId, request.teachPages);
		return response;
	}

	@Override
	public SResponse_TeachQuestionByTeacher TeachQuestionByTeacher(SRequest_TeachQuestionByTeacher request)
	{
		SResponse_TeachQuestionByTeacher response = new SResponse_TeachQuestionByTeacher();
		teachRecordDao.updateQuestionTeacher(request.teachId, request.questionHot, request.questionMind, request.questionLogic, request.questionOther);
		return response;
	}

	@Override
	public SResponse_TeachOverByStudent TeachOverByStudent(SRequest_TeachOverByStudent request)
	{
		SResponse_TeachOverByStudent response = new SResponse_TeachOverByStudent();
		long overTime = System.currentTimeMillis();
		teachRecordDao.updateOverTime(request.teachId, request.userId, overTime);
		return response;
	}

	@Override
	public SResponse_TeachConfirmByStudent TeachConfirmByStudent(SRequest_TeachConfirmByStudent request)
	{
		SResponse_TeachConfirmByStudent response = new SResponse_TeachConfirmByStudent();
		teachRecordDao.updateConfirmMsg(request.teachId, request.userId, request.confirmMsg);
		teachRecordDao.updateConfirmMsg(request.teachId, request.targetId, request.confirmMsg);
		return response;
	}

	@Override
	public SResponse_TeachQuestionByStudent TeachQuestionByStudent(SRequest_TeachQuestionByStudent request)
	{
		SResponse_TeachQuestionByStudent response = new SResponse_TeachQuestionByStudent();
		teachRecordDao.updateQuestionStudent(request.teachId, request.userId, request.questionMatch, request.questionDiff, request.questionGot, request.questionQuality, request.questionLike);
		teachRecordDao.updateQuestionStudent(request.teachId, request.targetId, request.questionMatch, request.questionDiff, request.questionGot, request.questionQuality, request.questionLike);
		return response;
	}

	@Override
	public SResponse_TeachGetList TeachGetList(SRequest_TeachGetList request)
	{
		SResponse_TeachGetList response = new SResponse_TeachGetList();
		response.teachRecords = teachRecordDao.getList(request.userId, request.fromTime, request.toTime);
		return response;
	}
}