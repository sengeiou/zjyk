package com.lys.app.service.impl;

import com.lys.app.dao.AppDao;
import com.lys.app.service.AppService;
import com.lys.protobuf.SApp;
import com.lys.protobuf.SRequest_GetAppInfo;
import com.lys.protobuf.SResponse_GetAppInfo;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AppServiceImpl implements AppService
{
	@Autowired
	private AppDao appDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		appDao.createTable();
	}

	@Override
	public SApp get(String pkgName, String channel)
	{
		return appDao.get(pkgName, channel);
	}

	@Override
	public List<SApp> getList()
	{
		return appDao.getList();
	}

	@Override
	public SApp set(SApp app)
	{
		SApp ret = appDao.get(app.pkgName, app.channel);
		if (ret == null)
		{
			appDao.insert(app);
		}
		else
		{
			appDao.update(app);
		}
		return app;
	}

	@Override
	public void delete(String pkgName, String channel)
	{
		appDao.delete(pkgName, channel);
	}

	@Override
	public SResponse_GetAppInfo GetAppInfo(SRequest_GetAppInfo request)
	{
		SResponse_GetAppInfo response = new SResponse_GetAppInfo();
		response.app = appDao.get(request.pkgName, request.channel);
		return response;
	}
}