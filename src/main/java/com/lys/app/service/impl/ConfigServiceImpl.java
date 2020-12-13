package com.lys.app.service.impl;

import com.lys.app.manager.OssToken;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.service.ConfigService;
import com.lys.protobuf.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigServiceImpl implements ConfigService
{
	@Value("${url.root}")
	private String urlRoot = null;

	@Value("${app.config.root}")
	private String root = null;

	@Value("${app.config.api}")
	private String api = null;

	@Value("${app.config.upload}")
	private String upload = null;

//	@Value("${app.config.topic.api}")
//	private String topicApi = null;

	@Value("${app.config.svn.url}")
	private String svnUrl = null;

	@Value("${app.config.svn.account}")
	private String svnAccount = null;

	@Value("${app.config.svn.psw}")
	private String svnPsw = null;

	@Override
	public SResponse_GetConfig GetConfig(SRequest_GetConfig request)
	{
		SResponse_GetConfig response = new SResponse_GetConfig();
		response.urlRoot = urlRoot;
		response.root = root;
		response.api = api;
		response.upload = upload;
//		response.topicApi = topicApi;
		response.time = System.currentTimeMillis();
		response.svnUrl = svnUrl;
		response.svnAccount = svnAccount;
		response.svnPsw = svnPsw;
		return response;
	}

	private final Object lock = new Object();

	private final Map<String, String> lockMap = new ConcurrentHashMap<>();

	private String getLock(String key)
	{
		synchronized (lock)
		{
			if (!lockMap.containsKey(key))
				lockMap.put(key, key);
			return lockMap.get(key);
		}
	}

	@Override
	public SResponse_Test Test(SRequest_Test request)
	{
		SResponse_Test response = new SResponse_Test();
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public SResponse_GetOssToken GetOssToken(SRequest_GetOssToken request)
	{
		SResponse_GetOssToken response = OssToken.requestToken(request.userId);
		if (response == null)
			throw new ApiRuntimeException("请求TOKEN失败");
		return response;
	}
}