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
import com.lys.protobuf.ProtocolExam.ExamPointLossScore;

public class SExamPointLossScore extends SPTData<ExamPointLossScore>
{
	private static final SExamPointLossScore DefaultInstance = new SExamPointLossScore();

	public String pointId = null;
	public String pointName = null;
	public Integer lossScore = 0;

	public static SExamPointLossScore create(String pointId, String pointName, Integer lossScore)
	{
		SExamPointLossScore obj = new SExamPointLossScore();
		obj.pointId = pointId;
		obj.pointName = pointName;
		obj.lossScore = lossScore;
		return obj;
	}

	public SExamPointLossScore clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamPointLossScore _other_)
	{
		this.pointId = _other_.pointId;
		this.pointName = _other_.pointName;
		this.lossScore = _other_.lossScore;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("pointId"))
			pointId = _json_.getString("pointId");
		if (_json_.containsKey("pointName"))
			pointName = _json_.getString("pointName");
		if (_json_.containsKey("lossScore"))
			lossScore = _json_.getInteger("lossScore");
	}

	public static SExamPointLossScore load(String str)
	{
		try
		{
			SExamPointLossScore obj = new SExamPointLossScore();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamPointLossScore load(JSONObject json)
	{
		try
		{
			SExamPointLossScore obj = new SExamPointLossScore();
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
			if (pointId != null)
				_json_.put("pointId", pointId);
			if (pointName != null)
				_json_.put("pointName", pointName);
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

	public static List<SExamPointLossScore> loadList(JSONArray ja)
	{
		try
		{
			List<SExamPointLossScore> list = new ArrayList<SExamPointLossScore>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamPointLossScore item = SExamPointLossScore.load(jo);
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

	public static JSONArray saveList(List<SExamPointLossScore> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamPointLossScore item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamPointLossScore _proto_)
	{
		if (_proto_.hasPointId())
			pointId = _proto_.getPointId();
		if (_proto_.hasPointName())
			pointName = _proto_.getPointName();
		if (_proto_.hasLossScore())
			lossScore = _proto_.getLossScore();
	}

	public static SExamPointLossScore load(byte[] bytes)
	{
		try
		{
			SExamPointLossScore obj = new SExamPointLossScore();
			obj.parse(ExamPointLossScore.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamPointLossScore load(ExamPointLossScore proto)
	{
		try
		{
			SExamPointLossScore obj = new SExamPointLossScore();
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
	public ExamPointLossScore saveToProto()
	{
		ExamPointLossScore.Builder _builder_ = ExamPointLossScore.newBuilder();
		if (pointId != null && !pointId.equals(ExamPointLossScore.getDefaultInstance().getPointId()))
			_builder_.setPointId(pointId);
		if (pointName != null && !pointName.equals(ExamPointLossScore.getDefaultInstance().getPointName()))
			_builder_.setPointName(pointName);
		if (lossScore != null && !lossScore.equals(ExamPointLossScore.getDefaultInstance().getLossScore()))
			_builder_.setLossScore(lossScore);
		return _builder_.build();
	}
}
