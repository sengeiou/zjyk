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
import com.lys.protobuf.ProtocolExam.Request_ExamPointAddModify;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamPointAddModify extends SPTData<Request_ExamPointAddModify>
{
	private static final SRequest_ExamPointAddModify DefaultInstance = new SRequest_ExamPointAddModify();

	public SExamPoint point = null;

	public static SRequest_ExamPointAddModify create(SExamPoint point)
	{
		SRequest_ExamPointAddModify obj = new SRequest_ExamPointAddModify();
		obj.point = point;
		return obj;
	}

	public SRequest_ExamPointAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamPointAddModify _other_)
	{
		this.point = _other_.point;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("point"))
			point = SExamPoint.load(_json_.getJSONObject("point"));
	}

	public static SRequest_ExamPointAddModify load(String str)
	{
		try
		{
			SRequest_ExamPointAddModify obj = new SRequest_ExamPointAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPointAddModify load(JSONObject json)
	{
		try
		{
			SRequest_ExamPointAddModify obj = new SRequest_ExamPointAddModify();
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
			if (point != null)
				_json_.put("point", point.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ExamPointAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamPointAddModify> list = new ArrayList<SRequest_ExamPointAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamPointAddModify item = SRequest_ExamPointAddModify.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamPointAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamPointAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamPointAddModify _proto_)
	{
		if (_proto_.hasPoint())
			point = SExamPoint.load(_proto_.getPoint());
	}

	public static SRequest_ExamPointAddModify load(byte[] bytes)
	{
		try
		{
			SRequest_ExamPointAddModify obj = new SRequest_ExamPointAddModify();
			obj.parse(Request_ExamPointAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPointAddModify load(Request_ExamPointAddModify proto)
	{
		try
		{
			SRequest_ExamPointAddModify obj = new SRequest_ExamPointAddModify();
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
	public Request_ExamPointAddModify saveToProto()
	{
		Request_ExamPointAddModify.Builder _builder_ = Request_ExamPointAddModify.newBuilder();
		if (point != null)
			_builder_.setPoint(point.saveToProto());
		return _builder_.build();
	}
}
