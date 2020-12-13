package com.lys.app.controller;

import com.lys.app.AppApplication;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.service.*;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/logic")
//@ControllerAdvice
public class LogicController
{
	@Autowired
	private ConfigService configService = null;

	@Autowired
	private AppService appService = null;

	@Autowired
	private UserService userService = null;

	@Autowired
	private FriendService friendService = null;

	@Autowired
	private TaskService taskService = null;

	@Autowired
	private TopicRecordService topicRecordService = null;

	@Autowired
	private TeachRecordService teachRecordService = null;

	@Autowired
	private TeachService teachService = null;

	@Autowired
	private EventService eventService = null;

	@Autowired
	private SvnService svnService = null;

	@Autowired
	private FileService fileService = null;

	@Autowired
	private TopicService topicService = null;

	@Autowired
	private TaskGroupService taskGroupService = null;

	@Autowired
	private LiveService liveService = null;

	@Autowired
	private ExamPointService examPointService = null;

	@Autowired
	private ExamPaperService examPaperService = null;

	@Autowired
	private ExamRecordService examRecordService = null;

	@Autowired
	private UserGroupService userGroupService = null;

	@Autowired
	private ZhiXueService zhiXueService = null;

	public static Integer runningCount = 0;

	@RequestMapping("/apistr")
	@ResponseBody
	public String apistr(String request)
	{
		return api(SProtocol.load(request)).saveToStr();
	}

	@PostMapping("/api")
	@ResponseBody
	public SProtocol api(@RequestBody SProtocol request)
	{
		LOG.v(String.format("receive:------ %s ---- %s ----- %s ------", SHandleId.name(request.handleId), request.userId, request.userName));
//		LOGJson.log(request.saveToStr());
		runningCount++;
		SProtocol response = null;
		try
		{
			if (AppApplication.stop)
				throw new ApiRuntimeException("服务器正在维护");

			if (AppApplication.startTime == 0)
				throw new ApiRuntimeException("服务器正在启动");

			response = process(request);
		}
		catch (ApiRuntimeException e)
		{
//			e.printStackTrace();
			response = new SProtocol();
			response.code = -1;
			response.msg = e.getMsg();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			response = new SProtocol();
			response.code = -2;
			response.msg = e.getMessage();
		}
		runningCount--;
		LOG.v("result:--------- " + response.code + " --------" + (response.code != 200 ? response.msg : ""));
//		LOGJson.log(response.saveToStr());
		return response;
	}

	private SProtocol process(SProtocol request) throws ApiRuntimeException
	{
//		if (RunServiceImpl.sServerState.stop)
//			throw new ApiRuntimeException("服务器正在维护");

//		SRequestRecord requestRecord = new SRequestRecord();
//		requestRecord.entryTime = System.currentTimeMillis();
//		requestRecord.handleId = request.handleId;
//		requestRecord.handleName = SHandleId.name(request.handleId);
//		requestRecord.readyTime = System.currentTimeMillis();
//		RunServiceImpl.sServerState.requestRecords.add(requestRecord);

		SProtocol response = new SProtocol();
		response.code = 200;

		if (request.handleId.equals(SHandleId.GetConfig))
			response.data = configService.GetConfig(SRequest_GetConfig.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.Test))
			response.data = configService.Test(SRequest_Test.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.GetOssToken))
			response.data = configService.GetOssToken(SRequest_GetOssToken.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.GetAppInfo))
			response.data = appService.GetAppInfo(SRequest_GetAppInfo.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.UserPhoneCode))
			response.data = userService.UserPhoneCode(SRequest_UserPhoneCode.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.UserReg))
			response.data = userService.UserReg(SRequest_UserReg.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.UserLogin))
			response.data = userService.UserLogin(SRequest_UserLogin.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.GetUser))
			response.data = userService.GetUser(SRequest_GetUser.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ModifyHead))
			response.data = userService.ModifyHead(SRequest_ModifyHead.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ModifyName))
			response.data = userService.ModifyName(SRequest_ModifyName.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ModifySex))
			response.data = userService.ModifySex(SRequest_ModifySex.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ModifyGrade))
			response.data = userService.ModifyGrade(SRequest_ModifyGrade.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ModifyPsw))
			response.data = userService.ModifyPsw(SRequest_ModifyPsw.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.GetUserList))
			response.data = userService.GetUserList(SRequest_GetUserList.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.AddUser))
			response.data = userService.AddUser(SRequest_AddUser.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.DeleteUser))
			response.data = userService.DeleteUser(SRequest_DeleteUser.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.SetVip))
			response.data = userService.SetVip(SRequest_SetVip.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.SetCp))
			response.data = userService.SetCp(SRequest_SetCp.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.GetFriendList))
			response.data = friendService.GetFriendList(SRequest_GetFriendList.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.AddFriend))
			response.data = friendService.AddFriend(SRequest_AddFriend.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.DeleteFriend))
			response.data = friendService.DeleteFriend(SRequest_DeleteFriend.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ModifyFriendGroup))
			response.data = friendService.ModifyFriendGroup(SRequest_ModifyFriendGroup.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.FindTask))
			response.data = taskService.FindTask(SRequest_FindTask.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.GetTask))
			response.data = taskService.GetTask(SRequest_GetTask.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.GetTaskList))
			response.data = taskService.GetTaskList(SRequest_GetTaskList.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.CreateTask))
			response.data = taskService.CreateTask(SRequest_CreateTask.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.SendTask))
			response.data = taskService.SendTask(SRequest_SendTask.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.DeleteTask))
			response.data = taskService.DeleteTask(SRequest_DeleteTask.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetTaskFileVersion))
