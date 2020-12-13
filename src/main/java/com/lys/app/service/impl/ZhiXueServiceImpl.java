package com.lys.app.service.impl;

import com.lys.app.manager.SVNManager;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.other.client;
import com.lys.app.other.zhixue;
import com.lys.app.service.ZhiXueService;
import com.lys.protobuf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ZhiXueServiceImpl implements ZhiXueService
{
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath = null;

	@Autowired
	private SVNManager svnManager = null;

	@Override
	public SResponse_ZXProcessJuan ZXProcessJuan(SRequest_ZXProcessJuan request)
	{
		try
		{
			SResponse_ZXProcessJuan response = new SResponse_ZXProcessJuan();
			File file = new File(uploadPath, String.format("/juan/%s%s/%s/main.html", request.phase, request.subject, request.material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
			if (file.exists())
			{
				if (zhixue.processJuan(file))
				{
					SPTask task = client.genJuanTask(file.getParentFile(), request.phase, request.subject, request.material, 1600, true);
					if (task != null)
					{
						synchronized (svnManager)
						{
							svnManager.deleteIfExists(task.userId, task.id, "delete to recreate for juan");
						}

						String taskUrl = svnManager.getTaskUrl(task.userId, task.id);
						File taskDir = new File(file.getParentFile(), task.id);
						synchronized (svnManager)
						{
							svnManager.doImport(taskUrl, taskDir, "import task for juan");
						}

						return response;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		throw new ApiRuntimeException("处理错误");
	}

	@Override
	public SResponse_ZXProcessJuan2 ZXProcessJuan2(SRequest_ZXProcessJuan2 request)
	{
		try
		{
			SResponse_ZXProcessJuan2 response = new SResponse_ZXProcessJuan2();
			File file = new File(uploadPath, String.format("/juan/%s%s/%s/main.html", request.phase, request.subject, request.material.replace("/", "").replace("\\", "").replace("*", "").replace(":", "")));
			if (file.exists())
			{
				if (zhixue.processJuan2(file))
				{
					SPTask task1 = client.genJuanTask(file.getParentFile(), request.phase, request.subject, request.material, 1400, true);
					if (task1 != null)
					{
						synchronized (svnManager)
						{
							svnManager.deleteIfExists(task1.userId, task1.id, "delete to recreate for juan");
						}

						String taskUrl1 = svnManager.getTaskUrl(task1.userId, task1.id);
						File taskDir1 = new File(file.getParentFile(), task1.id);
						synchronized (svnManager)
						{
							svnManager.doImport(taskUrl1, taskDir1, "import task for juan");
						}

						SPTask task2 = client.genJuanTask(file.getParentFile(), "试题", request.subject, request.material, 1400, false);
						if (task2 != null)
						{
							synchronized (svnManager)
							{
								svnManager.deleteIfExists(task2.userId, task2.id, "delete to recreate for juan");
							}

							String taskUrl2 = svnManager.getTaskUrl(task2.userId, task2.id);
							File taskDir2 = new File(file.getParentFile(), task2.id);
							synchronized (svnManager)
							{
								svnManager.doImport(taskUrl2, taskDir2, "import task for juan");
							}

							return response;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		throw new ApiRuntimeException("处理错误");
	}

}