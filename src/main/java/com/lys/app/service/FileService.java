package com.lys.app.service;

import com.lys.protobuf.*;

public interface FileService
{
	SResponse_FileDelete FileDelete(SRequest_FileDelete request);

	SResponse_FileList FileList(SRequest_FileList request);

	SResponse_FileExists FileExists(SRequest_FileExists request);

	SResponse_FileCopy FileCopy(SRequest_FileCopy request);
}