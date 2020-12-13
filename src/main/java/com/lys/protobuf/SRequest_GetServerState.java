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
import com.lys.protobuf.ProtocolRun.Request_GetServerState;

// ---------------------- 获取服务器运行状态 --------------------------
public class SRequest_GetServerState extends SPTData<Request_GetServerState>
{
	private static final SRequest_GetServerState DefaultInstance = new SRequest_GetServerState();


	public static SRequest_GetServerState create()
	{
		SRequest_GetServerState obj = new SRequest_GetServerState();
		return obj;
	}

	public SRequest_GetServerState clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetServerState _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_GetServerState load(String str)
	{
		try
		{
			SRequest_GetServerState obj = new SRequest_GetServerState();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetServerState load(JSONObject json)
	{
		try
		{
			SRequest_GetServerState obj = new SRequest_GetServerState();
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

	public static List<SRequest_GetServerState> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetServerState> list = new ArrayList<SRequest_GetServerState>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetServerState item = SRequest_GetServerState.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetServerState> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetServerState item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetServerState _proto_)
	{
	}

	public static SRequest_GetServerState load(byte[] bytes)
	{
		try
		{
			SRequest_GetServerState obj = new SRequest_GetServerState();
			obj.parse(Request_GetServerState.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetServerState load(Request_GetServerState proto)
	{
		try
		{
			SRequest_GetServerState obj = new SRequest_GetServerState();
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
	public Request_GetServerState saveToProto()
	{
		Request_GetServerState.Builder _builder_ = Request_GetServerState.newBuilder();
		return _builder_.build();
	}
}
