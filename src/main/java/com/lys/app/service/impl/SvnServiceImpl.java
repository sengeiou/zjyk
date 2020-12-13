package com.lys.app.service.impl;

import com.lys.app.manager.SVNManager;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.service.SvnService;
import com.lys.protobuf.SRequest_SvnGetDir;
import com.lys.protobuf.SResponse_SvnGetDir;
import com.lys.protobuf.SSvnDirObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvnServiceImpl implements SvnService
{
	@Autowired
	private SVNManager svnManager = null;

	@Override
	public SResponse_SvnGetDir SvnGetDir(SRequest_SvnGetDir request)
	{
		SResponse_SvnGetDir response = new SResponse_SvnGetDir();
		List<SSvnDirObj> objs = svnManager.getDir(request.path);
		if (objs == null)
			throw new ApiRuntimeException("获取SVN目录失败");
		response.objs = objs;
		return response;
	}
}