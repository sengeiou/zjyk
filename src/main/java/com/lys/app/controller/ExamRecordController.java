package com.lys.app.controller;

import com.lys.app.dao.ExamPointDao;
import com.lys.app.dao.ExamRecordDao;
import com.lys.base.utils.JsonHelper;
import com.lys.protobuf.*;
import com.lys.utils.ExcelHelper;
import com.lys.utils.LOG;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/examrecord")
public class ExamRecordController
{
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath = null;

	@Value("${url.root}")
	private String urlRoot = null;

	@Autowired
	private ExamRecordDao examRecordDao = null;

	@Autowired
	private ExamPointDao examPointDao = null;

	@GetMapping("/page")
	public String manager()
	{
		return "jsp/examrecord";
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
		result.put("total", examRecordDao.searchCount(keywordList));
		result.put("rows", examRecordDao.search(keywordList, (page - 1) * rows, rows));
		return result;
	}

	@PostMapping("/submit")
	@ResponseBody
	public SExamRecord submit(String id, String studentName, String paperId, Integer selectTime, Integer gapTime, Integer answerTime, Integer mindset, String note, String topics)
	{
		System.out.println(this.getClass().getSimpleName() + String.format(" submit %s ...", studentName));

		SExamRecord data = null;

		if (!StringUtils.isEmpty(id))
			data = examRecordDao.get(id);
		if (data == null)
			data = new SExamRecord();

		data.id = id;
		data.studentName = studentName;
		data.paperId = paperId;
		data.selectTime = selectTime;
		data.gapTime = gapTime;
		data.answerTime = answerTime;
		data.mindset = mindset;
		data.note = note;
		data.topics = SExamRecordTopic.loadList(JsonHelper.getJSONArray(topics));

		if (StringUtils.isEmpty(data.id))
		{
			data.id = UUID.randomUUID().toString();
			examRecordDao.insert(data);
		}
		else
		{
			examRecordDao.update(data);
		}
		return data;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id)
	{
		System.out.println(this.getClass().getSimpleName() + String.format(" delete %s ...", id));
		examRecordDao.delete(id);
	}

	@RequestMapping("/savedp")
	@ResponseBody
	public void savedp(String id, String noteAll, String noteType, String notePoint, String noteDifficulty, String noteErrorType, String noteOffer)
	{
		System.out.println(this.getClass().getSimpleName() + String.format(" savedp %s ...", id));

		LOG.v("noteAll : " + noteAll);
		LOG.v("noteType : " + noteType);
		LOG.v("notePoint : " + notePoint);
		LOG.v("noteDifficulty : " + noteDifficulty);
		LOG.v("noteErrorType : " + noteErrorType);
		LOG.v("noteOffer : " + noteOffer);

		if (!StringUtils.isEmpty(id))
		{
			SExamRecord record = examRecordDao.get(id);
			if (record != null)
			{
				record.noteAll = noteAll;
				record.noteType = noteType;
				record.notePoint = notePoint;
				record.noteDifficulty = noteDifficulty;
				record.noteErrorType = noteErrorType;
				record.noteOffer = noteOffer;
				examRecordDao.update(record);
			}
		}
	}

