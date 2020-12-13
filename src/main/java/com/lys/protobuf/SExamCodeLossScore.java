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
import com.lys.protobuf.ProtocolExam.ExamCodeLossScore;

public class SExamCodeLossScore extends SPTData<ExamCodeLossScore>
{
	private static final SExamCodeLossScore DefaultInstance = new SExamCodeLossScore();

	public String code = null;
	public Float gotLv = 0F; // 得分率

	public static SExamCodeLossScore create(String code, Float gotLv)
	{
		SExamCodeLossScore obj = new SExamCodeLossScore();
		obj.code = code;
		obj.gotLv = gotLv;
		return obj;
	}

	public SExamCodeLossScore clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamCodeLossScore _other_)
	{
		this.code = _other_.code;
		this.gotLv = _other_.gotLv;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("code"))
			code = _json_.getString("code");
		if (_json_.containsKey("gotLv"))
			gotLv = _json_.getFloat("gotLv");
	}

	public static SExamCodeLossScore load(String str)
	{
		try
		{
			SExamCodeLossScore obj = new SExamCodeLossScore();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamCodeLossScore load(JSONObject json)
	{
		try
		{
			SExamCodeLossScore obj = new SExamCodeLossScore();
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
			if (gotLv != null)
				_json_.put("gotLv", gotLv);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SExamCodeLossScore> loadList(JSONArray ja)
	{
		try
		{
			List<SExamCodeLossScore> list = new ArrayList<SExamCodeLossScore>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamCodeLossScore item = SExamCodeLossScore.load(jo);
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

	public static JSONArray saveList(List<SExamCodeLossScore> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamCodeLossScore item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamCodeLossScore _proto_)
	{
		if (_proto_.hasCode())
			code = _proto_.getCode();
		if (_proto_.hasGotLv())
			gotLv = _proto_.getGotLv();
	}

	public static SExamCodeLossScore load(byte[] bytes)
	{
		try
		{
			SExamCodeLossScore obj = new SExamCodeLossScore();
			obj.parse(ExamCodeLossScore.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamCodeLossScore load(ExamCodeLossScore proto)
	{
		try
		{
			SExamCodeLossScore obj = new SExamCodeLossScore();
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
	public ExamCodeLossScore saveToProto()
	{
		ExamCodeLossScore.Builder _builder_ = ExamCodeLossScore.newBuilder();
		if (code != null && !code.equals(ExamCodeLossScore.getDefaultInstance().getCode()))
			_builder_.setCode(code);
		if (gotLv != null && !gotLv.equals(ExamCodeLossScore.getDefaultInstance().getGotLv()))
			_builder_.setGotLv(gotLv);
		return _builder_.build();
	}
}
