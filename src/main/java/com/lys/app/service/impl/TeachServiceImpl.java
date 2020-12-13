package com.lys.app.service.impl;

import com.lys.app.dao.TeachDao;
import com.lys.app.service.TeachService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Service
public class TeachServiceImpl implements TeachService
{
	@Autowired
	private TeachDao teachDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		teachDao.createTable();
	}

	@Override
	public SResponse_GetTeachList GetTeachList(SRequest_GetTeachList request)
	{
		SResponse_GetTeachList response = new SResponse_GetTeachList();
		response.teachs = teachDao.getList(request.teacherId, request.year, request.month, request.day);
		return response;
	}

	@Override
	public SResponse_ModifyTeach ModifyTeach(SRequest_ModifyTeach request)
	{
		SResponse_ModifyTeach response = new SResponse_ModifyTeach();
		for (STeach teach : request.teachs)
		{
			if (teach.flag == STeachFlag.None && StringUtils.isEmpty(teach.studentId))
			{
				teachDao.delete(teach);
			}
			else
			{
				STeach existTeach = teachDao.get(teach);
				if (existTeach == null)
				{
					teachDao.insert(teach);
				}
				else
				{
					teachDao.update(teach);
				}
			}
		}
		return response;
	}
}