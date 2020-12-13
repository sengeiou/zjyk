package com.lys.app.service;

import com.lys.protobuf.SRequest_ZXProcessJuan;
import com.lys.protobuf.SRequest_ZXProcessJuan2;
import com.lys.protobuf.SResponse_ZXProcessJuan;
import com.lys.protobuf.SResponse_ZXProcessJuan2;

public interface ZhiXueService
{
	SResponse_ZXProcessJuan ZXProcessJuan(SRequest_ZXProcessJuan request);

	SResponse_ZXProcessJuan2 ZXProcessJuan2(SRequest_ZXProcessJuan2 request);
}