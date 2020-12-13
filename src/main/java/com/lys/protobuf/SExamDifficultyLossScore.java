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
import com.lys.protobuf.ProtocolExam.ExamDifficultyLossScore;

public class SExamDifficultyLossScore extends SPTData<ExamDifficultyLossScore>
{
	private static final SExamDifficultyLossScore DefaultInstance = new SExamDifficultyLossScore();

	public Integer difficulty = 0;
	public Integer allScore = 0;
	public Integer lossScore = 0;

	public static SExamDifficultyLossScore create(Integer difficulty, Integer allScore, Integer lossScore)
	{
		SExamDifficultyLossScore obj = new SExamDifficultyLossScore();
		obj.difficulty = difficulty;
		obj.allScore = allScore;
		obj.lossScore = lossScore;
		return obj;
	}

	public SExamDifficultyLossScore clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamDifficultyLossScore _other_)
	{
		this.difficulty = _other_.difficulty;
		this.allScore = _other_.allScore;
		this.lossScore = _other_.lossScore;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("difficulty"))
			difficulty = _json_.getInteger("difficulty");
		if (_json_.containsKey("allScore"))
			allScore = _json_.getInteger("allScore");
		if (_json_.containsKey("lossScore"))
			lossScore = _json_.getInteger("lossScore");
	}

	public static SExamDifficultyLossScore load(String str)
	{
		try
		{
			SExamDifficultyLossScore obj = new SExamDifficultyLossScore();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamDifficultyLossScore load(JSONObject json)
	{
		try
		{
			SExamDifficultyLossScore obj = new SExamDifficultyLossScore();
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
			if (difficulty != null)
				_json_.put("difficulty", difficulty);
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

	public static List<SExamDifficultyLossScore> loadList(JSONArray ja)
	{
		try
		{
			List<SExamDifficultyLossScore> list = new ArrayList<SExamDifficultyLossScore>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamDifficultyLossScore item = SExamDifficultyLossScore.load(jo);
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

	public static JSONArray saveList(List<SExamDifficultyLossScore> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamDifficultyLossScore item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamDifficultyLossScore _proto_)
	{
		if (_proto_.hasDifficulty())
			difficulty = _proto_.getDifficulty();
		if (_proto_.hasAllScore())
			allScore = _proto_.getAllScore();
		if (_proto_.hasLossScore())
			lossScore = _proto_.getLossScore();
	}

	public static SExamDifficultyLossScore load(byte[] bytes)
	{
		try
		{
			SExamDifficultyLossScore obj = new SExamDifficultyLossScore();
			obj.parse(ExamDifficultyLossScore.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamDifficultyLossScore load(ExamDifficultyLossScore proto)
	{
		try
		{
			SExamDifficultyLossScore obj = new SExamDifficultyLossScore();
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
	public ExamDifficultyLossScore saveToProto()
	{
		ExamDifficultyLossScore.Builder _builder_ = ExamDifficultyLossScore.newBuilder();
		if (difficulty != null && !difficulty.equals(ExamDifficultyLossScore.getDefaultInstance().getDifficulty()))
			_builder_.setDifficulty(difficulty);
		if (allScore != null && !allScore.equals(ExamDifficultyLossScore.getDefaultInstance().getAllScore()))
			_builder_.setAllScore(allScore);
		if (lossScore != null && !lossScore.equals(ExamDifficultyLossScore.getDefaultInstance().getLossScore()))
			_builder_.setLossScore(lossScore);
		return _builder_.build();
	}
}
