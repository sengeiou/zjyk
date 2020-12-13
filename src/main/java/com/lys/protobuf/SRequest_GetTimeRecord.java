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
import com.lys.protobuf.ProtocolRun.Request_GetTimeRecord;

// ---------------------- xxxx --------------------------
public class SRequest_GetTimeRecord extends SPTData<Request_GetTimeRecord>
{
	private static final SRequest_GetTimeRecord DefaultInstance = new SRequest_GetTimeRecord();


	public static SRequest_GetTimeRecord create()
	{
		SRequest_GetTimeRecord obj = new SRequest_GetTimeRecord();
		return obj;
	}

	public SRequest_GetTimeRecord clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetTimeRecord _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_GetTimeRecord load(String str)
	{
		try
		{
			SRequest_GetTimeRecord obj = new SRequest_GetTimeRecord();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTimeRecord load(JSONObject json)
	{
		try
		{
			SRequest_GetTimeRecord obj = new SRequest_GetTimeRecord();
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

	public static List<SRequest_GetTimeRecord> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetTimeRecord> list = new ArrayList<SRequest_GetTimeRecord>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetTimeRecord item = SRequest_GetTimeRecord.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetTimeRecord> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetTimeRecord item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetTimeRecord _proto_)
	{
	}

	public static SRequest_GetTimeRecord load(byte[] bytes)
	{
		try
		{
			SRequest_GetTimeRecord obj = new SRequest_GetTimeRecord();
			obj.parse(Request_GetTimeRecord.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetTimeRecord load(Request_GetTimeRecord proto)
	{
		try
		{
			SRequest_GetTimeRecord obj = new SRequest_GetTimeRecord();
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
	public Request_GetTimeRecord saveToProto()
	{
		Request_GetTimeRecord.Builder _builder_ = Request_GetTimeRecord.newBuilder();
		return _builder_.build();
	}
}
