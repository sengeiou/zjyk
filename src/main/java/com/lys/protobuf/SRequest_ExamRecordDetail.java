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
import com.lys.protobuf.ProtocolExam.Request_ExamRecordDetail;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamRecordDetail extends SPTData<Request_ExamRecordDetail>
{
	private static final SRequest_ExamRecordDetail DefaultInstance = new SRequest_ExamRecordDetail();


	public static SRequest_ExamRecordDetail create()
	{
		SRequest_ExamRecordDetail obj = new SRequest_ExamRecordDetail();
		return obj;
	}

	public SRequest_ExamRecordDetail clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamRecordDetail _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_ExamRecordDetail load(String str)
	{
		try
		{
			SRequest_ExamRecordDetail obj = new SRequest_ExamRecordDetail();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamRecordDetail load(JSONObject json)
	{
		try
		{
			SRequest_ExamRecordDetail obj = new SRequest_ExamRecordDetail();
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

	public static List<SRequest_ExamRecordDetail> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamRecordDetail> list = new ArrayList<SRequest_ExamRecordDetail>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamRecordDetail item = SRequest_ExamRecordDetail.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamRecordDetail> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamRecordDetail item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamRecordDetail _proto_)
	{
	}

	public static SRequest_ExamRecordDetail load(byte[] bytes)
	{
		try
		{
			SRequest_ExamRecordDetail obj = new SRequest_ExamRecordDetail();
			obj.parse(Request_ExamRecordDetail.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamRecordDetail load(Request_ExamRecordDetail proto)
	{
		try
		{
			SRequest_ExamRecordDetail obj = new SRequest_ExamRecordDetail();
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
	public Request_ExamRecordDetail saveToProto()
	{
		Request_ExamRecordDetail.Builder _builder_ = Request_ExamRecordDetail.newBuilder();
		return _builder_.build();
	}
}
