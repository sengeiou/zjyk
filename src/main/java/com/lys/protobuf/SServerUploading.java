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
import com.lys.protobuf.ProtocolRun.ServerUploading;

public class SServerUploading extends SPTData<ServerUploading>
{
	private static final SServerUploading DefaultInstance = new SServerUploading();

	public String path = null;
	public Long startTime = 0L;

	public static SServerUploading create(String path, Long startTime)
	{
		SServerUploading obj = new SServerUploading();
		obj.path = path;
		obj.startTime = startTime;
		return obj;
	}

	public SServerUploading clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SServerUploading _other_)
	{
		this.path = _other_.path;
		this.startTime = _other_.startTime;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("path"))
			path = _json_.getString("path");
		if (_json_.containsKey("startTime"))
			startTime = _json_.getLong("startTime");
	}

	public static SServerUploading load(String str)
	{
		try
		{
			SServerUploading obj = new SServerUploading();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SServerUploading load(JSONObject json)
	{
		try
		{
			SServerUploading obj = new SServerUploading();
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
			if (path != null)
				_json_.put("path", path);
			if (startTime != null)
				_json_.put("startTime", String.valueOf(startTime));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SServerUploading> loadList(JSONArray ja)
	{
		try
		{
			List<SServerUploading> list = new ArrayList<SServerUploading>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SServerUploading item = SServerUploading.load(jo);
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

	public static JSONArray saveList(List<SServerUploading> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SServerUploading item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ServerUploading _proto_)
	{
		if (_proto_.hasPath())
			path = _proto_.getPath();
		if (_proto_.hasStartTime())
			startTime = _proto_.getStartTime();
	}

	public static SServerUploading load(byte[] bytes)
	{
		try
		{
			SServerUploading obj = new SServerUploading();
			obj.parse(ServerUploading.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SServerUploading load(ServerUploading proto)
	{
		try
		{
			SServerUploading obj = new SServerUploading();
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
	public ServerUploading saveToProto()
	{
		ServerUploading.Builder _builder_ = ServerUploading.newBuilder();
		if (path != null && !path.equals(ServerUploading.getDefaultInstance().getPath()))
			_builder_.setPath(path);
		if (startTime != null && !startTime.equals(ServerUploading.getDefaultInstance().getStartTime()))
			_builder_.setStartTime(startTime);
		return _builder_.build();
	}
}
