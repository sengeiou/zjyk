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
import com.lys.protobuf.ProtocolRun.RequestRecord;

public class SRequestRecord extends SPTData<RequestRecord>
{
	private static final SRequestRecord DefaultInstance = new SRequestRecord();

	public Long entryTime = 0L;
	public Long readyTime = 0L;
	public Long startProcessTime = 0L;
	public Long overProcessTime = 0L;
	public Integer handleId = 0;
	public String handleName = null;

	public static SRequestRecord create(Long entryTime, Long readyTime, Long startProcessTime, Long overProcessTime, Integer handleId, String handleName)
	{
		SRequestRecord obj = new SRequestRecord();
		obj.entryTime = entryTime;
		obj.readyTime = readyTime;
		obj.startProcessTime = startProcessTime;
		obj.overProcessTime = overProcessTime;
		obj.handleId = handleId;
		obj.handleName = handleName;
		return obj;
	}

	public SRequestRecord clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequestRecord _other_)
	{
		this.entryTime = _other_.entryTime;
		this.readyTime = _other_.readyTime;
		this.startProcessTime = _other_.startProcessTime;
		this.overProcessTime = _other_.overProcessTime;
		this.handleId = _other_.handleId;
		this.handleName = _other_.handleName;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("entryTime"))
			entryTime = _json_.getLong("entryTime");
		if (_json_.containsKey("readyTime"))
			readyTime = _json_.getLong("readyTime");
		if (_json_.containsKey("startProcessTime"))
			startProcessTime = _json_.getLong("startProcessTime");
		if (_json_.containsKey("overProcessTime"))
			overProcessTime = _json_.getLong("overProcessTime");
		if (_json_.containsKey("handleId"))
			handleId = _json_.getInteger("handleId");
		if (_json_.containsKey("handleName"))
			handleName = _json_.getString("handleName");
	}

	public static SRequestRecord load(String str)
	{
		try
		{
			SRequestRecord obj = new SRequestRecord();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequestRecord load(JSONObject json)
	{
		try
		{
			SRequestRecord obj = new SRequestRecord();
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
			if (entryTime != null)
				_json_.put("entryTime", String.valueOf(entryTime));
			if (readyTime != null)
				_json_.put("readyTime", String.valueOf(readyTime));
			if (startProcessTime != null)
				_json_.put("startProcessTime", String.valueOf(startProcessTime));
			if (overProcessTime != null)
				_json_.put("overProcessTime", String.valueOf(overProcessTime));
			if (handleId != null)
				_json_.put("handleId", handleId);
			if (handleName != null)
				_json_.put("handleName", handleName);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequestRecord> loadList(JSONArray ja)
	{
		try
		{
			List<SRequestRecord> list = new ArrayList<SRequestRecord>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequestRecord item = SRequestRecord.load(jo);
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

	public static JSONArray saveList(List<SRequestRecord> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequestRecord item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(RequestRecord _proto_)
	{
		if (_proto_.hasEntryTime())
			entryTime = _proto_.getEntryTime();
		if (_proto_.hasReadyTime())
			readyTime = _proto_.getReadyTime();
		if (_proto_.hasStartProcessTime())
			startProcessTime = _proto_.getStartProcessTime();
		if (_proto_.hasOverProcessTime())
			overProcessTime = _proto_.getOverProcessTime();
		if (_proto_.hasHandleId())
			handleId = _proto_.getHandleId();
		if (_proto_.hasHandleName())
			handleName = _proto_.getHandleName();
	}

	public static SRequestRecord load(byte[] bytes)
	{
		try
		{
			SRequestRecord obj = new SRequestRecord();
			obj.parse(RequestRecord.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequestRecord load(RequestRecord proto)
	{
		try
		{
			SRequestRecord obj = new SRequestRecord();
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
	public RequestRecord saveToProto()
	{
		RequestRecord.Builder _builder_ = RequestRecord.newBuilder();
		if (entryTime != null && !entryTime.equals(RequestRecord.getDefaultInstance().getEntryTime()))
			_builder_.setEntryTime(entryTime);
		if (readyTime != null && !readyTime.equals(RequestRecord.getDefaultInstance().getReadyTime()))
			_builder_.setReadyTime(readyTime);
		if (startProcessTime != null && !startProcessTime.equals(RequestRecord.getDefaultInstance().getStartProcessTime()))
			_builder_.setStartProcessTime(startProcessTime);
		if (overProcessTime != null && !overProcessTime.equals(RequestRecord.getDefaultInstance().getOverProcessTime()))
			_builder_.setOverProcessTime(overProcessTime);
		if (handleId != null && !handleId.equals(RequestRecord.getDefaultInstance().getHandleId()))
			_builder_.setHandleId(handleId);
		if (handleName != null && !handleName.equals(RequestRecord.getDefaultInstance().getHandleName()))
			_builder_.setHandleName(handleName);
		return _builder_.build();
	}
}
