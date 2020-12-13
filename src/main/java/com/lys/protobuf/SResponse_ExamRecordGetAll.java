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
import com.lys.protobuf.ProtocolExam.Response_ExamRecordGetAll;

public class SResponse_ExamRecordGetAll extends SPTData<Response_ExamRecordGetAll>
{
	private static final SResponse_ExamRecordGetAll DefaultInstance = new SResponse_ExamRecordGetAll();

	public List<SExamRecord> records = new ArrayList<SExamRecord>();

	public static SResponse_ExamRecordGetAll create()
	{
		SResponse_ExamRecordGetAll obj = new SResponse_ExamRecordGetAll();
		return obj;
	}

	public SResponse_ExamRecordGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamRecordGetAll _other_)
	{
		this.records = _other_.records;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("records"))
			records = SExamRecord.loadList(_json_.getJSONArray("records"));
	}

	public static SResponse_ExamRecordGetAll load(String str)
	{
		try
		{
			SResponse_ExamRecordGetAll obj = new SResponse_ExamRecordGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamRecordGetAll load(JSONObject json)
	{
		try
		{
			SResponse_ExamRecordGetAll obj = new SResponse_ExamRecordGetAll();
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
			if (records != null)
				_json_.put("records", SExamRecord.saveList(records));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ExamRecordGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamRecordGetAll> list = new ArrayList<SResponse_ExamRecordGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamRecordGetAll item = SResponse_ExamRecordGetAll.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamRecordGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamRecordGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamRecordGetAll _proto_)
	{
		for (int i = 0; i < _proto_.getRecordsCount(); i++)
			records.add(SExamRecord.load(_proto_.getRecords(i)));
	}

	public static SResponse_ExamRecordGetAll load(byte[] bytes)
	{
		try
		{
			SResponse_ExamRecordGetAll obj = new SResponse_ExamRecordGetAll();
			obj.parse(Response_ExamRecordGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamRecordGetAll load(Response_ExamRecordGetAll proto)
	{
		try
		{
			SResponse_ExamRecordGetAll obj = new SResponse_ExamRecordGetAll();
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
	public Response_ExamRecordGetAll saveToProto()
	{
		Response_ExamRecordGetAll.Builder _builder_ = Response_ExamRecordGetAll.newBuilder();
		if (records != null)
			for (SExamRecord _value_ : records)
				_builder_.addRecords(_value_.saveToProto());
		return _builder_.build();
	}
}
