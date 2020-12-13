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
import com.lys.protobuf.ProtocolExam.Request_ExamPaperGetAll;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamPaperGetAll extends SPTData<Request_ExamPaperGetAll>
{
	private static final SRequest_ExamPaperGetAll DefaultInstance = new SRequest_ExamPaperGetAll();


	public static SRequest_ExamPaperGetAll create()
	{
		SRequest_ExamPaperGetAll obj = new SRequest_ExamPaperGetAll();
		return obj;
	}

	public SRequest_ExamPaperGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamPaperGetAll _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_ExamPaperGetAll load(String str)
	{
		try
		{
			SRequest_ExamPaperGetAll obj = new SRequest_ExamPaperGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPaperGetAll load(JSONObject json)
	{
		try
		{
			SRequest_ExamPaperGetAll obj = new SRequest_ExamPaperGetAll();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ExamPaperGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamPaperGetAll> list = new ArrayList<SRequest_ExamPaperGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamPaperGetAll item = SRequest_ExamPaperGetAll.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamPaperGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamPaperGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamPaperGetAll _proto_)
	{
	}

	public static SRequest_ExamPaperGetAll load(byte[] bytes)
	{
		try
		{
			SRequest_ExamPaperGetAll obj = new SRequest_ExamPaperGetAll();
			obj.parse(Request_ExamPaperGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPaperGetAll load(Request_ExamPaperGetAll proto)
	{
		try
		{
			SRequest_ExamPaperGetAll obj = new SRequest_ExamPaperGetAll();
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
	public Request_ExamPaperGetAll saveToProto()
	{
		Request_ExamPaperGetAll.Builder _builder_ = Request_ExamPaperGetAll.newBuilder();
		return _builder_.build();
	}
}
