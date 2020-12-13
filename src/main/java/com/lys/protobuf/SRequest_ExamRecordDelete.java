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
import com.lys.protobuf.ProtocolExam.Request_ExamRecordDelete;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamRecordDelete extends SPTData<Request_ExamRecordDelete>
{
	private static final SRequest_ExamRecordDelete DefaultInstance = new SRequest_ExamRecordDelete();

	public String id = null;

	public static SRequest_ExamRecordDelete create(String id)
	{
		SRequest_ExamRecordDelete obj = new SRequest_ExamRecordDelete();
		obj.id = id;
		return obj;
	}

	public SRequest_ExamRecordDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamRecordDelete _other_)
	{
		this.id = _other_.id;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
	}

	public static SRequest_ExamRecordDelete load(String str)
	{
		try
		{
			SRequest_ExamRecordDelete obj = new SRequest_ExamRecordDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamRecordDelete load(JSONObject json)
	{
		try
		{
			SRequest_ExamRecordDelete obj = new SRequest_ExamRecordDelete();
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
			if (id != null)
				_json_.put("id", id);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ExamRecordDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamRecordDelete> list = new ArrayList<SRequest_ExamRecordDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamRecordDelete item = SRequest_ExamRecordDelete.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamRecordDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamRecordDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamRecordDelete _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
	}

	public static SRequest_ExamRecordDelete load(byte[] bytes)
	{
		try
		{
			SRequest_ExamRecordDelete obj = new SRequest_ExamRecordDelete();
			obj.parse(Request_ExamRecordDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamRecordDelete load(Request_ExamRecordDelete proto)
	{
		try
		{
			SRequest_ExamRecordDelete obj = new SRequest_ExamRecordDelete();
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
	public Request_ExamRecordDelete saveToProto()
	{
		Request_ExamRecordDelete.Builder _builder_ = Request_ExamRecordDelete.newBuilder();
		if (id != null && !id.equals(Request_ExamRecordDelete.getDefaultInstance().getId()))
			_builder_.setId(id);
		return _builder_.build();
	}
}
