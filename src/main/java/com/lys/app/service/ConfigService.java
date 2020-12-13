package com.lys.app.service;

import com.lys.protobuf.*;

public interface ConfigService
{
	SResponse_GetConfig GetConfig(SRequest_GetConfig request);

	SResponse_Test Test(SRequest_Test request);

	SResponse_GetOssToken GetOssToken(SRequest_GetOssToken request);

//
//	SResponse_GetAppInfoList GetAppInfoList(SRequest_GetAppInfoList request);
//

//

//
//
//
//
//	SResponse_SearchTopics SearchTopics(SRequest_SearchTopics request);
//
//	SResponse_GetTopicStyles GetTopicStyles(SRequest_GetTopicStyles request);
//
//	SResponse_GetKnowledges GetKnowledges(SRequest_GetKnowledges request);
//

//
//
//
//	SResponse_GetMatterList GetMatterList(SRequest_GetMatterList request);
//
//	SResponse_AddModifyMatter AddModifyMatter(SRequest_AddModifyMatter request);
//
//	SResponse_SwapMatter SwapMatter(SRequest_SwapMatter request);
//
//	SResponse_DeleteMatter DeleteMatter(SRequest_DeleteMatter request);
//
//	SResponse_GetBuyList GetBuyList(SRequest_GetBuyList request);
//
//	SResponse_AddModifyBuy AddModifyBuy(SRequest_AddModifyBuy request);
//
//	SResponse_DeleteBuy DeleteBuy(SRequest_DeleteBuy request);
//
//	SResponse_GetCommentList GetCommentList(SRequest_GetCommentList request);
//
//	SResponse_AddModifyComment AddModifyComment(SRequest_AddModifyComment request);
//
//	SResponse_DeleteComment DeleteComment(SRequest_DeleteComment request);
//
//	SResponse_GetGoodsList GetGoodsList(SRequest_GetGoodsList request);
//
//	SResponse_AddModifyGoods AddModifyGoods(SRequest_AddModifyGoods request);
//
//	SResponse_SwapGoods SwapGoods(SRequest_SwapGoods request);
//
//	SResponse_DeleteGoods DeleteGoods(SRequest_DeleteGoods request);
//
//	SResponse_GetOrderList GetOrderList(SRequest_GetOrderList request);
//
//	SResponse_AddOrder AddOrder(SRequest_AddOrder request);
//
//	SResponse_ModifyOrderState ModifyOrderState(SRequest_ModifyOrderState request);
//
//	SResponse_GetTaskGroupList GetTaskGroupList(SRequest_GetTaskGroupList request);
//
//	SResponse_AddModifyTaskGroup AddModifyTaskGroup(SRequest_AddModifyTaskGroup request);
//
//	SResponse_SwapTaskGroup SwapTaskGroup(SRequest_SwapTaskGroup request);
//
//	SResponse_DeleteTaskGroup DeleteTaskGroup(SRequest_DeleteTaskGroup request);
//
//
//
//	SResponse_ZXCreateTask ZXCreateTask(SRequest_ZXCreateTask request);
//
//	SResponse_ZXPullTask ZXPullTask(SRequest_ZXPullTask request);
//
//	SResponse_ZXCatchPageOver ZXCatchPageOver(SRequest_ZXCatchPageOver request);
//
//	SResponse_ZXCatchOver ZXCatchOver(SRequest_ZXCatchOver request);
//
//	SResponse_ZXGenKnowledgeTree ZXGenKnowledgeTree(SRequest_ZXGenKnowledgeTree request);
//
//	SResponse_ZXGenChapterTree ZXGenChapterTree(SRequest_ZXGenChapterTree request);
//
//	SResponse_ZXAddTopic ZXAddTopic(SRequest_ZXAddTopic request);
//
//	SResponse_ZXPullAccount ZXPullAccount(SRequest_ZXPullAccount request);
//
//	SResponse_ZXReportAccount ZXReportAccount(SRequest_ZXReportAccount request);
//
//	SResponse_ZXDeviceList ZXDeviceList(SRequest_ZXDeviceList request);
//
//	SResponse_ZXTickInfo ZXTickInfo(SRequest_ZXTickInfo request);
//
//	SResponse_ZXProcessJuan ZXProcessJuan(SRequest_ZXProcessJuan request);
//
//	SResponse_ZXProcessJuan2 ZXProcessJuan2(SRequest_ZXProcessJuan2 request);
//
//	SResponse_GetServerLog GetServerLog(SRequest_GetServerLog request);
//
//	SResponse_GetServerState GetServerState(SRequest_GetServerState request);
//
//	SResponse_SetServerState SetServerState(SRequest_SetServerState request);
//
//	SResponse_GetTimeRecord GetTimeRecord(SRequest_GetTimeRecord request);
//
//	SResponse_GetServerUploadingList GetServerUploadingList(SRequest_GetServerUploadingList request);
}