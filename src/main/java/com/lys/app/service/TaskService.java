package com.lys.app.service;

import com.lys.protobuf.*;

public interface TaskService
{
	SResponse_FindTask FindTask(SRequest_FindTask request);

	SResponse_GetTask GetTask(SRequest_GetTask request);

	SResponse_GetTaskList GetTaskList(SRequest_GetTaskList request);

	SResponse_CreateTask CreateTask(SRequest_CreateTask request);

	SResponse_SendTask SendTask(SRequest_SendTask request);

	SResponse_DeleteTask DeleteTask(SRequest_DeleteTask request);

	SResponse_GetTaskFileVersion GetTaskFileVersion(SRequest_GetTaskFileVersion request);

	SResponse_GetTaskForWeb GetTaskForWeb(SRequest_GetTaskForWeb request);

	SResponse_SetTaskState SetTaskState(SRequest_SetTaskState request);

	SResponse_SetTaskNote SetTaskNote(SRequest_SetTaskNote request);

	SResponse_ModifyTask ModifyTask(SRequest_ModifyTask request);

	SResponse_ModifyTaskComment ModifyTaskComment(SRequest_ModifyTaskComment request);

	SResponse_AddTaskScore AddTaskScore(SRequest_AddTaskScore request);

	SResponse_SetTaskOpen SetTaskOpen(SRequest_SetTaskOpen request);

	SResponse_RandomOpenTask RandomOpenTask(SRequest_RandomOpenTask request);
}