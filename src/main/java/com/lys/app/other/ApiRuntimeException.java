package com.lys.app.other;

public class ApiRuntimeException extends RuntimeException
{
	private String msg;

	public ApiRuntimeException(String msg)
	{
		super(msg);
		this.msg = msg;
	}

	public String getMsg()
	{
		return msg;
	}
}
