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
import com.lys.protobuf.ProtocolExam.Response_ExamPointGetAll;

public class SResponse_ExamPointGetAll extends SPTData<Response_ExamPointGetAll>
{
	private static final SResponse_ExamPointGetAll DefaultInstance = new SResponse_ExamPointGetAll();

	public List<SExamPoint> points = new ArrayList<SExamPoint>();

	public static SResponse_ExamPointGetAll create()
	{
		SResponse_ExamPointGetAll obj = new SResponse_ExamPointGetAll();
		return obj;
	}

	public SResponse_ExamPointGetAll clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamPointGetAll _other_)
	{
		this.points = _other_.points;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("points"))
			points = SExamPoint.loadList(_json_.getJSONArray("points"));
	}

	public static SResponse_ExamPointGetAll load(String str)
	{
		try
		{
			SResponse_ExamPointGetAll obj = new SResponse_ExamPointGetAll();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPointGetAll load(JSONObject json)
	{
		try
		{
			SResponse_ExamPointGetAll obj = new SResponse_ExamPointGetAll();
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
			if (points != null)
				_json_.put("points", SExamPoint.saveList(points));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ExamPointGetAll> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamPointGetAll> list = new ArrayList<SResponse_ExamPointGetAll>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamPointGetAll item = SResponse_ExamPointGetAll.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamPointGetAll> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamPointGetAll item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamPointGetAll _proto_)
	{
		for (int i = 0; i < _proto_.getPointsCount(); i++)
			points.add(SExamPoint.load(_proto_.getPoints(i)));
	}

	public static SResponse_ExamPointGetAll load(byte[] bytes)
	{
		try
		{
			SResponse_ExamPointGetAll obj = new SResponse_ExamPointGetAll();
			obj.parse(Response_ExamPointGetAll.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPointGetAll load(Response_ExamPointGetAll proto)
	{
		try
		{
			SResponse_ExamPointGetAll obj = new SResponse_ExamPointGetAll();
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
	public Response_ExamPointGetAll saveToProto()
	{
		Response_ExamPointGetAll.Builder _builder_ = Response_ExamPointGetAll.newBuilder();
		if (points != null)
			for (SExamPoint _value_ : points)
				_builder_.addPoints(_value_.saveToProto());
		return _builder_.build();
	}
}
