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
import com.lys.protobuf.ProtocolExam.Response_ExamPaperDelete;

public class SResponse_ExamPaperDelete extends SPTData<Response_ExamPaperDelete>
{
	private static final SResponse_ExamPaperDelete DefaultInstance = new SResponse_ExamPaperDelete();


	public static SResponse_ExamPaperDelete create()
	{
		SResponse_ExamPaperDelete obj = new SResponse_ExamPaperDelete();
		return obj;
	}

	public SResponse_ExamPaperDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamPaperDelete _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ExamPaperDelete load(String str)
	{
		try
		{
			SResponse_ExamPaperDelete obj = new SResponse_ExamPaperDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPaperDelete load(JSONObject json)
	{
		try
		{
			SResponse_ExamPaperDelete obj = new SResponse_ExamPaperDelete();
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

	public static List<SResponse_ExamPaperDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamPaperDelete> list = new ArrayList<SResponse_ExamPaperDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamPaperDelete item = SResponse_ExamPaperDelete.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamPaperDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamPaperDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamPaperDelete _proto_)
	{
	}

	public static SResponse_ExamPaperDelete load(byte[] bytes)
	{
		try
		{
			SResponse_ExamPaperDelete obj = new SResponse_ExamPaperDelete();
			obj.parse(Response_ExamPaperDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPaperDelete load(Response_ExamPaperDelete proto)
	{
		try
		{
			SResponse_ExamPaperDelete obj = new SResponse_ExamPaperDelete();
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
	public Response_ExamPaperDelete saveToProto()
	{
		Response_ExamPaperDelete.Builder _builder_ = Response_ExamPaperDelete.newBuilder();
		return _builder_.build();
	}
}
