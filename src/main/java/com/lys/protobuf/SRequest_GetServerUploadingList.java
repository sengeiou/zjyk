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
import com.lys.protobuf.ProtocolRun.Request_GetServerUploadingList;

// ---------------------- 获取服务器正在上传列表 --------------------------
public class SRequest_GetServerUploadingList extends SPTData<Request_GetServerUploadingList>
{
	private static final SRequest_GetServerUploadingList DefaultInstance = new SRequest_GetServerUploadingList();


	public static SRequest_GetServerUploadingList create()
	{
		SRequest_GetServerUploadingList obj = new SRequest_GetServerUploadingList();
		return obj;
	}

	public SRequest_GetServerUploadingList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_GetServerUploadingList _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_GetServerUploadingList load(String str)
	{
		try
		{
			SRequest_GetServerUploadingList obj = new SRequest_GetServerUploadingList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetServerUploadingList load(JSONObject json)
	{
		try
		{
			SRequest_GetServerUploadingList obj = new SRequest_GetServerUploadingList();
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

	public static List<SRequest_GetServerUploadingList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_GetServerUploadingList> list = new ArrayList<SRequest_GetServerUploadingList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_GetServerUploadingList item = SRequest_GetServerUploadingList.load(jo);
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

	public static JSONArray saveList(List<SRequest_GetServerUploadingList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_GetServerUploadingList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_GetServerUploadingList _proto_)
	{
	}

	public static SRequest_GetServerUploadingList load(byte[] bytes)
	{
		try
		{
			SRequest_GetServerUploadingList obj = new SRequest_GetServerUploadingList();
			obj.parse(Request_GetServerUploadingList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_GetServerUploadingList load(Request_GetServerUploadingList proto)
	{
		try
		{
			SRequest_GetServerUploadingList obj = new SRequest_GetServerUploadingList();
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
	public Request_GetServerUploadingList saveToProto()
	{
		Request_GetServerUploadingList.Builder _builder_ = Request_GetServerUploadingList.newBuilder();
		return _builder_.build();
	}
}
