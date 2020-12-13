package com.lys.app.service;

import com.lys.protobuf.*;

public interface ExamPointService
{
	SResponse_ExamPointGetAll ExamPointGetAll(SRequest_ExamPointGetAll request);

	SResponse_ExamPointAddModify ExamPointAddModify(SRequest_ExamPointAddModify request);

	SResponse_ExamPointDelete ExamPointDelete(SRequest_ExamPointDelete request);
}