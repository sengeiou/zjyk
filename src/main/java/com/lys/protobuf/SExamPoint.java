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
import com.lys.protobuf.ProtocolExam.ExamPoint;

public class SExamPoint extends SPTData<ExamPoint>
{
	private static final SExamPoint DefaultInstance = new SExamPoint();

	public String id = null;
	public String chapter = null;
	public String point = null;
	public Integer difficulty = 0; // 1-5
	public Integer importance = 0; // 1-5
	public String frequency = null; // 考频
	public String note = null;

	public static SExamPoint create(String id, String chapter, String point, Integer difficulty, Integer importance, String frequency, String note)
	{
		SExamPoint obj = new SExamPoint();
		obj.id = id;
		obj.chapter = chapter;
		obj.point = point;
		obj.difficulty = difficulty;
		obj.importance = importance;
		obj.frequency = frequency;
		obj.note = note;
		return obj;
	}

	public SExamPoint clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamPoint _other_)
	{
		this.id = _other_.id;
		this.chapter = _other_.chapter;
		this.point = _other_.point;
		this.difficulty = _other_.difficulty;
		this.importance = _other_.importance;
		this.frequency = _other_.frequency;
		this.note = _other_.note;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("chapter"))
			chapter = _json_.getString("chapter");
		if (_json_.containsKey("point"))
			point = _json_.getString("point");
		if (_json_.containsKey("difficulty"))
			difficulty = _json_.getInteger("difficulty");
		if (_json_.containsKey("importance"))
			importance = _json_.getInteger("importance");
		if (_json_.containsKey("frequency"))
			frequency = _json_.getString("frequency");
		if (_json_.containsKey("note"))
			note = _json_.getString("note");
	}

	public static SExamPoint load(String str)
	{
		try
		{
			SExamPoint obj = new SExamPoint();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamPoint load(JSONObject json)
	{
		try
		{
			SExamPoint obj = new SExamPoint();
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
			if (chapter != null)
				_json_.put("chapter", chapter);
			if (point != null)
				_json_.put("point", point);
			if (difficulty != null)
				_json_.put("difficulty", difficulty);
			if (importance != null)
				_json_.put("importance", importance);
			if (frequency != null)
				_json_.put("frequency", frequency);
			if (note != null)
				_json_.put("note", note);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SExamPoint> loadList(JSONArray ja)
	{
		try
		{
			List<SExamPoint> list = new ArrayList<SExamPoint>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamPoint item = SExamPoint.load(jo);
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

	public static JSONArray saveList(List<SExamPoint> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamPoint item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamPoint _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasChapter())
			chapter = _proto_.getChapter();
		if (_proto_.hasPoint())
			point = _proto_.getPoint();
		if (_proto_.hasDifficulty())
			difficulty = _proto_.getDifficulty();
		if (_proto_.hasImportance())
			importance = _proto_.getImportance();
		if (_proto_.hasFrequency())
			frequency = _proto_.getFrequency();
		if (_proto_.hasNote())
			note = _proto_.getNote();
	}

	public static SExamPoint load(byte[] bytes)
	{
		try
		{
			SExamPoint obj = new SExamPoint();
			obj.parse(ExamPoint.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamPoint load(ExamPoint proto)
	{
		try
		{
			SExamPoint obj = new SExamPoint();
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
	public ExamPoint saveToProto()
	{
		ExamPoint.Builder _builder_ = ExamPoint.newBuilder();
		if (id != null && !id.equals(ExamPoint.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (chapter != null && !chapter.equals(ExamPoint.getDefaultInstance().getChapter()))
			_builder_.setChapter(chapter);
		if (point != null && !point.equals(ExamPoint.getDefaultInstance().getPoint()))
			_builder_.setPoint(point);
		if (difficulty != null && !difficulty.equals(ExamPoint.getDefaultInstance().getDifficulty()))
			_builder_.setDifficulty(difficulty);
		if (importance != null && !importance.equals(ExamPoint.getDefaultInstance().getImportance()))
			_builder_.setImportance(importance);
		if (frequency != null && !frequency.equals(ExamPoint.getDefaultInstance().getFrequency()))
			_builder_.setFrequency(frequency);
		if (note != null && !note.equals(ExamPoint.getDefaultInstance().getNote()))
			_builder_.setNote(note);
		return _builder_.build();
	}
}
