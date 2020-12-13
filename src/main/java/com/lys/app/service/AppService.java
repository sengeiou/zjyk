package com.lys.app.service;

import com.lys.protobuf.SApp;
import com.lys.protobuf.SRequest_GetAppInfo;
import com.lys.protobuf.SResponse_GetAppInfo;

import java.util.List;

public interface AppService
{
	SApp get(String pkgName, String channel);

	List<SApp> getList();

	SApp set(SApp app);

	void delete(String pkgName, String channel);

	SResponse_GetAppInfo GetAppInfo(SRequest_GetAppInfo request);
}