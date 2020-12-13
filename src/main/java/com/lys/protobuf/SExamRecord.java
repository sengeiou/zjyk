package com.lys.protobuf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.JsonHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.google.protobuf.ByteString;

import com.lys.base.utils.SPTData;
import com.lys.protobuf.ProtocolExam.ExamRecord;

public class SExamRecord extends SPTData<ExamRecord>
{
	private static final SExamRecord DefaultInstance = new SExamRecord();

	public String id = null;
	public String studentName = null;
	public String paperId = null;
	public SExamPaper paper = null;
	public Integer selectTime = 0;
	public Integer gapTime = 0;
	public Integer answerTime = 0;
	public /*SExamMindset*/ Integer mindset = ExamRecord.getDefaultInstance().getMindset().getNumber();
	public String note = null;
	public List<SExamRecordTopic> topics = new ArrayList<SExamRecordTopic>();
	public String noteType = null;
	public String notePoint = null;
	public String noteDifficulty = null;
	public String noteErrorType = null;
	public String noteOffer = null;
	public String noteAll = null;

	public static SExamRecord create(String id, String studentName, String paperId, SExamPaper paper, Integer selectTime, Integer gapTime, Integer answerTime, Integer mindset, String note, String noteType, String notePoint, String noteDifficulty, String noteErrorType, String noteOffer, String noteAll)
	{
		SExamRecord obj = new SExamRecord();
		obj.id = id;
		obj.studentName = studentName;
		obj.paperId = paperId;
		obj.paper = paper;
		obj.selectTime = selectTime;
		obj.gapTime = gapTime;
		obj.answerTime = answerTime;
		obj.mindset = mindset;
		obj.note = note;
		obj.noteType = noteType;
		obj.notePoint = notePoint;
		obj.noteDifficulty = noteDifficulty;
		obj.noteErrorType = noteErrorType;
		obj.noteOffer = noteOffer;
		obj.noteAll = noteAll;
		return obj;
	}

