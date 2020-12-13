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
import com.lys.protobuf.ProtocolExam.Response_ExamPointDelete;

public class SResponse_ExamPointDelete extends SPTData<Response_ExamPointDelete>
{
	private static final SResponse_ExamPointDelete DefaultInstance = new SResponse_ExamPointDelete();


	public static SResponse_ExamPointDelete create()
	{
		SResponse_ExamPointDelete obj = new SResponse_ExamPointDelete();
		return obj;
	}

	public SResponse_ExamPointDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamPointDelete _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ExamPointDelete load(String str)
	{
		try
		{
			SResponse_ExamPointDelete obj = new SResponse_ExamPointDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPointDelete load(JSONObject json)
	{
		try
		{
			SResponse_ExamPointDelete obj = new SResponse_ExamPointDelete();
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

	public static List<SResponse_ExamPointDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamPointDelete> list = new ArrayList<SResponse_ExamPointDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamPointDelete item = SResponse_ExamPointDelete.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamPointDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamPointDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamPointDelete _proto_)
	{
	}

	public static SResponse_ExamPointDelete load(byte[] bytes)
	{
		try
		{
			SResponse_ExamPointDelete obj = new SResponse_ExamPointDelete();
			obj.parse(Response_ExamPointDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPointDelete load(Response_ExamPointDelete proto)
	{
		try
		{
			SResponse_ExamPointDelete obj = new SResponse_ExamPointDelete();
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
	public Response_ExamPointDelete saveToProto()
	{
		Response_ExamPointDelete.Builder _builder_ = Response_ExamPointDelete.newBuilder();
		return _builder_.build();
	}
}
