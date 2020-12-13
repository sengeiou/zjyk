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
import com.lys.protobuf.ProtocolRun.Response_SetServerState;

public class SResponse_SetServerState extends SPTData<Response_SetServerState>
{
	private static final SResponse_SetServerState DefaultInstance = new SResponse_SetServerState();

	public Boolean stop = false;

	public static SResponse_SetServerState create(Boolean stop)
	{
		SResponse_SetServerState obj = new SResponse_SetServerState();
		obj.stop = stop;
		return obj;
	}

	public SResponse_SetServerState clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_SetServerState _other_)
	{
		this.stop = _other_.stop;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("stop"))
			stop = _json_.getBoolean("stop");
	}

	public static SResponse_SetServerState load(String str)
	{
		try
		{
			SResponse_SetServerState obj = new SResponse_SetServerState();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetServerState load(JSONObject json)
	{
		try
		{
			SResponse_SetServerState obj = new SResponse_SetServerState();
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

	public static List<SResponse_SetServerState> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_SetServerState> list = new ArrayList<SResponse_SetServerState>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_SetServerState item = SResponse_SetServerState.load(jo);
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

	public static JSONArray saveList(List<SResponse_SetServerState> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_SetServerState item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_SetServerState _proto_)
	{
		if (_proto_.hasStop())
			stop = _proto_.getStop();
	}

	public static SResponse_SetServerState load(byte[] bytes)
	{
		try
		{
			SResponse_SetServerState obj = new SResponse_SetServerState();
			obj.parse(Response_SetServerState.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_SetServerState load(Response_SetServerState proto)
	{
		try
		{
			SResponse_SetServerState obj = new SResponse_SetServerState();
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
	public Response_SetServerState saveToProto()
	{
		Response_SetServerState.Builder _builder_ = Response_SetServerState.newBuilder();
		if (stop != null && !stop.equals(Response_SetServerState.getDefaultInstance().getStop()))
			_builder_.setStop(stop);
		return _builder_.build();
	}
}
