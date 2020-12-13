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
import com.lys.protobuf.ProtocolExam.Request_ExamRecordGetAll;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamRecordGetAll extends SPTData<Request_ExamRecordGetAll>
{
	private static final SRequest_ExamRecordGetAll DefaultInstance = new SRequest_ExamRecordGetAll();


	public static SRequest_ExamRecordGetAll create()
	{
		SRequest_ExamRecordGetAll obj = new SRequest_ExamRecordGetAll();
		return obj;
	}

	public SRequest_ExamRecordGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamRecordGetAll _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_ExamRecordGetAll load(String str)
	{
		try
		{
			SRequest_ExamRecordGetAll obj = new SRequest_ExamRecordGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamRecordGetAll load(JSONObject json)
	{
		try
		{
			SRequest_ExamRecordGetAll obj = new SRequest_ExamRecordGetAll();
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

	public static List<SRequest_ExamRecordGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamRecordGetAll> list = new ArrayList<SRequest_ExamRecordGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamRecordGetAll item = SRequest_ExamRecordGetAll.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamRecordGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamRecordGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamRecordGetAll _proto_)
	{
	}

	public static SRequest_ExamRecordGetAll load(byte[] bytes)
	{
		try
		{
			SRequest_ExamRecordGetAll obj = new SRequest_ExamRecordGetAll();
			obj.parse(Request_ExamRecordGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamRecordGetAll load(Request_ExamRecordGetAll proto)
	{
		try
		{
			SRequest_ExamRecordGetAll obj = new SRequest_ExamRecordGetAll();
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
	public Request_ExamRecordGetAll saveToProto()
	{
		Request_ExamRecordGetAll.Builder _builder_ = Request_ExamRecordGetAll.newBuilder();
		return _builder_.build();
	}
}
