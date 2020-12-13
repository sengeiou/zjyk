package com.lys.app.controller;

import com.lys.app.dao.ExamPaperDao;
import com.lys.app.dao.ExamRecordDao;
import com.lys.base.utils.JsonHelper;
import com.lys.protobuf.SExamPaper;
import com.lys.protobuf.SExamPaperTopic;
import com.lys.protobuf.SExamRecord;
import com.lys.protobuf.SExamRecordTopic;
import com.lys.utils.ExcelHelper;
import com.lys.utils.LOG;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/exampaper")
public class ExamPaperController
{
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath = null;

	@Value("${url.root}")
	private String urlRoot = null;

	@Autowired
	private ExamPaperDao examPaperDao = null;

	@Autowired
	private ExamRecordDao examRecordDao = null;

	@GetMapping("/page")
	public String manager()
	{
		return "jsp/exampaper";
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
		result.put("total", examPaperDao.searchCount(keywordList));
		result.put("rows", examPaperDao.search(keywordList, (page - 1) * rows, rows));
		return result;
	}

	@PostMapping("/submit")
	@ResponseBody
	public SExamPaper submit(String id, String name, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "MM/dd/yyyy HH:mm") Date time, String note, String topics)
	{
		System.out.println(this.getClass().getSimpleName() + String.format(" submit %s ...", name));

		SExamPaper data = new SExamPaper();
		data.id = id;
		data.name = name;
		data.time = time != null ? time.getTime() : 0;
		data.note = note;
		data.topics = SExamPaperTopic.loadList(JsonHelper.getJSONArray(topics));

		if (StringUtils.isEmpty(data.id))
		{
			data.id = UUID.randomUUID().toString();
			examPaperDao.insert(data);
		}
		else
		{
			examPaperDao.update(data);
		}
		return data;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id)
	{
		System.out.println(this.getClass().getSimpleName() + String.format(" delete %s ...", id));
		examPaperDao.delete(id);
	}

	@RequestMapping("/exportXls")
	@ResponseBody
	public String exportXls(String id)
	{
		SExamPaper paper = examPaperDao.get(id);

		List<SExamRecord> recordList = examRecordDao.getListByPaperId(id);

		String path = String.format("export/%s.xls", paper.name);

		File dstFile = new File(uploadPath, path);
		File dstDir = dstFile.getParentFile();
		if (!dstDir.exists())
			dstDir.mkdirs();

		Workbook wb = ExcelHelper.createExcel();
		Sheet sheet = wb.createSheet();
		packXls(sheet, paper, recordList);
		ExcelHelper.writeExcel(wb, dstFile);
		try
		{
			wb.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return urlRoot + path;
	}

	private void packXls(Sheet sheet, SExamPaper paper, List<SExamRecord> recordList)
	{
		List<SExamPaperTopic> topics = paper.topics;

		// 试题MAP
		HashMap<String, SExamPaperTopic> topicMap = new HashMap<>();
		for (SExamPaperTopic topic : topics)
		{
			topicMap.put(topic.code, topic);
		}

		int row = 0;

		{
			Row rowh = sheet.createRow(row++);
			int col = 3;
			for (SExamPaperTopic topic : topics)
			{
				rowh.createCell(col++).setCellValue(topic.code);
			}
		}

		for (SExamRecord record : recordList)
		{
			List<SExamRecordTopic> errorTopics = record.topics;

			// 错题MAP
			HashMap<String, SExamRecordTopic> errorTopicMap = new HashMap<>();
			for (SExamRecordTopic errorTopic : errorTopics)
			{
				errorTopicMap.put(errorTopic.code, errorTopic);
			}

			Row rowd = sheet.createRow(row++);

			int col = 0;

			rowd.createCell(col++).setCellValue(record.studentName);
			rowd.createCell(col++).setCellValue(paper.name);
			rowd.createCell(col++).setCellValue(new SimpleDateFormat("yyyy年MM月dd日").format(new Date(paper.time)));

			for (SExamPaperTopic topic : topics)
			{
				if (errorTopicMap.containsKey(topic.code))
				{
					SExamRecordTopic errorTopic = errorTopicMap.get(topic.code);
					rowd.createCell(col++).setCellValue(errorTopic.gotScore);
				}
				else
				{
					rowd.createCell(col++).setCellValue(topic.allScore);
				}
			}
		}
	}

}