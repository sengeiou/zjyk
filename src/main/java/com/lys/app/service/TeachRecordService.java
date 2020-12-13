package com.lys.app.service;

import com.lys.protobuf.*;

public interface TeachRecordService
{
	SResponse_TeachStart TeachStart(SRequest_TeachStart request);

	SResponse_TeachOverByTeacher TeachOverByTeacher(SRequest_TeachOverByTeacher request);

	SResponse_TeachQuestionByTeacher TeachQuestionByTeacher(SRequest_TeachQuestionByTeacher request);

	SResponse_TeachOverByStudent TeachOverByStudent(SRequest_TeachOverByStudent request);

	SResponse_TeachConfirmByStudent TeachConfirmByStudent(SRequest_TeachConfirmByStudent request);

	SResponse_TeachQuestionByStudent TeachQuestionByStudent(SRequest_TeachQuestionByStudent request);

	SResponse_TeachGetList TeachGetList(SRequest_TeachGetList request);
}