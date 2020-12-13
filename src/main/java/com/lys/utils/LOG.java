package com.lys.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LOG
{
	private static final SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static String getProcessId()
	{
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName();
		return name.substring(0, name.indexOf("@"));
	}

	private static String packMsg(Object msg)
	{
		Date date = new Date();
		String timeStr = formatTime.format(date);
		long ms = date.getTime() % 1000;
		long threadId = Thread.currentThread().getId();
		return String.format("%s.%03d : %s : %d : %s", timeStr, ms, getProcessId(), threadId, msg);
	}

	public static void v(Object msg)
	{
		String str = packMsg(msg);
		System.out.println(str);
//		RunServiceImpl.addLog(str);
	}

	public static void e(Object msg)
	{
		String str = packMsg(msg);
		System.err.println(str);
//		RunServiceImpl.addLog(str);
	}
}
