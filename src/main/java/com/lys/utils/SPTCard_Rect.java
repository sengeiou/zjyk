package com.lys.utils;

public class SPTCard_Rect
{
	public Integer left = 0;
	public Integer top = 0;
	public Integer right = 0;
	public Integer bottom = 0;

	public static SPTCard_Rect create(Integer left, Integer top, Integer right, Integer bottom)
	{
		SPTCard_Rect obj = new SPTCard_Rect();
		obj.left = left;
		obj.top = top;
		obj.right = right;
		obj.bottom = bottom;
		return obj;
	}
}
