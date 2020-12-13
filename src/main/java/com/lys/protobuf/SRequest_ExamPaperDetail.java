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
import com.lys.protobuf.ProtocolExam.Request_ExamPaperDetail;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_ExamPaperDetail extends SPTData<Request_ExamPaperDetail>
{
	private static final SRequest_ExamPaperDetail DefaultInstance = new SRequest_ExamPaperDetail();

	public List<SExamPaperTopic> topics = new ArrayList<SExamPaperTopic>();

	public static SRequest_ExamPaperDetail create()
	{
		SRequest_ExamPaperDetail obj = new SRequest_ExamPaperDetail();
		return obj;
	}

	public SRequest_ExamPaperDetail clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_ExamPaperDetail _other_)
	{
		this.topics = _other_.topics;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("topics"))
			topics = SExamPaperTopic.loadList(_json_.getJSONArray("topics"));
	}

	public static SRequest_ExamPaperDetail load(String str)
	{
		try
		{
			SRequest_ExamPaperDetail obj = new SRequest_ExamPaperDetail();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPaperDetail load(JSONObject json)
	{
		try
		{
			SRequest_ExamPaperDetail obj = new SRequest_ExamPaperDetail();
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

	public static List<SRequest_ExamPaperDetail> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_ExamPaperDetail> list = new ArrayList<SRequest_ExamPaperDetail>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_ExamPaperDetail item = SRequest_ExamPaperDetail.load(jo);
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

	public static JSONArray saveList(List<SRequest_ExamPaperDetail> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_ExamPaperDetail item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_ExamPaperDetail _proto_)
	{
		for (int i = 0; i < _proto_.getTopicsCount(); i++)
			topics.add(SExamPaperTopic.load(_proto_.getTopics(i)));
	}

	public static SRequest_ExamPaperDetail load(byte[] bytes)
	{
		try
		{
			SRequest_ExamPaperDetail obj = new SRequest_ExamPaperDetail();
			obj.parse(Request_ExamPaperDetail.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_ExamPaperDetail load(Request_ExamPaperDetail proto)
	{
		try
		{
			SRequest_ExamPaperDetail obj = new SRequest_ExamPaperDetail();
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
	public Request_ExamPaperDetail saveToProto()
	{
		Request_ExamPaperDetail.Builder _builder_ = Request_ExamPaperDetail.newBuilder();
		if (topics != null)
			for (SExamPaperTopic _value_ : topics)
				_builder_.addTopics(_value_.saveToProto());
		return _builder_.build();
	}
}