//			response.data = taskService.GetTaskFileVersion(SRequest_GetTaskFileVersion.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.GetTaskForWeb))
			response.data = taskService.GetTaskForWeb(SRequest_GetTaskForWeb.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.SetTaskState))
			response.data = taskService.SetTaskState(SRequest_SetTaskState.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.SetTaskNote))
			response.data = taskService.SetTaskNote(SRequest_SetTaskNote.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ModifyTask))
			response.data = taskService.ModifyTask(SRequest_ModifyTask.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ModifyTaskComment))
			response.data = taskService.ModifyTaskComment(SRequest_ModifyTaskComment.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.AddTaskScore))
			response.data = taskService.AddTaskScore(SRequest_AddTaskScore.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.SetTaskOpen))
			response.data = taskService.SetTaskOpen(SRequest_SetTaskOpen.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.RandomOpenTask))
			response.data = taskService.RandomOpenTask(SRequest_RandomOpenTask.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.FileDelete))
			response.data = fileService.FileDelete(SRequest_FileDelete.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.FileList))
			response.data = fileService.FileList(SRequest_FileList.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.FileExists))
			response.data = fileService.FileExists(SRequest_FileExists.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.FileCopy))
			response.data = fileService.FileCopy(SRequest_FileCopy.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.SearchTopics))
			response.data = topicService.SearchTopics(SRequest_SearchTopics.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetTopicStyles))
//			response.data = xxxxxxxxService.GetTopicStyles(SRequest_GetTopicStyles.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetKnowledges))
//			response.data = xxxxxxxxService.GetKnowledges(SRequest_GetKnowledges.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.SvnGetDir))
			response.data = svnService.SvnGetDir(SRequest_SvnGetDir.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.TopicRecordGetList))
			response.data = topicRecordService.TopicRecordGetList(SRequest_TopicRecordGetList.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TopicRecordGet))
			response.data = topicRecordService.TopicRecordGet(SRequest_TopicRecordGet.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TopicRecordSetFav))
			response.data = topicRecordService.TopicRecordSetFav(SRequest_TopicRecordSetFav.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TopicRecordSetResult))
			response.data = topicRecordService.TopicRecordSetResult(SRequest_TopicRecordSetResult.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TopicRecordDelete))
			response.data = topicRecordService.TopicRecordDelete(SRequest_TopicRecordDelete.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.TeachStart))
			response.data = teachRecordService.TeachStart(SRequest_TeachStart.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TeachOverByTeacher))
			response.data = teachRecordService.TeachOverByTeacher(SRequest_TeachOverByTeacher.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TeachQuestionByTeacher))
			response.data = teachRecordService.TeachQuestionByTeacher(SRequest_TeachQuestionByTeacher.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TeachOverByStudent))
			response.data = teachRecordService.TeachOverByStudent(SRequest_TeachOverByStudent.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TeachConfirmByStudent))
			response.data = teachRecordService.TeachConfirmByStudent(SRequest_TeachConfirmByStudent.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TeachQuestionByStudent))
			response.data = teachRecordService.TeachQuestionByStudent(SRequest_TeachQuestionByStudent.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.TeachGetList))
			response.data = teachRecordService.TeachGetList(SRequest_TeachGetList.load(request.data)).saveToStr();

