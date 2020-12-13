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
import com.lys.protobuf.ProtocolRun.Response_GetServerLog;

public class SResponse_GetServerLog extends SPTData<Response_GetServerLog>
{
	private static final SResponse_GetServerLog DefaultInstance = new SResponse_GetServerLog();

	public List<String> logs = new ArrayList<String>();

	public static SResponse_GetServerLog create()
	{
		SResponse_GetServerLog obj = new SResponse_GetServerLog();
		return obj;
	}

	public SResponse_GetServerLog clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetServerLog _other_)
	{
		this.logs = _other_.logs;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("logs"))
			logs = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "logs"));
	}

	public static SResponse_GetServerLog load(String str)
	{
		try
		{
			SResponse_GetServerLog obj = new SResponse_GetServerLog();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetServerLog load(JSONObject json)
	{
		try
		{
			SResponse_GetServerLog obj = new SResponse_GetServerLog();
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
			if (logs != null)
				_json_.put("logs", AppDataTool.saveStringList(logs));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetServerLog> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetServerLog> list = new ArrayList<SResponse_GetServerLog>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetServerLog item = SResponse_GetServerLog.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetServerLog> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetServerLog item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetServerLog _proto_)
	{
		for (int i = 0; i < _proto_.getLogsCount(); i++)
			logs.add(_proto_.getLogs(i));
	}

	public static SResponse_GetServerLog load(byte[] bytes)
	{
		try
		{
			SResponse_GetServerLog obj = new SResponse_GetServerLog();
			obj.parse(Response_GetServerLog.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetServerLog load(Response_GetServerLog proto)
	{
		try
		{
			SResponse_GetServerLog obj = new SResponse_GetServerLog();
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
	public Response_GetServerLog saveToProto()
	{
		Response_GetServerLog.Builder _builder_ = Response_GetServerLog.newBuilder();
		if (logs != null)
			for (String _value_ : logs)
				_builder_.addLogs(_value_);
		return _builder_.build();
	}
}
