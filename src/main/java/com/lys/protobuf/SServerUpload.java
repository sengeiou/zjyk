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
import com.lys.protobuf.ProtocolRun.ServerUpload;

public class SServerUpload extends SPTData<ServerUpload>
{
	private static final SServerUpload DefaultInstance = new SServerUpload();

	public String path = null;
	public Long startTime = 0L;
	public Long endTime = 0L;
	public Long fileSize = 0L;
	public Boolean result = false;

	public static SServerUpload create(String path, Long startTime, Long endTime, Long fileSize, Boolean result)
	{
		SServerUpload obj = new SServerUpload();
		obj.path = path;
		obj.startTime = startTime;
		obj.endTime = endTime;
		obj.fileSize = fileSize;
		obj.result = result;
		return obj;
	}

	public SServerUpload clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SServerUpload _other_)
	{
		this.path = _other_.path;
		this.startTime = _other_.startTime;
		this.endTime = _other_.endTime;
		this.fileSize = _other_.fileSize;
		this.result = _other_.result;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("path"))
			path = _json_.getString("path");
		if (_json_.containsKey("startTime"))
			startTime = _json_.getLong("startTime");
		if (_json_.containsKey("endTime"))
			endTime = _json_.getLong("endTime");
		if (_json_.containsKey("fileSize"))
			fileSize = _json_.getLong("fileSize");
		if (_json_.containsKey("result"))
			result = _json_.getBoolean("result");
	}

	public static SServerUpload load(String str)
	{
		try
		{
			SServerUpload obj = new SServerUpload();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SServerUpload load(JSONObject json)
	{
		try
		{
			SServerUpload obj = new SServerUpload();
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
			if (endTime != null)
				_json_.put("endTime", String.valueOf(endTime));
			if (fileSize != null)
				_json_.put("fileSize", String.valueOf(fileSize));
			if (result != null)
				_json_.put("result", result);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SServerUpload> loadList(JSONArray ja)
	{
		try
		{
			List<SServerUpload> list = new ArrayList<SServerUpload>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SServerUpload item = SServerUpload.load(jo);
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

	public static JSONArray saveList(List<SServerUpload> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SServerUpload item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ServerUpload _proto_)
	{
		if (_proto_.hasPath())
			path = _proto_.getPath();
		if (_proto_.hasStartTime())
			startTime = _proto_.getStartTime();
		if (_proto_.hasEndTime())
			endTime = _proto_.getEndTime();
		if (_proto_.hasFileSize())
			fileSize = _proto_.getFileSize();
		if (_proto_.hasResult())
			result = _proto_.getResult();
	}

	public static SServerUpload load(byte[] bytes)
	{
		try
		{
			SServerUpload obj = new SServerUpload();
			obj.parse(ServerUpload.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SServerUpload load(ServerUpload proto)
	{
		try
		{
			SServerUpload obj = new SServerUpload();
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
	public ServerUpload saveToProto()
	{
		ServerUpload.Builder _builder_ = ServerUpload.newBuilder();
		if (path != null && !path.equals(ServerUpload.getDefaultInstance().getPath()))
			_builder_.setPath(path);
		if (startTime != null && !startTime.equals(ServerUpload.getDefaultInstance().getStartTime()))
			_builder_.setStartTime(startTime);
		if (endTime != null && !endTime.equals(ServerUpload.getDefaultInstance().getEndTime()))
			_builder_.setEndTime(endTime);
		if (fileSize != null && !fileSize.equals(ServerUpload.getDefaultInstance().getFileSize()))
			_builder_.setFileSize(fileSize);
		if (result != null && !result.equals(ServerUpload.getDefaultInstance().getResult()))
			_builder_.setResult(result);
		return _builder_.build();
	}
}
