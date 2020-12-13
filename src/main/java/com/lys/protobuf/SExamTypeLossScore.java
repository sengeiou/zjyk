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
import com.lys.protobuf.ProtocolExam.ExamTypeLossScore;

public class SExamTypeLossScore extends SPTData<ExamTypeLossScore>
{
	private static final SExamTypeLossScore DefaultInstance = new SExamTypeLossScore();

	public /*STopicType*/ Integer type = ExamTypeLossScore.getDefaultInstance().getType().getNumber();
	public Integer allScore = 0;
	public Integer lossScore = 0;

	public static SExamTypeLossScore create(Integer type, Integer allScore, Integer lossScore)
	{
		SExamTypeLossScore obj = new SExamTypeLossScore();
		obj.type = type;
		obj.allScore = allScore;
		obj.lossScore = lossScore;
		return obj;
	}

	public SExamTypeLossScore clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamTypeLossScore _other_)
	{
		this.type = _other_.type;
		this.allScore = _other_.allScore;
		this.lossScore = _other_.lossScore;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("type"))
			type = _json_.getInteger("type");
		if (_json_.containsKey("allScore"))
			allScore = _json_.getInteger("allScore");
		if (_json_.containsKey("lossScore"))
			lossScore = _json_.getInteger("lossScore");
	}

	public static SExamTypeLossScore load(String str)
	{
		try
		{
			SExamTypeLossScore obj = new SExamTypeLossScore();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamTypeLossScore load(JSONObject json)
	{
		try
		{
			SExamTypeLossScore obj = new SExamTypeLossScore();
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
			if (type != null)
				_json_.put("type", type);
			if (allScore != null)
				_json_.put("allScore", allScore);
			if (lossScore != null)
				_json_.put("lossScore", lossScore);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SExamTypeLossScore> loadList(JSONArray ja)
	{
		try
		{
			List<SExamTypeLossScore> list = new ArrayList<SExamTypeLossScore>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamTypeLossScore item = SExamTypeLossScore.load(jo);
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

	public static JSONArray saveList(List<SExamTypeLossScore> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamTypeLossScore item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamTypeLossScore _proto_)
	{
		if (_proto_.hasType())
			type = _proto_.getType().getNumber();
		if (_proto_.hasAllScore())
			allScore = _proto_.getAllScore();
		if (_proto_.hasLossScore())
			lossScore = _proto_.getLossScore();
	}

	public static SExamTypeLossScore load(byte[] bytes)
	{
		try
		{
			SExamTypeLossScore obj = new SExamTypeLossScore();
			obj.parse(ExamTypeLossScore.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamTypeLossScore load(ExamTypeLossScore proto)
	{
		try
		{
			SExamTypeLossScore obj = new SExamTypeLossScore();
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
	public ExamTypeLossScore saveToProto()
	{
		ExamTypeLossScore.Builder _builder_ = ExamTypeLossScore.newBuilder();
		if (type != null && ExamTypeLossScore.getDefaultInstance().getType().getNumber() != type)
			_builder_.setType(ProtocolExam.TopicType.valueOf(type));
		if (allScore != null && !allScore.equals(ExamTypeLossScore.getDefaultInstance().getAllScore()))
			_builder_.setAllScore(allScore);
		if (lossScore != null && !lossScore.equals(ExamTypeLossScore.getDefaultInstance().getLossScore()))
			_builder_.setLossScore(lossScore);
		return _builder_.build();
	}
}
