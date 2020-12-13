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
import com.lys.protobuf.ProtocolRun.TimeRecord;

public class STimeRecord extends SPTData<TimeRecord>
{
	private static final STimeRecord DefaultInstance = new STimeRecord();

	public Long startTime = 0L;
	public Long startProcessTime = 0L;
	public Long overProcessTime = 0L;
	public String des = null;

	public static STimeRecord create(Long startTime, Long startProcessTime, Long overProcessTime, String des)
	{
		STimeRecord obj = new STimeRecord();
		obj.startTime = startTime;
		obj.startProcessTime = startProcessTime;
		obj.overProcessTime = overProcessTime;
		obj.des = des;
		return obj;
	}

	public STimeRecord clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STimeRecord _other_)
	{
		this.startTime = _other_.startTime;
		this.startProcessTime = _other_.startProcessTime;
		this.overProcessTime = _other_.overProcessTime;
		this.des = _other_.des;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("startTime"))
			startTime = _json_.getLong("startTime");
		if (_json_.containsKey("startProcessTime"))
			startProcessTime = _json_.getLong("startProcessTime");
		if (_json_.containsKey("overProcessTime"))
			overProcessTime = _json_.getLong("overProcessTime");
		if (_json_.containsKey("des"))
			des = _json_.getString("des");
	}

	public static STimeRecord load(String str)
	{
		try
		{
			STimeRecord obj = new STimeRecord();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STimeRecord load(JSONObject json)
	{
		try
		{
			STimeRecord obj = new STimeRecord();
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
			if (startProcessTime != null)
				_json_.put("startProcessTime", String.valueOf(startProcessTime));
			if (overProcessTime != null)
				_json_.put("overProcessTime", String.valueOf(overProcessTime));
			if (des != null)
				_json_.put("des", des);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STimeRecord> loadList(JSONArray ja)
	{
		try
		{
			List<STimeRecord> list = new ArrayList<STimeRecord>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STimeRecord item = STimeRecord.load(jo);
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

	public static JSONArray saveList(List<STimeRecord> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STimeRecord item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(TimeRecord _proto_)
	{
		if (_proto_.hasStartTime())
			startTime = _proto_.getStartTime();
		if (_proto_.hasStartProcessTime())
			startProcessTime = _proto_.getStartProcessTime();
		if (_proto_.hasOverProcessTime())
			overProcessTime = _proto_.getOverProcessTime();
		if (_proto_.hasDes())
			des = _proto_.getDes();
	}

	public static STimeRecord load(byte[] bytes)
	{
		try
		{
			STimeRecord obj = new STimeRecord();
			obj.parse(TimeRecord.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STimeRecord load(TimeRecord proto)
	{
		try
		{
			STimeRecord obj = new STimeRecord();
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
	public TimeRecord saveToProto()
	{
		TimeRecord.Builder _builder_ = TimeRecord.newBuilder();
		if (startTime != null && !startTime.equals(TimeRecord.getDefaultInstance().getStartTime()))
			_builder_.setStartTime(startTime);
		if (startProcessTime != null && !startProcessTime.equals(TimeRecord.getDefaultInstance().getStartProcessTime()))
			_builder_.setStartProcessTime(startProcessTime);
		if (overProcessTime != null && !overProcessTime.equals(TimeRecord.getDefaultInstance().getOverProcessTime()))
			_builder_.setOverProcessTime(overProcessTime);
		if (des != null && !des.equals(TimeRecord.getDefaultInstance().getDes()))
			_builder_.setDes(des);
		return _builder_.build();
	}
}
