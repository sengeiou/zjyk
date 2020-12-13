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
import com.lys.protobuf.ProtocolExam.Request_ExamPointDelete;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamPointDelete extends SPTData<Request_ExamPointDelete>
{
	private static final SRequest_ExamPointDelete DefaultInstance = new SRequest_ExamPointDelete();

	public String id = null;

	public static SRequest_ExamPointDelete create(String id)
	{
		SRequest_ExamPointDelete obj = new SRequest_ExamPointDelete();
		obj.id = id;
		return obj;
	}

	public SRequest_ExamPointDelete clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamPointDelete _other_)
	{
		this.id = _other_.id;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
	}

	public static SRequest_ExamPointDelete load(String str)
	{
		try
		{
			SRequest_ExamPointDelete obj = new SRequest_ExamPointDelete();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPointDelete load(JSONObject json)
	{
		try
		{
			SRequest_ExamPointDelete obj = new SRequest_ExamPointDelete();
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

	public static List<SRequest_ExamPointDelete> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamPointDelete> list = new ArrayList<SRequest_ExamPointDelete>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamPointDelete item = SRequest_ExamPointDelete.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamPointDelete> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamPointDelete item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamPointDelete _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
	}

	public static SRequest_ExamPointDelete load(byte[] bytes)
	{
		try
		{
			SRequest_ExamPointDelete obj = new SRequest_ExamPointDelete();
			obj.parse(Request_ExamPointDelete.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPointDelete load(Request_ExamPointDelete proto)
	{
		try
		{
			SRequest_ExamPointDelete obj = new SRequest_ExamPointDelete();
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
	public Request_ExamPointDelete saveToProto()
	{
		Request_ExamPointDelete.Builder _builder_ = Request_ExamPointDelete.newBuilder();
		if (id != null && !id.equals(Request_ExamPointDelete.getDefaultInstance().getId()))
			_builder_.setId(id);
		return _builder_.build();
	}
}
