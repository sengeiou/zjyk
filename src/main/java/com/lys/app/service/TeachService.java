package com.lys.app.service;

import com.lys.protobuf.SRequest_GetTeachList;
import com.lys.protobuf.SRequest_ModifyTeach;
import com.lys.protobuf.SResponse_GetTeachList;
import com.lys.protobuf.SResponse_ModifyTeach;

public interface TeachService
{
	SResponse_GetTeachList GetTeachList(SRequest_GetTeachList request);

	SResponse_ModifyTeach ModifyTeach(SRequest_ModifyTeach request);
}