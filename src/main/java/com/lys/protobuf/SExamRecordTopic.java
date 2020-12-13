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
import com.lys.protobuf.ProtocolExam.ExamRecordTopic;

public class SExamRecordTopic extends SPTData<ExamRecordTopic>
{
	private static final SExamRecordTopic DefaultInstance = new SExamRecordTopic();

	public String code = null;
	public Integer gotScore = 0;
	public /*SExamErrorType*/ Integer errorType = ExamRecordTopic.getDefaultInstance().getErrorType().getNumber();

	public static SExamRecordTopic create(String code, Integer gotScore, Integer errorType)
	{
		SExamRecordTopic obj = new SExamRecordTopic();
		obj.code = code;
		obj.gotScore = gotScore;
		obj.errorType = errorType;
		return obj;
	}

	public SExamRecordTopic clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamRecordTopic _other_)
	{
		this.code = _other_.code;
		this.gotScore = _other_.gotScore;
		this.errorType = _other_.errorType;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("code"))
			code = _json_.getString("code");
		if (_json_.containsKey("gotScore"))
			gotScore = _json_.getInteger("gotScore");
		if (_json_.containsKey("errorType"))
			errorType = _json_.getInteger("errorType");
	}

	public static SExamRecordTopic load(String str)
	{
		try
		{
			SExamRecordTopic obj = new SExamRecordTopic();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamRecordTopic load(JSONObject json)
	{
		try
		{
			SExamRecordTopic obj = new SExamRecordTopic();
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
			if (gotScore != null)
				_json_.put("gotScore", gotScore);
			if (errorType != null)
				_json_.put("errorType", errorType);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SExamRecordTopic> loadList(JSONArray ja)
	{
		try
		{
			List<SExamRecordTopic> list = new ArrayList<SExamRecordTopic>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamRecordTopic item = SExamRecordTopic.load(jo);
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

	public static JSONArray saveList(List<SExamRecordTopic> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamRecordTopic item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamRecordTopic _proto_)
	{
		if (_proto_.hasCode())
			code = _proto_.getCode();
		if (_proto_.hasGotScore())
			gotScore = _proto_.getGotScore();
		if (_proto_.hasErrorType())
			errorType = _proto_.getErrorType().getNumber();
	}

	public static SExamRecordTopic load(byte[] bytes)
	{
		try
		{
			SExamRecordTopic obj = new SExamRecordTopic();
			obj.parse(ExamRecordTopic.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamRecordTopic load(ExamRecordTopic proto)
	{
		try
		{
			SExamRecordTopic obj = new SExamRecordTopic();
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
	public ExamRecordTopic saveToProto()
	{
		ExamRecordTopic.Builder _builder_ = ExamRecordTopic.newBuilder();
		if (code != null && !code.equals(ExamRecordTopic.getDefaultInstance().getCode()))
			_builder_.setCode(code);
		if (gotScore != null && !gotScore.equals(ExamRecordTopic.getDefaultInstance().getGotScore()))
			_builder_.setGotScore(gotScore);
		if (errorType != null && ExamRecordTopic.getDefaultInstance().getErrorType().getNumber() != errorType)
			_builder_.setErrorType(ProtocolExam.ExamErrorType.valueOf(errorType));
		return _builder_.build();
	}
}