	private void examdetail(ModelAndView mv, String id)
	{
		SExamRecord record = examRecordDao.get(id);
		if (record != null)
		{
			SExamPaper paper = record.paper;
			List<SExamPaperTopic> topics = paper.topics;
			List<SExamRecordTopic> errorTopics = record.topics;

			// 试题MAP
			HashMap<String, SExamPaperTopic> topicMap = new HashMap<>();
			for (SExamPaperTopic topic : topics)
			{
				topicMap.put(topic.code, topic);
			}

			// 错题MAP
			HashMap<String, SExamRecordTopic> errorTopicMap = new HashMap<>();
			for (SExamRecordTopic errorTopic : errorTopics)
			{
				errorTopicMap.put(errorTopic.code, errorTopic);
			}

			// 考点MAP
			HashMap<String, SExamPoint> pointMap = new HashMap<>();
			for (SExamRecordTopic errorTopic : errorTopics)
			{
				SExamPaperTopic topic = topicMap.get(errorTopic.code);
				if (!pointMap.containsKey(topic.pointId))
				{
					pointMap.put(topic.pointId, examPointDao.get(topic.pointId));
				}
			}

			//-------------------------------------- 得分率 ---------------------------------------

			ArrayList<SExamCodeLossScore> codeLossScoreList = new ArrayList<>();
			for (SExamPaperTopic topic : topics)
			{
				if (errorTopicMap.containsKey(topic.code))
				{
					SExamRecordTopic errorTopic = errorTopicMap.get(topic.code);
					codeLossScoreList.add(SExamCodeLossScore.create(topic.code, 1f * errorTopic.gotScore / topic.allScore));
				}
				else
				{
					codeLossScoreList.add(SExamCodeLossScore.create(topic.code, 1f));
				}
			}

			// LOG
			for (SExamCodeLossScore codeLossScore : codeLossScoreList)
			{
				LOG.v(String.format("难度：%s\t得分率：%s", codeLossScore.code, codeLossScore.gotLv));
			}

			//------------------------------------ 题型 -----------------------------------------

			// 失分MAP
			HashMap<Integer, SExamTypeLossScore> typeLossScoreMap = new HashMap<>();
			for (SExamPaperTopic topic : topics)
			{
				if (typeLossScoreMap.containsKey(topic.type))
				{
					SExamTypeLossScore typeLossScore = typeLossScoreMap.get(topic.type);
					typeLossScore.allScore += topic.allScore;
				}
				else
				{
					typeLossScoreMap.put(topic.type, SExamTypeLossScore.create(topic.type, topic.allScore, 0));
				}
			}
			for (SExamRecordTopic errorTopic : errorTopics)
			{
				SExamPaperTopic topic = topicMap.get(errorTopic.code);
				SExamTypeLossScore typeLossScore = typeLossScoreMap.get(topic.type);
				typeLossScore.lossScore += (topic.allScore - errorTopic.gotScore);
			}

			// 失分排序
			ArrayList<SExamTypeLossScore> typeLossScoreList = new ArrayList<>(typeLossScoreMap.values());
			Collections.sort(typeLossScoreList, new Comparator<SExamTypeLossScore>()
			{
				@Override
				public int compare(SExamTypeLossScore o1, SExamTypeLossScore o2)
				{
					return o1.type.compareTo(o2.type);
				}
			});

			// LOG
			for (SExamTypeLossScore typeLossScore : typeLossScoreList)
			{
				LOG.v(String.format("题型：%s\t总分：%s\t失分：%s", typeLossScore.type, typeLossScore.allScore, typeLossScore.lossScore));
			}

			//------------------------------------- 考点 ----------------------------------------

			// 失分MAP
			HashMap<String, SExamPointLossScore> pointLossScoreMap = new HashMap<>();
			for (SExamRecordTopic errorTopic : errorTopics)
			{
				SExamPaperTopic topic = topicMap.get(errorTopic.code);
				SExamPoint point = pointMap.get(topic.pointId);
				if (pointLossScoreMap.containsKey(point.id))
				{
					SExamPointLossScore pointLossScore = pointLossScoreMap.get(point.id);
					pointLossScore.lossScore += (topic.allScore - errorTopic.gotScore);
				}
				else
				{
					pointLossScoreMap.put(point.id, SExamPointLossScore.create(point.id, point.point, topic.allScore - errorTopic.gotScore));
				}
			}

			// 失分排序
			ArrayList<SExamPointLossScore> pointLossScoreList = new ArrayList<>(pointLossScoreMap.values());
			Collections.sort(pointLossScoreList, new Comparator<SExamPointLossScore>()
			{
				@Override
				public int compare(SExamPointLossScore o1, SExamPointLossScore o2)
				{
					return o2.lossScore.compareTo(o1.lossScore);
				}
			});

			// LOG
			for (SExamPointLossScore pointLossScore : pointLossScoreList)
			{
				LOG.v(String.format("考点：%s\t失分：%s", pointLossScore.pointName, pointLossScore.lossScore));
			}

			//-------------------------------------- 难度 ---------------------------------------

			// 失分MAP
			HashMap<Integer, SExamDifficultyLossScore> difficultyLossScoreMap = new HashMap<>();
			for (SExamPaperTopic topic : topics)
			{
				if (difficultyLossScoreMap.containsKey(topic.difficulty))
				{
					SExamDifficultyLossScore difficultyLossScore = difficultyLossScoreMap.get(topic.difficulty);
					difficultyLossScore.allScore += topic.allScore;
				}
				else
				{
					difficultyLossScoreMap.put(topic.difficulty, SExamDifficultyLossScore.create(topic.difficulty, topic.allScore, 0));
				}
			}
			for (SExamRecordTopic errorTopic : errorTopics)
			{
				SExamPaperTopic topic = topicMap.get(errorTopic.code);
				SExamDifficultyLossScore difficultyLossScore = difficultyLossScoreMap.get(topic.difficulty);
				difficultyLossScore.lossScore += (topic.allScore - errorTopic.gotScore);
			}

			// 失分排序
			ArrayList<SExamDifficultyLossScore> difficultyLossScoreList = new ArrayList<>(difficultyLossScoreMap.values());
			Collections.sort(difficultyLossScoreList, new Comparator<SExamDifficultyLossScore>()
			{
				@Override
				public int compare(SExamDifficultyLossScore o1, SExamDifficultyLossScore o2)
				{
					return o1.difficulty.compareTo(o2.difficulty);
				}
			});

			// LOG
			for (SExamDifficultyLossScore difficultyLossScore : difficultyLossScoreList)
			{
				LOG.v(String.format("难度：%s\t总分：%s\t失分：%s", difficultyLossScore.difficulty, difficultyLossScore.allScore, difficultyLossScore.lossScore));
			}

			//------------------------------------ 错误类型 -----------------------------------------

			// 失分MAP
			HashMap<Integer, SExamErrorTypeLossScore> errorTypeLossScoreMap = new HashMap<>();
			for (SExamRecordTopic errorTopic : errorTopics)
			{
				SExamPaperTopic topic = topicMap.get(errorTopic.code);
				if (errorTypeLossScoreMap.containsKey(errorTopic.errorType))
				{
					SExamErrorTypeLossScore errorTypeLossScore = errorTypeLossScoreMap.get(errorTopic.errorType);
					errorTypeLossScore.lossScore += (topic.allScore - errorTopic.gotScore);
				}
				else
				{
					errorTypeLossScoreMap.put(errorTopic.errorType, SExamErrorTypeLossScore.create(errorTopic.errorType, topic.allScore - errorTopic.gotScore));
				}
			}

			// 失分排序
			ArrayList<SExamErrorTypeLossScore> errorTypeLossScoreList = new ArrayList<>(errorTypeLossScoreMap.values());
			Collections.sort(errorTypeLossScoreList, new Comparator<SExamErrorTypeLossScore>()
			{
				@Override
				public int compare(SExamErrorTypeLossScore o1, SExamErrorTypeLossScore o2)
				{
					return o1.errorType.compareTo(o2.errorType);
				}
			});

			// LOG
			for (SExamErrorTypeLossScore errorTypeLossScore : errorTypeLossScoreList)
			{
				LOG.v(String.format("错误类型：%s\t失分：%s", errorTypeLossScore.errorType, errorTypeLossScore.lossScore));
			}

			//-----------------------------------------------------------------------------

			mv.addObject("record", record.saveToJson());

			mv.addObject("codeLossScoreList", SExamCodeLossScore.saveList(codeLossScoreList));
			mv.addObject("typeLossScoreList", SExamTypeLossScore.saveList(typeLossScoreList));
			mv.addObject("pointLossScoreList", SExamPointLossScore.saveList(pointLossScoreList));
			mv.addObject("difficultyLossScoreList", SExamDifficultyLossScore.saveList(difficultyLossScoreList));
			mv.addObject("errorTypeLossScoreList", SExamErrorTypeLossScore.saveList(errorTypeLossScoreList));
		}
	}

