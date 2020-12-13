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
import com.lys.protobuf.ProtocolRun.ServerState;

public class SServerState extends SPTData<ServerState>
{
	private static final SServerState DefaultInstance = new SServerState();

	public Long startTime = 0L;
	public Boolean stop = false;
	public List<SRequestRecord> requestRecords = new ArrayList<SRequestRecord>();

	public static SServerState create(Long startTime, Boolean stop)
	{
		SServerState obj = new SServerState();
		obj.startTime = startTime;
		obj.stop = stop;
		return obj;
	}

	public SServerState clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SServerState _other_)
	{
		this.startTime = _other_.startTime;
		this.stop = _other_.stop;
		this.requestRecords = _other_.requestRecords;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("startTime"))
			startTime = _json_.getLong("startTime");
		if (_json_.containsKey("stop"))
			stop = _json_.getBoolean("stop");
		if (_json_.containsKey("requestRecords"))
			requestRecords = SRequestRecord.loadList(_json_.getJSONArray("requestRecords"));
	}

	public static SServerState load(String str)
	{
		try
		{
			SServerState obj = new SServerState();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SServerState load(JSONObject json)
	{
		try
		{
			SServerState obj = new SServerState();
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
			if (startTime != null)
				_json_.put("startTime", String.valueOf(startTime));
			if (stop != null)
				_json_.put("stop", stop);
			if (requestRecords != null)
				_json_.put("requestRecords", SRequestRecord.saveList(requestRecords));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SServerState> loadList(JSONArray ja)
	{
		try
		{
			List<SServerState> list = new ArrayList<SServerState>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SServerState item = SServerState.load(jo);
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

	public static JSONArray saveList(List<SServerState> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SServerState item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ServerState _proto_)
	{
		if (_proto_.hasStartTime())
			startTime = _proto_.getStartTime();
		if (_proto_.hasStop())
			stop = _proto_.getStop();
		for (int i = 0; i < _proto_.getRequestRecordsCount(); i++)
			requestRecords.add(SRequestRecord.load(_proto_.getRequestRecords(i)));
	}

	public static SServerState load(byte[] bytes)
	{
		try
		{
			SServerState obj = new SServerState();
			obj.parse(ServerState.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SServerState load(ServerState proto)
	{
		try
		{
			SServerState obj = new SServerState();
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
	public ServerState saveToProto()
	{
		ServerState.Builder _builder_ = ServerState.newBuilder();
		if (startTime != null && !startTime.equals(ServerState.getDefaultInstance().getStartTime()))
			_builder_.setStartTime(startTime);
		if (stop != null && !stop.equals(ServerState.getDefaultInstance().getStop()))
			_builder_.setStop(stop);
		if (requestRecords != null)
			for (SRequestRecord _value_ : requestRecords)
				_builder_.addRequestRecords(_value_.saveToProto());
		return _builder_.build();
	}
}
