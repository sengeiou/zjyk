package com.lys.app.service;

import com.lys.protobuf.SRequest_SvnGetDir;
import com.lys.protobuf.SResponse_SvnGetDir;

public interface SvnService
{
	SResponse_SvnGetDir SvnGetDir(SRequest_SvnGetDir request);
}