	public SExamRecord clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamRecord _other_)
	{
		this.id = _other_.id;
		this.studentName = _other_.studentName;
		this.paperId = _other_.paperId;
		this.paper = _other_.paper;
		this.selectTime = _other_.selectTime;
		this.gapTime = _other_.gapTime;
		this.answerTime = _other_.answerTime;
		this.mindset = _other_.mindset;
		this.note = _other_.note;
		this.topics = _other_.topics;
		this.noteType = _other_.noteType;
		this.notePoint = _other_.notePoint;
		this.noteDifficulty = _other_.noteDifficulty;
		this.noteErrorType = _other_.noteErrorType;
		this.noteOffer = _other_.noteOffer;
		this.noteAll = _other_.noteAll;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("studentName"))
			studentName = _json_.getString("studentName");
		if (_json_.containsKey("paperId"))
			paperId = _json_.getString("paperId");
		if (_json_.containsKey("paper"))
			paper = SExamPaper.load(_json_.getJSONObject("paper"));
		if (_json_.containsKey("selectTime"))
			selectTime = _json_.getInteger("selectTime");
		if (_json_.containsKey("gapTime"))
			gapTime = _json_.getInteger("gapTime");
		if (_json_.containsKey("answerTime"))
			answerTime = _json_.getInteger("answerTime");
		if (_json_.containsKey("mindset"))
			mindset = _json_.getInteger("mindset");
		if (_json_.containsKey("note"))
			note = _json_.getString("note");
		if (_json_.containsKey("topics"))
			topics = SExamRecordTopic.loadList(_json_.getJSONArray("topics"));
		if (_json_.containsKey("noteType"))
			noteType = _json_.getString("noteType");
		if (_json_.containsKey("notePoint"))
			notePoint = _json_.getString("notePoint");
		if (_json_.containsKey("noteDifficulty"))
			noteDifficulty = _json_.getString("noteDifficulty");
		if (_json_.containsKey("noteErrorType"))
			noteErrorType = _json_.getString("noteErrorType");
		if (_json_.containsKey("noteOffer"))
			noteOffer = _json_.getString("noteOffer");
		if (_json_.containsKey("noteAll"))
			noteAll = _json_.getString("noteAll");
	}

	public static SExamRecord load(String str)
	{
		try
		{
			SExamRecord obj = new SExamRecord();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamRecord load(JSONObject json)
	{
		try
		{
			SExamRecord obj = new SExamRecord();
			obj.parse(json);
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject saveToJson()
	{
		try
		{
			JSONObject _json_ = new JSONObject(true);
			if (id != null)
				_json_.put("id", id);
			if (studentName != null)
				_json_.put("studentName", studentName);
			if (paperId != null)
				_json_.put("paperId", paperId);
			if (paper != null)
				_json_.put("paper", paper.saveToJson());
			if (selectTime != null)
				_json_.put("selectTime", selectTime);
			if (gapTime != null)
				_json_.put("gapTime", gapTime);
			if (answerTime != null)
				_json_.put("answerTime", answerTime);
			if (mindset != null)
				_json_.put("mindset", mindset);
			if (note != null)
				_json_.put("note", note);
			if (topics != null)
				_json_.put("topics", SExamRecordTopic.saveList(topics));
			if (noteType != null)
				_json_.put("noteType", noteType);
			if (notePoint != null)
				_json_.put("notePoint", notePoint);
			if (noteDifficulty != null)
				_json_.put("noteDifficulty", noteDifficulty);
			if (noteErrorType != null)
				_json_.put("noteErrorType", noteErrorType);
			if (noteOffer != null)
				_json_.put("noteOffer", noteOffer);
			if (noteAll != null)
				_json_.put("noteAll", noteAll);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SExamRecord> loadList(JSONArray ja)
	{
		try
		{
			List<SExamRecord> list = new ArrayList<SExamRecord>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamRecord item = SExamRecord.load(jo);
				if (item == null)
					return null;
				list.add(item);
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray saveList(List<SExamRecord> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamRecord item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamRecord _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasStudentName())
			studentName = _proto_.getStudentName();
		if (_proto_.hasPaperId())
			paperId = _proto_.getPaperId();
		if (_proto_.hasPaper())
			paper = SExamPaper.load(_proto_.getPaper());
		if (_proto_.hasSelectTime())
			selectTime = _proto_.getSelectTime();
		if (_proto_.hasGapTime())
			gapTime = _proto_.getGapTime();
		if (_proto_.hasAnswerTime())
			answerTime = _proto_.getAnswerTime();
		if (_proto_.hasMindset())
			mindset = _proto_.getMindset().getNumber();
		if (_proto_.hasNote())
			note = _proto_.getNote();
		for (int i = 0; i < _proto_.getTopicsCount(); i++)
			topics.add(SExamRecordTopic.load(_proto_.getTopics(i)));
		if (_proto_.hasNoteType())
			noteType = _proto_.getNoteType();
		if (_proto_.hasNotePoint())
			notePoint = _proto_.getNotePoint();
		if (_proto_.hasNoteDifficulty())
			noteDifficulty = _proto_.getNoteDifficulty();
		if (_proto_.hasNoteErrorType())
			noteErrorType = _proto_.getNoteErrorType();
		if (_proto_.hasNoteOffer())
			noteOffer = _proto_.getNoteOffer();
		if (_proto_.hasNoteAll())
			noteAll = _proto_.getNoteAll();
	}

	public static SExamRecord load(byte[] bytes)
	{
		try
		{
			SExamRecord obj = new SExamRecord();
			obj.parse(ExamRecord.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamRecord load(ExamRecord proto)
	{
		try
		{
			SExamRecord obj = new SExamRecord();
			obj.parse(proto);
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ExamRecord saveToProto()
	{
		ExamRecord.Builder _builder_ = ExamRecord.newBuilder();
		if (id != null && !id.equals(ExamRecord.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (studentName != null && !studentName.equals(ExamRecord.getDefaultInstance().getStudentName()))
			_builder_.setStudentName(studentName);
		if (paperId != null && !paperId.equals(ExamRecord.getDefaultInstance().getPaperId()))
			_builder_.setPaperId(paperId);
		if (paper != null)
			_builder_.setPaper(paper.saveToProto());
		if (selectTime != null && !selectTime.equals(ExamRecord.getDefaultInstance().getSelectTime()))
			_builder_.setSelectTime(selectTime);
		if (gapTime != null && !gapTime.equals(ExamRecord.getDefaultInstance().getGapTime()))
			_builder_.setGapTime(gapTime);
		if (answerTime != null && !answerTime.equals(ExamRecord.getDefaultInstance().getAnswerTime()))
			_builder_.setAnswerTime(answerTime);
		if (mindset != null && ExamRecord.getDefaultInstance().getMindset().getNumber() != mindset)
			_builder_.setMindset(ProtocolExam.ExamMindset.valueOf(mindset));
		if (note != null && !note.equals(ExamRecord.getDefaultInstance().getNote()))
			_builder_.setNote(note);
		if (topics != null)
			for (SExamRecordTopic _value_ : topics)
				_builder_.addTopics(_value_.saveToProto());
		if (noteType != null && !noteType.equals(ExamRecord.getDefaultInstance().getNoteType()))
			_builder_.setNoteType(noteType);
		if (notePoint != null && !notePoint.equals(ExamRecord.getDefaultInstance().getNotePoint()))
			_builder_.setNotePoint(notePoint);
		if (noteDifficulty != null && !noteDifficulty.equals(ExamRecord.getDefaultInstance().getNoteDifficulty()))
			_builder_.setNoteDifficulty(noteDifficulty);
		if (noteErrorType != null && !noteErrorType.equals(ExamRecord.getDefaultInstance().getNoteErrorType()))
			_builder_.setNoteErrorType(noteErrorType);
		if (noteOffer != null && !noteOffer.equals(ExamRecord.getDefaultInstance().getNoteOffer()))
			_builder_.setNoteOffer(noteOffer);
		if (noteAll != null && !noteAll.equals(ExamRecord.getDefaultInstance().getNoteAll()))
			_builder_.setNoteAll(noteAll);
		return _builder_.build();
	}
}
