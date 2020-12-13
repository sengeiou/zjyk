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
import com.lys.protobuf.ProtocolExam.Response_ExamPaperGetAll;

public class SResponse_ExamPaperGetAll extends SPTData<Response_ExamPaperGetAll>
{
	private static final SResponse_ExamPaperGetAll DefaultInstance = new SResponse_ExamPaperGetAll();

	public List<SExamPaper> papers = new ArrayList<SExamPaper>();

	public static SResponse_ExamPaperGetAll create()
	{
		SResponse_ExamPaperGetAll obj = new SResponse_ExamPaperGetAll();
		return obj;
	}

	public SResponse_ExamPaperGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamPaperGetAll _other_)
	{
		this.papers = _other_.papers;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("papers"))
			papers = SExamPaper.loadList(_json_.getJSONArray("papers"));
	}

	public static SResponse_ExamPaperGetAll load(String str)
	{
		try
		{
			SResponse_ExamPaperGetAll obj = new SResponse_ExamPaperGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPaperGetAll load(JSONObject json)
	{
		try
		{
			SResponse_ExamPaperGetAll obj = new SResponse_ExamPaperGetAll();
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
			if (papers != null)
				_json_.put("papers", SExamPaper.saveList(papers));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ExamPaperGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamPaperGetAll> list = new ArrayList<SResponse_ExamPaperGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamPaperGetAll item = SResponse_ExamPaperGetAll.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamPaperGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamPaperGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamPaperGetAll _proto_)
	{
		for (int i = 0; i < _proto_.getPapersCount(); i++)
			papers.add(SExamPaper.load(_proto_.getPapers(i)));
	}

	public static SResponse_ExamPaperGetAll load(byte[] bytes)
	{
		try
		{
			SResponse_ExamPaperGetAll obj = new SResponse_ExamPaperGetAll();
			obj.parse(Response_ExamPaperGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPaperGetAll load(Response_ExamPaperGetAll proto)
	{
		try
		{
			SResponse_ExamPaperGetAll obj = new SResponse_ExamPaperGetAll();
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
	public Response_ExamPaperGetAll saveToProto()
	{
		Response_ExamPaperGetAll.Builder _builder_ = Response_ExamPaperGetAll.newBuilder();
		if (papers != null)
			for (SExamPaper _value_ : papers)
				_builder_.addPapers(_value_.saveToProto());
		return _builder_.build();
	}
}
