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
import com.lys.protobuf.ProtocolRun.Response_GetServerUploadingList;

public class SResponse_GetServerUploadingList extends SPTData<Response_GetServerUploadingList>
{
	private static final SResponse_GetServerUploadingList DefaultInstance = new SResponse_GetServerUploadingList();

	public List<SServerUploading> uploadingList = new ArrayList<SServerUploading>();
	public List<SServerUpload> uploadList = new ArrayList<SServerUpload>();

	public static SResponse_GetServerUploadingList create()
	{
		SResponse_GetServerUploadingList obj = new SResponse_GetServerUploadingList();
		return obj;
	}

	public SResponse_GetServerUploadingList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_GetServerUploadingList _other_)
	{
		this.uploadingList = _other_.uploadingList;
		this.uploadList = _other_.uploadList;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("uploadingList"))
			uploadingList = SServerUploading.loadList(_json_.getJSONArray("uploadingList"));
		if (_json_.containsKey("uploadList"))
			uploadList = SServerUpload.loadList(_json_.getJSONArray("uploadList"));
	}

	public static SResponse_GetServerUploadingList load(String str)
	{
		try
		{
			SResponse_GetServerUploadingList obj = new SResponse_GetServerUploadingList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetServerUploadingList load(JSONObject json)
	{
		try
		{
			SResponse_GetServerUploadingList obj = new SResponse_GetServerUploadingList();
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
			if (uploadingList != null)
				_json_.put("uploadingList", SServerUploading.saveList(uploadingList));
			if (uploadList != null)
				_json_.put("uploadList", SServerUpload.saveList(uploadList));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_GetServerUploadingList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_GetServerUploadingList> list = new ArrayList<SResponse_GetServerUploadingList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_GetServerUploadingList item = SResponse_GetServerUploadingList.load(jo);
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

	public static JSONArray saveList(List<SResponse_GetServerUploadingList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_GetServerUploadingList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_GetServerUploadingList _proto_)
	{
		for (int i = 0; i < _proto_.getUploadingListCount(); i++)
			uploadingList.add(SServerUploading.load(_proto_.getUploadingList(i)));
		for (int i = 0; i < _proto_.getUploadListCount(); i++)
			uploadList.add(SServerUpload.load(_proto_.getUploadList(i)));
	}

	public static SResponse_GetServerUploadingList load(byte[] bytes)
	{
		try
		{
			SResponse_GetServerUploadingList obj = new SResponse_GetServerUploadingList();
			obj.parse(Response_GetServerUploadingList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_GetServerUploadingList load(Response_GetServerUploadingList proto)
	{
		try
		{
			SResponse_GetServerUploadingList obj = new SResponse_GetServerUploadingList();
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
	public Response_GetServerUploadingList saveToProto()
	{
		Response_GetServerUploadingList.Builder _builder_ = Response_GetServerUploadingList.newBuilder();
		if (uploadingList != null)
			for (SServerUploading _value_ : uploadingList)
				_builder_.addUploadingList(_value_.saveToProto());
		if (uploadList != null)
			for (SServerUpload _value_ : uploadList)
				_builder_.addUploadList(_value_.saveToProto());
		return _builder_.build();
	}
}
