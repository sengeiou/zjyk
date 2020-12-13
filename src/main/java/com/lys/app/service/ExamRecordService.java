package com.lys.app.service;

import com.lys.protobuf.*;

public interface ExamRecordService
{
	SResponse_ExamRecordGetAll ExamRecordGetAll(SRequest_ExamRecordGetAll request);

	SResponse_ExamRecordAddModify ExamRecordAddModify(SRequest_ExamRecordAddModify request);

	SResponse_ExamRecordDelete ExamRecordDelete(SRequest_ExamRecordDelete request);

	SResponse_ExamRecordDetail ExamRecordDetail(SRequest_ExamRecordDetail request);
}