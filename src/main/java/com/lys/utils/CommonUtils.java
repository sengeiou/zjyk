package com.lys.utils;

import java.util.Base64;

public class CommonUtils
{
	public static String base64Encode(byte[] bytes)
	{
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static byte[] base64Decode(String str)
	{
		return Base64.getDecoder().decode(str);
	}
}
