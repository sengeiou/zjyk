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
import com.lys.protobuf.ProtocolRun.Request_SetServerState;

// ---------------------- xxxx --------------------------
public class SRequest_SetServerState extends SPTData<Request_SetServerState>
{
	private static final SRequest_SetServerState DefaultInstance = new SRequest_SetServerState();

	public Boolean stop = false;

	public static SRequest_SetServerState create(Boolean stop)
	{
		SRequest_SetServerState obj = new SRequest_SetServerState();
		obj.stop = stop;
		return obj;
	}

	public SRequest_SetServerState clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_SetServerState _other_)
	{
		this.stop = _other_.stop;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("stop"))
			stop = _json_.getBoolean("stop");
	}

	public static SRequest_SetServerState load(String str)
	{
		try
		{
			SRequest_SetServerState obj = new SRequest_SetServerState();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetServerState load(JSONObject json)
	{
		try
		{
			SRequest_SetServerState obj = new SRequest_SetServerState();
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
			if (stop != null)
				_json_.put("stop", stop);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_SetServerState> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_SetServerState> list = new ArrayList<SRequest_SetServerState>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_SetServerState item = SRequest_SetServerState.load(jo);
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

	public static JSONArray saveList(List<SRequest_SetServerState> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_SetServerState item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_SetServerState _proto_)
	{
		if (_proto_.hasStop())
			stop = _proto_.getStop();
	}

	public static SRequest_SetServerState load(byte[] bytes)
	{
		try
		{
			SRequest_SetServerState obj = new SRequest_SetServerState();
			obj.parse(Request_SetServerState.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_SetServerState load(Request_SetServerState proto)
	{
		try
		{
			SRequest_SetServerState obj = new SRequest_SetServerState();
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
	public Request_SetServerState saveToProto()
	{
		Request_SetServerState.Builder _builder_ = Request_SetServerState.newBuilder();
		if (stop != null && !stop.equals(Request_SetServerState.getDefaultInstance().getStop()))
			_builder_.setStop(stop);
		return _builder_.build();
	}
}
