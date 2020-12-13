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
import com.lys.protobuf.ProtocolRun.Response_GetTimeRecord;

public class SResponse_GetTimeRecord extends SPTData<Response_GetTimeRecord>
{
	private static final SResponse_GetTimeRecord DefaultInstance = new SResponse_GetTimeRecord();

	public List<STimeRecord> timeRecords = new ArrayList<STimeRecord>();

	public static SResponse_GetTimeRecord create()
	{
		SResponse_GetTimeRecord obj = new SResponse_GetTimeRecord();
		return obj;
	}

	public SResponse_GetTimeRecord clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetTimeRecord _other_)
	{
		this.timeRecords = _other_.timeRecords;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("timeRecords"))
			timeRecords = STimeRecord.loadList(_json_.getJSONArray("timeRecords"));
	}

	public static SResponse_GetTimeRecord load(String str)
	{
		try
		{
			SResponse_GetTimeRecord obj = new SResponse_GetTimeRecord();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTimeRecord load(JSONObject json)
	{
		try
		{
			SResponse_GetTimeRecord obj = new SResponse_GetTimeRecord();
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
			if (timeRecords != null)
				_json_.put("timeRecords", STimeRecord.saveList(timeRecords));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetTimeRecord> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetTimeRecord> list = new ArrayList<SResponse_GetTimeRecord>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetTimeRecord item = SResponse_GetTimeRecord.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetTimeRecord> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetTimeRecord item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetTimeRecord _proto_)
	{
		for (int i = 0; i < _proto_.getTimeRecordsCount(); i++)
			timeRecords.add(STimeRecord.load(_proto_.getTimeRecords(i)));
	}

	public static SResponse_GetTimeRecord load(byte[] bytes)
	{
		try
		{
			SResponse_GetTimeRecord obj = new SResponse_GetTimeRecord();
			obj.parse(Response_GetTimeRecord.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetTimeRecord load(Response_GetTimeRecord proto)
	{
		try
		{
			SResponse_GetTimeRecord obj = new SResponse_GetTimeRecord();
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
	public Response_GetTimeRecord saveToProto()
	{
		Response_GetTimeRecord.Builder _builder_ = Response_GetTimeRecord.newBuilder();
		if (timeRecords != null)
			for (STimeRecord _value_ : timeRecords)
				_builder_.addTimeRecords(_value_.saveToProto());
		return _builder_.build();
	}
}
