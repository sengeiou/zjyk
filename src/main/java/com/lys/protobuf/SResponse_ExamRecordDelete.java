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
import com.lys.protobuf.ProtocolExam.Response_ExamRecordDelete;

public class SResponse_ExamRecordDelete extends SPTData<Response_ExamRecordDelete>
{
	private static final SResponse_ExamRecordDelete DefaultInstance = new SResponse_ExamRecordDelete();


	public static SResponse_ExamRecordDelete create()
	{
		SResponse_ExamRecordDelete obj = new SResponse_ExamRecordDelete();
		return obj;
	}

	public SResponse_ExamRecordDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamRecordDelete _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ExamRecordDelete load(String str)
	{
		try
		{
			SResponse_ExamRecordDelete obj = new SResponse_ExamRecordDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamRecordDelete load(JSONObject json)
	{
		try
		{
			SResponse_ExamRecordDelete obj = new SResponse_ExamRecordDelete();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ExamRecordDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamRecordDelete> list = new ArrayList<SResponse_ExamRecordDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamRecordDelete item = SResponse_ExamRecordDelete.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamRecordDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamRecordDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamRecordDelete _proto_)
	{
	}

	public static SResponse_ExamRecordDelete load(byte[] bytes)
	{
		try
		{
			SResponse_ExamRecordDelete obj = new SResponse_ExamRecordDelete();
			obj.parse(Response_ExamRecordDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamRecordDelete load(Response_ExamRecordDelete proto)
	{
		try
		{
			SResponse_ExamRecordDelete obj = new SResponse_ExamRecordDelete();
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
	public Response_ExamRecordDelete saveToProto()
	{
		Response_ExamRecordDelete.Builder _builder_ = Response_ExamRecordDelete.newBuilder();
		return _builder_.build();
	}
}
