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
import com.lys.protobuf.ProtocolExam.Request_ExamPaperDelete;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamPaperDelete extends SPTData<Request_ExamPaperDelete>
{
	private static final SRequest_ExamPaperDelete DefaultInstance = new SRequest_ExamPaperDelete();

	public String id = null;

	public static SRequest_ExamPaperDelete create(String id)
	{
		SRequest_ExamPaperDelete obj = new SRequest_ExamPaperDelete();
		obj.id = id;
		return obj;
	}

	public SRequest_ExamPaperDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamPaperDelete _other_)
	{
		this.id = _other_.id;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
	}

	public static SRequest_ExamPaperDelete load(String str)
	{
		try
		{
			SRequest_ExamPaperDelete obj = new SRequest_ExamPaperDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPaperDelete load(JSONObject json)
	{
		try
		{
			SRequest_ExamPaperDelete obj = new SRequest_ExamPaperDelete();
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
			if (id != null)
				_json_.put("id", id);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_ExamPaperDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamPaperDelete> list = new ArrayList<SRequest_ExamPaperDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamPaperDelete item = SRequest_ExamPaperDelete.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamPaperDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamPaperDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamPaperDelete _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
	}

	public static SRequest_ExamPaperDelete load(byte[] bytes)
	{
		try
		{
			SRequest_ExamPaperDelete obj = new SRequest_ExamPaperDelete();
			obj.parse(Request_ExamPaperDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPaperDelete load(Request_ExamPaperDelete proto)
	{
		try
		{
			SRequest_ExamPaperDelete obj = new SRequest_ExamPaperDelete();
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
	public Request_ExamPaperDelete saveToProto()
	{
		Request_ExamPaperDelete.Builder _builder_ = Request_ExamPaperDelete.newBuilder();
		if (id != null && !id.equals(Request_ExamPaperDelete.getDefaultInstance().getId()))
			_builder_.setId(id);
		return _builder_.build();
	}
}
