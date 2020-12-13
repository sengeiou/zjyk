package com.lys.app.controller;

import com.lys.app.AppApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/run")
public class RunController
{
	@GetMapping("/page")
	public ModelAndView manager()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsp/run");
		mv.addObject("startTime", AppApplication.startTime);
		mv.addObject("stop", AppApplication.stop);
		mv.addObject("logicRunningCount", LogicController.runningCount);
		return mv;
	}

	@RequestMapping("/startTime")
	@ResponseBody
	public Long startTime()
	{
		return AppApplication.startTime;
	}

	@RequestMapping("/isStop")
	@ResponseBody
	public Boolean isStop()
	{
		return AppApplication.stop;
	}

	@RequestMapping("/swapStop")
	@ResponseBody
	public Boolean swapStop()
	{
		AppApplication.stop = !AppApplication.stop;
		return AppApplication.stop;
	}

//	@Autowired
//	private RunService runService = null;
//
//	@PostMapping("/api")
//	@ResponseBody
//	public SProtocol api(@RequestBody SProtocol request)
//	{
////		LOG.v(String.format("receive:------ %s ---- %s ----- %s ------", SHandleId.name(request.handleId), request.userId, request.userName));
//
//		SProtocol response = new SProtocol();
//		response.code = 200;
//
//		if (request.handleId.equals(SHandleId.GetServerLog))
//			response.data = runService.GetServerLog(SRequest_GetServerLog.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetServerState))
//			response.data = runService.GetServerState(SRequest_GetServerState.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.SetServerState))
//			response.data = runService.SetServerState(SRequest_SetServerState.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetTimeRecord))
//			response.data = runService.GetTimeRecord(SRequest_GetTimeRecord.load(request.data)).saveToStr();
//		else if (request.handleId.equals(SHandleId.GetServerUploadingList))
//			response.data = runService.GetServerUploadingList(SRequest_GetServerUploadingList.load(request.data)).saveToStr();
//
////		LOG.v("result:--------- " + response.code + " --------" + (response.code != 200 ? response.msg : ""));
//
//		return response;
//	}
}