package com.lys.utils;

public class SPTCard_Point
{
	public Integer x = 0;
	public Integer y = 0;

	public static SPTCard_Point create(Integer x, Integer y)
	{
		SPTCard_Point obj = new SPTCard_Point();
		obj.x = x;
		obj.y = y;
		return obj;
	}
}
