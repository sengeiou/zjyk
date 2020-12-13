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
import com.lys.protobuf.ProtocolExam.Response_ExamPaperAddModify;

public class SResponse_ExamPaperAddModify extends SPTData<Response_ExamPaperAddModify>
{
	private static final SResponse_ExamPaperAddModify DefaultInstance = new SResponse_ExamPaperAddModify();


	public static SResponse_ExamPaperAddModify create()
	{
		SResponse_ExamPaperAddModify obj = new SResponse_ExamPaperAddModify();
		return obj;
	}

	public SResponse_ExamPaperAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamPaperAddModify _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ExamPaperAddModify load(String str)
	{
		try
		{
			SResponse_ExamPaperAddModify obj = new SResponse_ExamPaperAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPaperAddModify load(JSONObject json)
	{
		try
		{
			SResponse_ExamPaperAddModify obj = new SResponse_ExamPaperAddModify();
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

	public static List<SResponse_ExamPaperAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamPaperAddModify> list = new ArrayList<SResponse_ExamPaperAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamPaperAddModify item = SResponse_ExamPaperAddModify.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamPaperAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamPaperAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamPaperAddModify _proto_)
	{
	}

	public static SResponse_ExamPaperAddModify load(byte[] bytes)
	{
		try
		{
			SResponse_ExamPaperAddModify obj = new SResponse_ExamPaperAddModify();
			obj.parse(Response_ExamPaperAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPaperAddModify load(Response_ExamPaperAddModify proto)
	{
		try
		{
			SResponse_ExamPaperAddModify obj = new SResponse_ExamPaperAddModify();
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
	public Response_ExamPaperAddModify saveToProto()
	{
		Response_ExamPaperAddModify.Builder _builder_ = Response_ExamPaperAddModify.newBuilder();
		return _builder_.build();
	}
}