	public ModelAndView examdp(String id)
	{
		LOG.v("examdp " + id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsp/examdp");
		examdetail(mv, id);
		return mv;
	}

	public ModelAndView examkq(String id)
	{
		LOG.v("examkq " + id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsp/examkq");
		examdetail(mv, id);
		return mv;
	}

	@RequestMapping("/exportXls")
	@ResponseBody
	public String exportXls(String name)
	{
		List<SExamRecord> recordList = examRecordDao.getListByName(name);

		String path = String.format("export/%s.xls", name);

		File dstFile = new File(uploadPath, path);
		File dstDir = dstFile.getParentFile();
		if (!dstDir.exists())
			dstDir.mkdirs();

		Workbook wb = ExcelHelper.createExcel();
		Sheet sheet = wb.createSheet();
		packXls(sheet, recordList);
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

	private void packXls(Sheet sheet, List<SExamRecord> recordList)
	{
		int row = 0;
		for (SExamRecord record : recordList)
		{
			SExamPaper paper = record.paper;
			List<SExamPaperTopic> topics = paper.topics;
			List<SExamRecordTopic> errorTopics = record.topics;

			// 试题MAP
			HashMap<String, SExamPaperTopic> topicMap = new HashMap<>();
			for (SExamPaperTopic topic : topics)
			{
				topicMap.put(topic.code, topic);
			}

			// 错题MAP
			HashMap<String, SExamRecordTopic> errorTopicMap = new HashMap<>();
			for (SExamRecordTopic errorTopic : errorTopics)
			{
				errorTopicMap.put(errorTopic.code, errorTopic);
			}

			Row rowh = sheet.createRow(row++);
			Row rowd = sheet.createRow(row++);

			int col = 0;

			rowd.createCell(col++).setCellValue(record.studentName);
			rowd.createCell(col++).setCellValue(paper.name);
			rowd.createCell(col++).setCellValue(new SimpleDateFormat("yyyy年MM月dd日").format(new Date(paper.time)));

			for (SExamPaperTopic topic : topics)
			{
				rowh.createCell(col).setCellValue(topic.code);
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