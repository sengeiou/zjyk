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
import com.lys.protobuf.ProtocolRun.Request_GetServerLog;

// ---------------------- 获取服务器运行日志 --------------------------
public class SRequest_GetServerLog extends SPTData<Request_GetServerLog>
{
	private static final SRequest_GetServerLog DefaultInstance = new SRequest_GetServerLog();


	public static SRequest_GetServerLog create()
	{
		SRequest_GetServerLog obj = new SRequest_GetServerLog();
		return obj;
	}

	public SRequest_GetServerLog clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetServerLog _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_GetServerLog load(String str)
	{
		try
		{
			SRequest_GetServerLog obj = new SRequest_GetServerLog();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetServerLog load(JSONObject json)
	{
		try
		{
			SRequest_GetServerLog obj = new SRequest_GetServerLog();
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

	public static List<SRequest_GetServerLog> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetServerLog> list = new ArrayList<SRequest_GetServerLog>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetServerLog item = SRequest_GetServerLog.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetServerLog> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetServerLog item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetServerLog _proto_)
	{
	}

	public static SRequest_GetServerLog load(byte[] bytes)
	{
		try
		{
			SRequest_GetServerLog obj = new SRequest_GetServerLog();
			obj.parse(Request_GetServerLog.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetServerLog load(Request_GetServerLog proto)
	{
		try
		{
			SRequest_GetServerLog obj = new SRequest_GetServerLog();
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
	public Request_GetServerLog saveToProto()
	{
		Request_GetServerLog.Builder _builder_ = Request_GetServerLog.newBuilder();
		return _builder_.build();
	}
}
