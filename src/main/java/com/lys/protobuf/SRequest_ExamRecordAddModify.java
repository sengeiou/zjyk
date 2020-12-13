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
import com.lys.protobuf.ProtocolExam.Request_ExamRecordAddModify;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamRecordAddModify extends SPTData<Request_ExamRecordAddModify>
{
	private static final SRequest_ExamRecordAddModify DefaultInstance = new SRequest_ExamRecordAddModify();

	public SExamRecord record = null;

	public static SRequest_ExamRecordAddModify create(SExamRecord record)
	{
		SRequest_ExamRecordAddModify obj = new SRequest_ExamRecordAddModify();
		obj.record = record;
		return obj;
	}

	public SRequest_ExamRecordAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamRecordAddModify _other_)
	{
		this.record = _other_.record;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("record"))
			record = SExamRecord.load(_json_.getJSONObject("record"));
	}

	public static SRequest_ExamRecordAddModify load(String str)
	{
		try
		{
			SRequest_ExamRecordAddModify obj = new SRequest_ExamRecordAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamRecordAddModify load(JSONObject json)
	{
		try
		{
			SRequest_ExamRecordAddModify obj = new SRequest_ExamRecordAddModify();
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
			if (record != null)
				_json_.put("record", record.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ExamRecordAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamRecordAddModify> list = new ArrayList<SRequest_ExamRecordAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamRecordAddModify item = SRequest_ExamRecordAddModify.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamRecordAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamRecordAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamRecordAddModify _proto_)
	{
		if (_proto_.hasRecord())
			record = SExamRecord.load(_proto_.getRecord());
	}

	public static SRequest_ExamRecordAddModify load(byte[] bytes)
	{
		try
		{
			SRequest_ExamRecordAddModify obj = new SRequest_ExamRecordAddModify();
			obj.parse(Request_ExamRecordAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamRecordAddModify load(Request_ExamRecordAddModify proto)
	{
		try
		{
			SRequest_ExamRecordAddModify obj = new SRequest_ExamRecordAddModify();
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
	public Request_ExamRecordAddModify saveToProto()
	{
		Request_ExamRecordAddModify.Builder _builder_ = Request_ExamRecordAddModify.newBuilder();
		if (record != null)
			_builder_.setRecord(record.saveToProto());
		return _builder_.build();
	}
}
