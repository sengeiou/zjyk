package com.lys.app.controller;

import com.lys.app.dao.ExamPointDao;
import com.lys.protobuf.SExamPoint;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/exampoint")
public class ExamPointController
{
	@Autowired
	private ExamPointDao examPointDao = null;

	@GetMapping("/page")
	public String manager()
	{
		return "jsp/exampoint";
	}

	@RequestMapping("/search")
	@ResponseBody
	public Map<String, Object> search(String keywords, Integer page, Integer rows)
	{
		LOG.v(this.getClass().getSimpleName() + String.format(" search ..."));

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
		result.put("total", examPointDao.searchCount(keywordList));
		result.put("rows", examPointDao.search(keywordList, (page - 1) * rows, rows));
		return result;
	}

	@PostMapping("/submit")
	@ResponseBody
	public SExamPoint submit(String id, String chapter, String point, Integer difficulty, Integer importance, String frequency, String note)
	{
		System.out.println(this.getClass().getSimpleName() + String.format(" submit %s ...", point));

		SExamPoint data = new SExamPoint();
		data.id = id;
		data.chapter = chapter;
		data.point = point;
		data.difficulty = difficulty;
		data.importance = importance;
		data.frequency = frequency;
		data.note = note;

		if (StringUtils.isEmpty(data.id))
		{
			data.id = UUID.randomUUID().toString();
			examPointDao.insert(data);
		}
		else
		{
			examPointDao.update(data);
		}
		return data;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id)
	{
		System.out.println(this.getClass().getSimpleName() + String.format(" delete %s ...", id));
		examPointDao.delete(id);
	}
}