//		else if (request.handleId.equals(SHandleId.GetMatterList))
//			response.data = xxxxxxxxService.GetMatterList(SRequest_GetMatterList.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.AddModifyMatter))
//			response.data = xxxxxxxxService.AddModifyMatter(SRequest_AddModifyMatter.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.SwapMatter))
//			response.data = xxxxxxxxService.SwapMatter(SRequest_SwapMatter.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.DeleteMatter))
//			response.data = xxxxxxxxService.DeleteMatter(SRequest_DeleteMatter.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetBuyList))
//			response.data = xxxxxxxxService.GetBuyList(SRequest_GetBuyList.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.AddModifyBuy))
//			response.data = xxxxxxxxService.AddModifyBuy(SRequest_AddModifyBuy.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.DeleteBuy))
//			response.data = xxxxxxxxService.DeleteBuy(SRequest_DeleteBuy.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetCommentList))
//			response.data = xxxxxxxxService.GetCommentList(SRequest_GetCommentList.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.AddModifyComment))
//			response.data = xxxxxxxxService.AddModifyComment(SRequest_AddModifyComment.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.DeleteComment))
//			response.data = xxxxxxxxService.DeleteComment(SRequest_DeleteComment.load(request.data)).saveToStr();

//		else if (request.handleId.equals(SHandleId.GetGoodsList))
//			response.data = xxxxxxxxService.GetGoodsList(SRequest_GetGoodsList.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.AddModifyGoods))
//			response.data = xxxxxxxxService.AddModifyGoods(SRequest_AddModifyGoods.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.SwapGoods))
//			response.data = xxxxxxxxService.SwapGoods(SRequest_SwapGoods.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.DeleteGoods))
//			response.data = xxxxxxxxService.DeleteGoods(SRequest_DeleteGoods.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetOrderList))
//			response.data = xxxxxxxxService.GetOrderList(SRequest_GetOrderList.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.AddOrder))
//			response.data = xxxxxxxxService.AddOrder(SRequest_AddOrder.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ModifyOrderState))
//			response.data = xxxxxxxxService.ModifyOrderState(SRequest_ModifyOrderState.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.GetTaskGroupList))
			response.data = taskGroupService.GetTaskGroupList(SRequest_GetTaskGroupList.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.AddModifyTaskGroup))
			response.data = taskGroupService.AddModifyTaskGroup(SRequest_AddModifyTaskGroup.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.SwapTaskGroup))
			response.data = taskGroupService.SwapTaskGroup(SRequest_SwapTaskGroup.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.DeleteTaskGroup))
			response.data = taskGroupService.DeleteTaskGroup(SRequest_DeleteTaskGroup.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.GetTeachList))
			response.data = teachService.GetTeachList(SRequest_GetTeachList.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ModifyTeach))
			response.data = teachService.ModifyTeach(SRequest_ModifyTeach.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.AddEvent))
			response.data = eventService.AddEvent(SRequest_AddEvent.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.GetEventList))
			response.data = eventService.GetEventList(SRequest_GetEventList.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.LiveGetAll))
			response.data = liveService.LiveGetAll(SRequest_LiveGetAll.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.LiveGetList))
			response.data = liveService.LiveGetList(SRequest_LiveGetList.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.LiveAddModify))
			response.data = liveService.LiveAddModify(SRequest_LiveAddModify.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.LiveDelete))
			response.data = liveService.LiveDelete(SRequest_LiveDelete.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.LiveCopy))
			response.data = liveService.LiveCopy(SRequest_LiveCopy.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.ExamPointGetAll))
			response.data = examPointService.ExamPointGetAll(SRequest_ExamPointGetAll.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ExamPointAddModify))
			response.data = examPointService.ExamPointAddModify(SRequest_ExamPointAddModify.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ExamPointDelete))
			response.data = examPointService.ExamPointDelete(SRequest_ExamPointDelete.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.ExamPaperGetAll))
			response.data = examPaperService.ExamPaperGetAll(SRequest_ExamPaperGetAll.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ExamPaperAddModify))
			response.data = examPaperService.ExamPaperAddModify(SRequest_ExamPaperAddModify.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ExamPaperDelete))
			response.data = examPaperService.ExamPaperDelete(SRequest_ExamPaperDelete.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ExamPaperDetail))
			response.data = examPaperService.ExamPaperDetail(SRequest_ExamPaperDetail.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.ExamRecordGetAll))
			response.data = examRecordService.ExamRecordGetAll(SRequest_ExamRecordGetAll.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ExamRecordAddModify))
			response.data = examRecordService.ExamRecordAddModify(SRequest_ExamRecordAddModify.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ExamRecordDelete))
			response.data = examRecordService.ExamRecordDelete(SRequest_ExamRecordDelete.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ExamRecordDetail))
			response.data = examRecordService.ExamRecordDetail(SRequest_ExamRecordDetail.load(request.data)).saveToStr();

		else if (request.handleId.equals(SHandleId.UserGroupGetAll))
			response.data = userGroupService.UserGroupGetAll(SRequest_UserGroupGetAll.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.UserGroupAddModify))
			response.data = userGroupService.UserGroupAddModify(SRequest_UserGroupAddModify.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.UserGroupDelete))
			response.data = userGroupService.UserGroupDelete(SRequest_UserGroupDelete.load(request.data)).saveToStr();

