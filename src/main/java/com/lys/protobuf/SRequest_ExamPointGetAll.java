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
import com.lys.protobuf.ProtocolExam.Request_ExamPointGetAll;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamPointGetAll extends SPTData<Request_ExamPointGetAll>
{
	private static final SRequest_ExamPointGetAll DefaultInstance = new SRequest_ExamPointGetAll();


	public static SRequest_ExamPointGetAll create()
	{
		SRequest_ExamPointGetAll obj = new SRequest_ExamPointGetAll();
		return obj;
	}

	public SRequest_ExamPointGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamPointGetAll _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_ExamPointGetAll load(String str)
	{
		try
		{
			SRequest_ExamPointGetAll obj = new SRequest_ExamPointGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPointGetAll load(JSONObject json)
	{
		try
		{
			SRequest_ExamPointGetAll obj = new SRequest_ExamPointGetAll();
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

	public static List<SRequest_ExamPointGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamPointGetAll> list = new ArrayList<SRequest_ExamPointGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamPointGetAll item = SRequest_ExamPointGetAll.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamPointGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamPointGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamPointGetAll _proto_)
	{
	}

	public static SRequest_ExamPointGetAll load(byte[] bytes)
	{
		try
		{
			SRequest_ExamPointGetAll obj = new SRequest_ExamPointGetAll();
			obj.parse(Request_ExamPointGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPointGetAll load(Request_ExamPointGetAll proto)
	{
		try
		{
			SRequest_ExamPointGetAll obj = new SRequest_ExamPointGetAll();
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
	public Request_ExamPointGetAll saveToProto()
	{
		Request_ExamPointGetAll.Builder _builder_ = Request_ExamPointGetAll.newBuilder();
		return _builder_.build();
	}
}
