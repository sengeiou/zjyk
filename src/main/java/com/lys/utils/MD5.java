package com.lys.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;

public class MD5
{
	private static final char[] hexCode = "0123456789abcdef".toCharArray();

	private static String toHexString(byte[] data)
	{
		StringBuilder r = new StringBuilder(data.length * 2);
		for (byte b : data)
		{
			r.append(hexCode[(b >> 4) & 0xF]);
			r.append(hexCode[(b & 0xF)]);
		}
		return r.toString();
	}

	public static String calcMD5(InputStream stream)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] buf = new byte[8192];
			int len;
			while ((len = stream.read(buf)) > 0)
			{
				digest.update(buf, 0, len);
			}
			return toHexString(digest.digest());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public static String calcMD5(byte[] bytes)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(bytes);
			return toHexString(digest.digest());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public static String calcMD5(File file)
	{
		try (InputStream stream = Files.newInputStream(file.toPath(), StandardOpenOption.READ))
		{
			return calcMD5(stream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public static String calcMD5(String text)
	{
		return calcMD5(text.getBytes(Charset.forName("utf-8")));
	}
}