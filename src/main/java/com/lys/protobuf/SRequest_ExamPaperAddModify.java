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
import com.lys.protobuf.ProtocolExam.Request_ExamPaperAddModify;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamPaperAddModify extends SPTData<Request_ExamPaperAddModify>
{
	private static final SRequest_ExamPaperAddModify DefaultInstance = new SRequest_ExamPaperAddModify();

	public SExamPaper paper = null;

	public static SRequest_ExamPaperAddModify create(SExamPaper paper)
	{
		SRequest_ExamPaperAddModify obj = new SRequest_ExamPaperAddModify();
		obj.paper = paper;
		return obj;
	}

	public SRequest_ExamPaperAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamPaperAddModify _other_)
	{
		this.paper = _other_.paper;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("paper"))
			paper = SExamPaper.load(_json_.getJSONObject("paper"));
	}

	public static SRequest_ExamPaperAddModify load(String str)
	{
		try
		{
			SRequest_ExamPaperAddModify obj = new SRequest_ExamPaperAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPaperAddModify load(JSONObject json)
	{
		try
		{
			SRequest_ExamPaperAddModify obj = new SRequest_ExamPaperAddModify();
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
			if (paper != null)
				_json_.put("paper", paper.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ExamPaperAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamPaperAddModify> list = new ArrayList<SRequest_ExamPaperAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamPaperAddModify item = SRequest_ExamPaperAddModify.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamPaperAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamPaperAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamPaperAddModify _proto_)
	{
		if (_proto_.hasPaper())
			paper = SExamPaper.load(_proto_.getPaper());
	}

	public static SRequest_ExamPaperAddModify load(byte[] bytes)
	{
		try
		{
			SRequest_ExamPaperAddModify obj = new SRequest_ExamPaperAddModify();
			obj.parse(Request_ExamPaperAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPaperAddModify load(Request_ExamPaperAddModify proto)
	{
		try
		{
			SRequest_ExamPaperAddModify obj = new SRequest_ExamPaperAddModify();
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
	public Request_ExamPaperAddModify saveToProto()
	{
		Request_ExamPaperAddModify.Builder _builder_ = Request_ExamPaperAddModify.newBuilder();
		if (paper != null)
			_builder_.setPaper(paper.saveToProto());
		return _builder_.build();
	}
}
