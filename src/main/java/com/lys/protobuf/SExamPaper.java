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
import com.lys.protobuf.ProtocolExam.ExamPaper;

public class SExamPaper extends SPTData<ExamPaper>
{
	private static final SExamPaper DefaultInstance = new SExamPaper();

	public String id = null;
	public String name = null;
	public Long time = 0L;
	public List<String> files = new ArrayList<String>();
	public String note = null;
	public List<SExamPaperTopic> topics = new ArrayList<SExamPaperTopic>();

	public static SExamPaper create(String id, String name, Long time, String note)
	{
		SExamPaper obj = new SExamPaper();
		obj.id = id;
		obj.name = name;
		obj.time = time;
		obj.note = note;
		return obj;
	}

	public SExamPaper clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SExamPaper _other_)
	{
		this.id = _other_.id;
		this.name = _other_.name;
		this.time = _other_.time;
		this.files = _other_.files;
		this.note = _other_.note;
		this.topics = _other_.topics;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("id"))
			id = _json_.getString("id");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("time"))
			time = _json_.getLong("time");
		if (_json_.containsKey("files"))
			files = AppDataTool.loadStringList(AppDataTool.getJSONArray(_json_, "files"));
		if (_json_.containsKey("note"))
			note = _json_.getString("note");
		if (_json_.containsKey("topics"))
			topics = SExamPaperTopic.loadList(_json_.getJSONArray("topics"));
	}

	public static SExamPaper load(String str)
	{
		try
		{
			SExamPaper obj = new SExamPaper();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamPaper load(JSONObject json)
	{
		try
		{
			SExamPaper obj = new SExamPaper();
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
			if (name != null)
				_json_.put("name", name);
			if (time != null)
				_json_.put("time", String.valueOf(time));
			if (files != null)
				_json_.put("files", AppDataTool.saveStringList(files));
			if (note != null)
				_json_.put("note", note);
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

	public static List<SExamPaper> loadList(JSONArray ja)
	{
		try
		{
			List<SExamPaper> list = new ArrayList<SExamPaper>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SExamPaper item = SExamPaper.load(jo);
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

	public static JSONArray saveList(List<SExamPaper> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SExamPaper item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(ExamPaper _proto_)
	{
		if (_proto_.hasId())
			id = _proto_.getId();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasTime())
			time = _proto_.getTime();
		for (int i = 0; i < _proto_.getFilesCount(); i++)
			files.add(_proto_.getFiles(i));
		if (_proto_.hasNote())
			note = _proto_.getNote();
		for (int i = 0; i < _proto_.getTopicsCount(); i++)
			topics.add(SExamPaperTopic.load(_proto_.getTopics(i)));
	}

	public static SExamPaper load(byte[] bytes)
	{
		try
		{
			SExamPaper obj = new SExamPaper();
			obj.parse(ExamPaper.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SExamPaper load(ExamPaper proto)
	{
		try
		{
			SExamPaper obj = new SExamPaper();
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
	public ExamPaper saveToProto()
	{
		ExamPaper.Builder _builder_ = ExamPaper.newBuilder();
		if (id != null && !id.equals(ExamPaper.getDefaultInstance().getId()))
			_builder_.setId(id);
		if (name != null && !name.equals(ExamPaper.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (time != null && !time.equals(ExamPaper.getDefaultInstance().getTime()))
			_builder_.setTime(time);
		if (files != null)
			for (String _value_ : files)
				_builder_.addFiles(_value_);
		if (note != null && !note.equals(ExamPaper.getDefaultInstance().getNote()))
			_builder_.setNote(note);
		if (topics != null)
			for (SExamPaperTopic _value_ : topics)
				_builder_.addTopics(_value_.saveToProto());
		return _builder_.build();
	}
}
