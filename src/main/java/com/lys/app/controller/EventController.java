package com.lys.app.controller;

import com.lys.app.dao.EventDao;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/event")
public class EventController
{
//	@Autowired
//	private UserService userService = null;

	@Autowired
	private EventDao eventDao = null;

	@GetMapping("/page")
	public String manager()
	{
		return "jsp/event";
	}

	@RequestMapping("/search")
	@ResponseBody
	public Map<String, Object> search(String userId, String action, String target, //
									  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy年MM月dd日") Date beginTime, //
									  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy年MM月dd日") Date endTime, //
									  String keywords, Integer page, Integer rows)
	{
		LOG.v(this.getClass().getSimpleName() + String.format(" search ..."));

		LOG.v("userId	" + userId);
		LOG.v("action	" + action);
		LOG.v("target	" + target);
		LOG.v("beginTime	" + beginTime);
		LOG.v("endTime	" + endTime);
		LOG.v("keywords	" + keywords);
		LOG.v("page	" + page);
		LOG.v("rows	" + rows);

		List<String> keywordList = new ArrayList<>();
		if (!StringUtils.isEmpty(keywords))
		{
			for (String keyword : keywords.split("\\s+"))
			{
				if (!StringUtils.isEmpty(keyword))
					keywordList.add(keyword);
			}
		}
		LOG.v(keywordList);

		Map<String, Object> result = new HashMap<>();
		result.put("total", eventDao.searchCount(userId, action, target, beginTime != null ? beginTime.getTime() : null, endTime != null ? endTime.getTime() : null, keywordList));
		result.put("rows", eventDao.search(userId, action, target, beginTime != null ? beginTime.getTime() : null, endTime != null ? endTime.getTime() : null, keywordList, (page - 1) * rows, rows));
		return result;
	}

//	@RequestMapping("/reg")
//	@ResponseBody
//	public SUser reg()
//	{
//		LOG.v(this.getClass().getSimpleName() + String.format(" reg ..."));
//		return userService.reg();
//	}
//
//	@RequestMapping("/login")
//	@ResponseBody
//	public SUser login(String id)
//	{
//		LOG.v(this.getClass().getSimpleName() + String.format(" login %s ...", id));
//		return userService.login(id);
//	}
//
//	@RequestMapping("/modify")
//	@ResponseBody
//	public SUser modify(SUser user)
//	{
//		LOG.v(this.getClass().getSimpleName() + String.format(" modify %s ...", user.id));
//		return userService.modify(user);
//	}
}