//		else if (request.handleId.equals(SHandleId.ZXCreateTask))
//			response.data = xxxxxxxxService.ZXCreateTask(SRequest_ZXCreateTask.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXPullTask))
//			response.data = xxxxxxxxService.ZXPullTask(SRequest_ZXPullTask.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXCatchPageOver))
//			response.data = xxxxxxxxService.ZXCatchPageOver(SRequest_ZXCatchPageOver.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXCatchOver))
//			response.data = xxxxxxxxService.ZXCatchOver(SRequest_ZXCatchOver.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXGenKnowledgeTree))
//			response.data = xxxxxxxxService.ZXGenKnowledgeTree(SRequest_ZXGenKnowledgeTree.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXGenChapterTree))
//			response.data = xxxxxxxxService.ZXGenChapterTree(SRequest_ZXGenChapterTree.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXAddTopic))
//			response.data = xxxxxxxxService.ZXAddTopic(SRequest_ZXAddTopic.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXPullAccount))
//			response.data = xxxxxxxxService.ZXPullAccount(SRequest_ZXPullAccount.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXReportAccount))
//			response.data = xxxxxxxxService.ZXReportAccount(SRequest_ZXReportAccount.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXDeviceList))
//			response.data = xxxxxxxxService.ZXDeviceList(SRequest_ZXDeviceList.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.ZXTickInfo))
//			response.data = xxxxxxxxService.ZXTickInfo(SRequest_ZXTickInfo.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ZXProcessJuan))
			response.data = zhiXueService.ZXProcessJuan(SRequest_ZXProcessJuan.load(request.data)).saveToStr();
		else if (request.handleId.equals(SHandleId.ZXProcessJuan2))
			response.data = zhiXueService.ZXProcessJuan2(SRequest_ZXProcessJuan2.load(request.data)).saveToStr();

//		else if (request.handleId.equals(SHandleId.GetServerLog))
//			response.data = xxxxxxxxService.GetServerLog(SRequest_GetServerLog.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetServerState))
//			response.data = xxxxxxxxService.GetServerState(SRequest_GetServerState.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.SetServerState))
//			response.data = xxxxxxxxService.SetServerState(SRequest_SetServerState.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetTimeRecord))
//			response.data = xxxxxxxxService.GetTimeRecord(SRequest_GetTimeRecord.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetServerUploadingList))
//			response.data = xxxxxxxxService.GetServerUploadingList(SRequest_GetServerUploadingList.load(request.data)).saveToStr();

		else
			throw new ApiRuntimeException("接口 " + SHandleId.name(request.handleId) + " 未定义");

		return response;
	}

//	@ExceptionHandler(ApiRuntimeException.class)
//	@ResponseBody
//	@ResponseStatus(value = HttpStatus.OK)
//	public SProtocol handlerApiException(ApiRuntimeException ex)
//	{
//		LOG.v("result:--------- " + "handlerApiException" + " --------" + ex.getMsg());
//		SProtocol response = new SProtocol();
//		response.code = -1;
//		response.msg = ex.getMsg();
//		return response;
//	}

}