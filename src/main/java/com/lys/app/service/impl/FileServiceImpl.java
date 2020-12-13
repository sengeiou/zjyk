package com.lys.app.service.impl;

import com.lys.app.service.FileService;
import com.lys.protobuf.*;
import com.lys.utils.FsUtils;
import com.lys.utils.MD5;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileServiceImpl implements FileService
{
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath = null;

	@Value("${url.root}")
	private String urlRoot = null;

	@Override
	public SResponse_FileDelete FileDelete(SRequest_FileDelete request)
	{
		SResponse_FileDelete response = new SResponse_FileDelete();
		for (String path : request.paths)
		{
			File file = new File(uploadPath, path);
			FsUtils.delete(file);
			FsUtils.deleteDirectoryIfEmpty(file.getParentFile());
		}
		return response;
	}

	@Override
	public SResponse_FileList FileList(SRequest_FileList request)
	{
		SResponse_FileList response = new SResponse_FileList();
		response.root = urlRoot;
		File file = new File(uploadPath, request.path);
		for (File f : FsUtils.searchFiles(file))
		{
			SFilePath path = new SFilePath();
			path.path = f.getAbsolutePath().substring(new File(uploadPath).getAbsolutePath().length());
			path.path = path.path.replace('\\', '/');
			path.md5 = MD5.calcMD5(f);
			response.paths.add(path);
		}
		return response;
	}

	@Override
	public SResponse_FileExists FileExists(SRequest_FileExists request)
	{
		SResponse_FileExists response = new SResponse_FileExists();
		File file = new File(uploadPath, request.path);
		response.exists = file.exists();
		return response;
	}

	@Override
	public SResponse_FileCopy FileCopy(SRequest_FileCopy request)
	{
		SResponse_FileCopy response = new SResponse_FileCopy();
		File srcFile = new File(uploadPath, request.srcPath);
		File dstFile = new File(uploadPath, request.dstPath);
		FsUtils.copyPath(srcFile, dstFile);
		return response;
	}
}