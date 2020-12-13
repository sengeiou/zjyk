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
import com.lys.protobuf.ProtocolExam.Response_ExamPointAddModify;

public class SResponse_ExamPointAddModify extends SPTData<Response_ExamPointAddModify>
{
	private static final SResponse_ExamPointAddModify DefaultInstance = new SResponse_ExamPointAddModify();


	public static SResponse_ExamPointAddModify create()
	{
		SResponse_ExamPointAddModify obj = new SResponse_ExamPointAddModify();
		return obj;
	}

	public SResponse_ExamPointAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamPointAddModify _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ExamPointAddModify load(String str)
	{
		try
		{
			SResponse_ExamPointAddModify obj = new SResponse_ExamPointAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPointAddModify load(JSONObject json)
	{
		try
		{
			SResponse_ExamPointAddModify obj = new SResponse_ExamPointAddModify();
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

	public static List<SResponse_ExamPointAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamPointAddModify> list = new ArrayList<SResponse_ExamPointAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamPointAddModify item = SResponse_ExamPointAddModify.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamPointAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamPointAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamPointAddModify _proto_)
	{
	}

	public static SResponse_ExamPointAddModify load(byte[] bytes)
	{
		try
		{
			SResponse_ExamPointAddModify obj = new SResponse_ExamPointAddModify();
			obj.parse(Response_ExamPointAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPointAddModify load(Response_ExamPointAddModify proto)
	{
		try
		{
			SResponse_ExamPointAddModify obj = new SResponse_ExamPointAddModify();
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
	public Response_ExamPointAddModify saveToProto()
	{
		Response_ExamPointAddModify.Builder _builder_ = Response_ExamPointAddModify.newBuilder();
		return _builder_.build();
	}
}
