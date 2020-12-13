package com.lys.app.service.impl;

import com.lys.app.dao.ExamPaperDao;
import com.lys.app.dao.ExamPointDao;
import com.lys.app.service.ExamPaperService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class ExamPaperServiceImpl implements ExamPaperService
{
	@Autowired
	private ExamPaperDao examPaperDao = null;

	@Autowired
	private ExamPointDao examPointDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		examPaperDao.createTable();
	}

	@Override
	public SResponse_ExamPaperGetAll ExamPaperGetAll(SRequest_ExamPaperGetAll request)
	{
		SResponse_ExamPaperGetAll response = new SResponse_ExamPaperGetAll();
		response.papers = examPaperDao.getList();
		return response;
	}

	@Override
	public SResponse_ExamPaperAddModify ExamPaperAddModify(SRequest_ExamPaperAddModify request)
	{
		SResponse_ExamPaperAddModify response = new SResponse_ExamPaperAddModify();
		if (StringUtils.isEmpty(request.paper.id))
		{
			request.paper.id = UUID.randomUUID().toString();
			examPaperDao.insert(request.paper);
		}
		else
		{
			examPaperDao.update(request.paper);
		}
		return response;
	}

	@Override
	public SResponse_ExamPaperDelete ExamPaperDelete(SRequest_ExamPaperDelete request)
	{
		SResponse_ExamPaperDelete response = new SResponse_ExamPaperDelete();
		examPaperDao.delete(request.id);
		return response;
	}

	@Override
	public SResponse_ExamPaperDetail ExamPaperDetail(SRequest_ExamPaperDetail request)
	{
		SResponse_ExamPaperDetail response = new SResponse_ExamPaperDetail();
		for (SExamPaperTopic topic : request.topics)
		{
			if (!StringUtils.isEmpty(topic.pointId))
				topic.point = examPointDao.get(topic.pointId);
		}
		response.topics = request.topics;
		return response;
	}
}