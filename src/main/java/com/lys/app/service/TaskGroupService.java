package com.lys.app.service;

import com.lys.protobuf.*;

public interface TaskGroupService
{
	SResponse_GetTaskGroupList GetTaskGroupList(SRequest_GetTaskGroupList request);

	SResponse_AddModifyTaskGroup AddModifyTaskGroup(SRequest_AddModifyTaskGroup request);

	SResponse_SwapTaskGroup SwapTaskGroup(SRequest_SwapTaskGroup request);

	SResponse_DeleteTaskGroup DeleteTaskGroup(SRequest_DeleteTaskGroup request);
}