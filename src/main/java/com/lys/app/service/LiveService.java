package com.lys.app.service;

import com.lys.protobuf.*;

public interface LiveService
{
	SResponse_LiveGetAll LiveGetAll(SRequest_LiveGetAll request);

	SResponse_LiveGetList LiveGetList(SRequest_LiveGetList request);

	SResponse_LiveAddModify LiveAddModify(SRequest_LiveAddModify request);

	SResponse_LiveDelete LiveDelete(SRequest_LiveDelete request);

	SResponse_LiveCopy LiveCopy(SRequest_LiveCopy request);
}