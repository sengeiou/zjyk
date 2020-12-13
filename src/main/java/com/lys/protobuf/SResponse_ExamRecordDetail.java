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
import com.lys.protobuf.ProtocolExam.Response_ExamRecordDetail;

public class SResponse_ExamRecordDetail extends SPTData<Response_ExamRecordDetail>
{
	private static final SResponse_ExamRecordDetail DefaultInstance = new SResponse_ExamRecordDetail();


	public static SResponse_ExamRecordDetail create()
	{
		SResponse_ExamRecordDetail obj = new SResponse_ExamRecordDetail();
		return obj;
	}

	public SResponse_ExamRecordDetail clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamRecordDetail _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_ExamRecordDetail load(String str)
	{
		try
		{
			SResponse_ExamRecordDetail obj = new SResponse_ExamRecordDetail();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamRecordDetail load(JSONObject json)
	{
		try
		{
			SResponse_ExamRecordDetail obj = new SResponse_ExamRecordDetail();
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

	public static List<SResponse_ExamRecordDetail> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamRecordDetail> list = new ArrayList<SResponse_ExamRecordDetail>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamRecordDetail item = SResponse_ExamRecordDetail.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamRecordDetail> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamRecordDetail item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamRecordDetail _proto_)
	{
	}

	public static SResponse_ExamRecordDetail load(byte[] bytes)
	{
		try
		{
			SResponse_ExamRecordDetail obj = new SResponse_ExamRecordDetail();
			obj.parse(Response_ExamRecordDetail.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamRecordDetail load(Response_ExamRecordDetail proto)
	{
		try
		{
			SResponse_ExamRecordDetail obj = new SResponse_ExamRecordDetail();
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
	public Response_ExamRecordDetail saveToProto()
	{
		Response_ExamRecordDetail.Builder _builder_ = Response_ExamRecordDetail.newBuilder();
		return _builder_.build();
	}
}
