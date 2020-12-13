package com.lys.utils;

public class SPTCard_Size
{
	public Integer width = 0;
	public Integer height = 0;

	public static SPTCard_Size create(Integer width, Integer height)
	{
		SPTCard_Size obj = new SPTCard_Size();
		obj.width = width;
		obj.height = height;
		return obj;
	}
}
