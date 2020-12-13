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
import com.lys.protobuf.ProtocolExam.ExamPaperTopic;

public class SExamPaperTopic extends SPTData<ExamPaperTopic>
{
	private static final SExamPaperTopic DefaultInstance = new SExamPaperTopic();

	public String code = null;
	public /*STopicType*/ Integer type = ExamPaperTopic.getDefaultInstance().getType().getNumber();
	public Integer allScore = 0;
	public Integer difficulty = 0; // 1-3
	public String pointId = null;
	public SExamPoint point = null;

	public static SExamPaperTopic create(String code, Integer type, Integer allScore, Integer difficulty, String pointId, SExamPoint point)
	{
		SExamPaperTopic obj = new SExamPaperTopic();
		obj.code = code;
		obj.type = type;
		obj.allScore = allScore;
		obj.difficulty = difficulty;
		obj.pointId = pointId;
		obj.point = point;
		return obj;
	}

	public SExamPaperTopic clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamPaperTopic _other_)
	{
		this.code = _other_.code;
		this.type = _other_.type;
		this.allScore = _other_.allScore;
		this.difficulty = _other_.difficulty;
		this.pointId = _other_.pointId;
		this.point = _other_.point;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("code"))
			code = _json_.getString("code");
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("allScore"))
			allScore = _json_.getInteger("allScore");
		if (_json_.containsKey("difficulty"))
			difficulty = _json_.getInteger("difficulty");
		if (_json_.containsKey("pointId"))
			pointId = _json_.getString("pointId");
		if (_json_.containsKey("point"))
			point = SExamPoint.load(_json_.getJSONObject("point"));
	}

	public static SExamPaperTopic load(String str)
	{
		try
		{
			SExamPaperTopic obj = new SExamPaperTopic();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamPaperTopic load(JSONObject json)
	{
		try
		{
			SExamPaperTopic obj = new SExamPaperTopic();
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
			if (code != null)
				_json_.put("code", code);
			if (type != null)
				_json_.put("type", type);
			if (allScore != null)
				_json_.put("allScore", allScore);
			if (difficulty != null)
				_json_.put("difficulty", difficulty);
			if (pointId != null)
				_json_.put("pointId", pointId);
			if (point != null)
				_json_.put("point", point.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SExamPaperTopic> loadList(JSONArray ja)
	{
		try
		{
			List<SExamPaperTopic> list = new ArrayList<SExamPaperTopic>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamPaperTopic item = SExamPaperTopic.load(jo);
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

	public static JSONArray saveList(List<SExamPaperTopic> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamPaperTopic item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamPaperTopic _proto_)
	{
		if (_proto_.hasCode())
			code = _proto_.getCode();
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		if (_proto_.hasAllScore())
			allScore = _proto_.getAllScore();
		if (_proto_.hasDifficulty())
			difficulty = _proto_.getDifficulty();
		if (_proto_.hasPointId())
			pointId = _proto_.getPointId();
		if (_proto_.hasPoint())
			point = SExamPoint.load(_proto_.getPoint());
	}

	public static SExamPaperTopic load(byte[] bytes)
	{
		try
		{
			SExamPaperTopic obj = new SExamPaperTopic();
			obj.parse(ExamPaperTopic.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamPaperTopic load(ExamPaperTopic proto)
	{
		try
		{
			SExamPaperTopic obj = new SExamPaperTopic();
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
	public ExamPaperTopic saveToProto()
	{
		ExamPaperTopic.Builder _builder_ = ExamPaperTopic.newBuilder();
		if (code != null && !code.equals(ExamPaperTopic.getDefaultInstance().getCode()))
			_builder_.setCode(code);
		if (type != null && ExamPaperTopic.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolExam.TopicType.valueOf(type));
		if (allScore != null && !allScore.equals(ExamPaperTopic.getDefaultInstance().getAllScore()))
			_builder_.setAllScore(allScore);
		if (difficulty != null && !difficulty.equals(ExamPaperTopic.getDefaultInstance().getDifficulty()))
			_builder_.setDifficulty(difficulty);
		if (pointId != null && !pointId.equals(ExamPaperTopic.getDefaultInstance().getPointId()))
			_builder_.setPointId(pointId);
		if (point != null)
			_builder_.setPoint(point.saveToProto());
		return _builder_.build();
	}
}
