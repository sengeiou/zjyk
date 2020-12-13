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
import com.lys.protobuf.ProtocolExam.Response_ExamPaperDetail;

public class SResponse_ExamPaperDetail extends SPTData<Response_ExamPaperDetail>
{
	private static final SResponse_ExamPaperDetail DefaultInstance = new SResponse_ExamPaperDetail();

	public List<SExamPaperTopic> topics = new ArrayList<SExamPaperTopic>();

	public static SResponse_ExamPaperDetail create()
	{
		SResponse_ExamPaperDetail obj = new SResponse_ExamPaperDetail();
		return obj;
	}

	public SResponse_ExamPaperDetail clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_ExamPaperDetail _other_)
	{
		this.topics = _other_.topics;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("topics"))
			topics = SExamPaperTopic.loadList(_json_.getJSONArray("topics"));
	}

	public static SResponse_ExamPaperDetail load(String str)
	{
		try
		{
			SResponse_ExamPaperDetail obj = new SResponse_ExamPaperDetail();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPaperDetail load(JSONObject json)
	{
		try
		{
			SResponse_ExamPaperDetail obj = new SResponse_ExamPaperDetail();
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
			if (topics != null)
				_json_.put("topics", SExamPaperTopic.saveList(topics));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_ExamPaperDetail> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_ExamPaperDetail> list = new ArrayList<SResponse_ExamPaperDetail>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_ExamPaperDetail item = SResponse_ExamPaperDetail.load(jo);
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

	public static JSONArray saveList(List<SResponse_ExamPaperDetail> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_ExamPaperDetail item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_ExamPaperDetail _proto_)
	{
		for (int i = 0; i < _proto_.getTopicsCount(); i++)
			topics.add(SExamPaperTopic.load(_proto_.getTopics(i)));
	}

	public static SResponse_ExamPaperDetail load(byte[] bytes)
	{
		try
		{
			SResponse_ExamPaperDetail obj = new SResponse_ExamPaperDetail();
			obj.parse(Response_ExamPaperDetail.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_ExamPaperDetail load(Response_ExamPaperDetail proto)
	{
		try
		{
			SResponse_ExamPaperDetail obj = new SResponse_ExamPaperDetail();
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
	public Response_ExamPaperDetail saveToProto()
	{
		Response_ExamPaperDetail.Builder _builder_ = Response_ExamPaperDetail.newBuilder();
		if (topics != null)
			for (SExamPaperTopic _value_ : topics)
				_builder_.addTopics(_value_.saveToProto());
		return _builder_.build();
	}
}
