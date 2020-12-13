package com.lys.app.service;

import com.lys.protobuf.*;

public interface ExamPaperService
{
	SResponse_ExamPaperGetAll ExamPaperGetAll(SRequest_ExamPaperGetAll request);

	SResponse_ExamPaperAddModify ExamPaperAddModify(SRequest_ExamPaperAddModify request);

	SResponse_ExamPaperDelete ExamPaperDelete(SRequest_ExamPaperDelete request);

	SResponse_ExamPaperDetail ExamPaperDetail(SRequest_ExamPaperDetail request);
}