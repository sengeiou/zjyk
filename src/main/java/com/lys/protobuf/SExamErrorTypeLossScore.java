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
import com.lys.protobuf.ProtocolExam.ExamErrorTypeLossScore;

public class SExamErrorTypeLossScore extends SPTData<ExamErrorTypeLossScore>
{
	private static final SExamErrorTypeLossScore DefaultInstance = new SExamErrorTypeLossScore();

	public /*SExamErrorType*/ Integer errorType = ExamErrorTypeLossScore.getDefaultInstance().getErrorType().getNumber();
	public Integer lossScore = 0;

	public static SExamErrorTypeLossScore create(Integer errorType, Integer lossScore)
	{
		SExamErrorTypeLossScore obj = new SExamErrorTypeLossScore();
		obj.errorType = errorType;
		obj.lossScore = lossScore;
		return obj;
	}

	public SExamErrorTypeLossScore clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamErrorTypeLossScore _other_)
	{
		this.errorType = _other_.errorType;
		this.lossScore = _other_.lossScore;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("errorType"))
			errorType = _json_.getInteger("errorType");
		if (_json_.containsKey("lossScore"))
			lossScore = _json_.getInteger("lossScore");
	}

	public static SExamErrorTypeLossScore load(String str)
	{
		try
		{
			SExamErrorTypeLossScore obj = new SExamErrorTypeLossScore();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamErrorTypeLossScore load(JSONObject json)
	{
		try
		{
			SExamErrorTypeLossScore obj = new SExamErrorTypeLossScore();
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
			if (errorType != null)
				_json_.put("errorType", errorType);
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

	public static List<SExamErrorTypeLossScore> loadList(JSONArray ja)
	{
		try
		{
			List<SExamErrorTypeLossScore> list = new ArrayList<SExamErrorTypeLossScore>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamErrorTypeLossScore item = SExamErrorTypeLossScore.load(jo);
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

	public static JSONArray saveList(List<SExamErrorTypeLossScore> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamErrorTypeLossScore item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamErrorTypeLossScore _proto_)
	{
		if (_proto_.hasErrorType())
			errorType = _proto_.getErrorType().getNumber();
		if (_proto_.hasLossScore())
			lossScore = _proto_.getLossScore();
	}

	public static SExamErrorTypeLossScore load(byte[] bytes)
	{
		try
		{
			SExamErrorTypeLossScore obj = new SExamErrorTypeLossScore();
			obj.parse(ExamErrorTypeLossScore.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamErrorTypeLossScore load(ExamErrorTypeLossScore proto)
	{
		try
		{
			SExamErrorTypeLossScore obj = new SExamErrorTypeLossScore();
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
	public ExamErrorTypeLossScore saveToProto()
	{
		ExamErrorTypeLossScore.Builder _builder_ = ExamErrorTypeLossScore.newBuilder();
		if (errorType != null && ExamErrorTypeLossScore.getDefaultInstance().getErrorType().getNumber() != errorType)
			_builder_.setErrorType(ProtocolExam.ExamErrorType.valueOf(errorType));
		if (lossScore != null && !lossScore.equals(ExamErrorTypeLossScore.getDefaultInstance().getLossScore()))
			_builder_.setLossScore(lossScore);
		return _builder_.build();
	}
}
