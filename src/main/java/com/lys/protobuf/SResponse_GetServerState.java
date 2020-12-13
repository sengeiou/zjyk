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
import com.lys.protobuf.ProtocolRun.Response_GetServerState;

public class SResponse_GetServerState extends SPTData<Response_GetServerState>
{
	private static final SResponse_GetServerState DefaultInstance = new SResponse_GetServerState();

	public SServerState serverState = null;

	public static SResponse_GetServerState create(SServerState serverState)
	{
		SResponse_GetServerState obj = new SResponse_GetServerState();
		obj.serverState = serverState;
		return obj;
	}

	public SResponse_GetServerState clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetServerState _other_)
	{
		this.serverState = _other_.serverState;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("serverState"))
			serverState = SServerState.load(_json_.getJSONObject("serverState"));
	}

	public static SResponse_GetServerState load(String str)
	{
		try
		{
			SResponse_GetServerState obj = new SResponse_GetServerState();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetServerState load(JSONObject json)
	{
		try
		{
			SResponse_GetServerState obj = new SResponse_GetServerState();
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
			if (serverState != null)
				_json_.put("serverState", serverState.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetServerState> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetServerState> list = new ArrayList<SResponse_GetServerState>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetServerState item = SResponse_GetServerState.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetServerState> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetServerState item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetServerState _proto_)
	{
		if (_proto_.hasServerState())
			serverState = SServerState.load(_proto_.getServerState());
	}

	public static SResponse_GetServerState load(byte[] bytes)
	{
		try
		{
			SResponse_GetServerState obj = new SResponse_GetServerState();
			obj.parse(Response_GetServerState.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetServerState load(Response_GetServerState proto)
	{
		try
		{
			SResponse_GetServerState obj = new SResponse_GetServerState();
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
	public Response_GetServerState saveToProto()
	{
		Response_GetServerState.Builder _builder_ = Response_GetServerState.newBuilder();
		if (serverState != null)
			_builder_.setServerState(serverState.saveToProto());
		return _builder_.build();
	}
}
