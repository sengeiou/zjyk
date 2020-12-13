package com.lys.app.service.impl;

import com.lys.app.dao.ExamPointDao;
import com.lys.app.service.ExamPointService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class ExamPointServiceImpl implements ExamPointService
{
	@Autowired
	private ExamPointDao examPointDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		examPointDao.createTable();
	}

	@Override
	public SResponse_ExamPointGetAll ExamPointGetAll(SRequest_ExamPointGetAll request)
	{
		SResponse_ExamPointGetAll response = new SResponse_ExamPointGetAll();
		response.points = examPointDao.getList();
		return response;
	}

	@Override
	public SResponse_ExamPointAddModify ExamPointAddModify(SRequest_ExamPointAddModify request)
	{
		SResponse_ExamPointAddModify response = new SResponse_ExamPointAddModify();
		if (StringUtils.isEmpty(request.point.id))
		{
			request.point.id = UUID.randomUUID().toString();
			examPointDao.insert(request.point);
		}
		else
		{
			examPointDao.update(request.point);
		}
		return response;
	}

	@Override
	public SResponse_ExamPointDelete ExamPointDelete(SRequest_ExamPointDelete request)
	{
		SResponse_ExamPointDelete response = new SResponse_ExamPointDelete();
		examPointDao.delete(request.id);
		return response;
	}
}