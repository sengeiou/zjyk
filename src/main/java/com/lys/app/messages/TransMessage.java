package com.lys.app.messages;

import io.rong.messages.BaseMessage;
import io.rong.util.GsonUtil;

import java.util.UUID;

public class TransMessage extends BaseMessage
{
	public String id;
	public String from;
	public String to;
	public String evt;
	public String msg;
	public long validTime = 10 * 1000;
	public long ts = System.currentTimeMillis();

	public void setValidTime(long validTime)
	{
		this.validTime = validTime;
	}

	private transient static final String TYPE = "app:TransMsg";

	public TransMessage(String evt, String msg)
	{
		this.id = UUID.randomUUID().toString();
		this.from = "";
		this.to = "";
		this.evt = evt;
		this.msg = msg;
	}

	@Override
	public String getType()
	{
		return TYPE;
	}

	@Override
	public String toString()
	{
		return GsonUtil.toJson(this, TransMessage.class);
	}
}