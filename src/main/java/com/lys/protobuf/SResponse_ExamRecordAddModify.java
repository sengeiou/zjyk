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
import com.lys.protobuf.ProtocolExam.Response_ExamRecordAddModify;

public class SResponse_ExamRecordAddModify extends SPTData<Response_ExamRecordAddModify>
{
	private static final SResponse_ExamRecordAddModify DefaultInstance = new SResponse_ExamRecordAddModify();


	public static SResponse_ExamRecordAddModify create()
	{
		SResponse_ExamRecordAddModify obj = new SResponse_ExamRecordAddModify();
		return obj;
	}

	public SResponse_ExamRecordAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamRecordAddModify _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ExamRecordAddModify load(String str)
	{
		try
		{
			SResponse_ExamRecordAddModify obj = new SResponse_ExamRecordAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamRecordAddModify load(JSONObject json)
	{
		try
		{
			SResponse_ExamRecordAddModify obj = new SResponse_ExamRecordAddModify();
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

	public static List<SResponse_ExamRecordAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamRecordAddModify> list = new ArrayList<SResponse_ExamRecordAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamRecordAddModify item = SResponse_ExamRecordAddModify.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamRecordAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamRecordAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamRecordAddModify _proto_)
	{
	}

	public static SResponse_ExamRecordAddModify load(byte[] bytes)
	{
		try
		{
			SResponse_ExamRecordAddModify obj = new SResponse_ExamRecordAddModify();
			obj.parse(Response_ExamRecordAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamRecordAddModify load(Response_ExamRecordAddModify proto)
	{
		try
		{
			SResponse_ExamRecordAddModify obj = new SResponse_ExamRecordAddModify();
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
	public Response_ExamRecordAddModify saveToProto()
	{
		Response_ExamRecordAddModify.Builder _builder_ = Response_ExamRecordAddModify.newBuilder();
		return _builder_.build();
	}
}
