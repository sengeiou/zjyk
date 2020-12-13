package com.lys.utils;

import java.awt.*;

public class BaseUtil
{
	public static float scale(float value, double xisu)
	{
		return ((int) (value * xisu * 10 + 0.5)) / 10.0f;
	}

	public static int scale(int value, double xisu)
	{
		return (int) (value * xisu + 0.5);
	}

	public static SPTCard_Point scale(SPTCard_Point point, double xisu)
	{
		return pos(scale(point.x, xisu), scale(point.y, xisu));
	}

	public static SPTCard_Rect scale(SPTCard_Rect rect, double xisu)
	{
		return rectByPos(scale(rect.left, xisu), scale(rect.top, xisu), scale(rect.right, xisu), scale(rect.bottom, xisu));
	}

	public static Color color(int color)
	{
		return new Color(color);
	}

	public static boolean isZero(SPTCard_Point point)
	{
		return point.x == 0 && point.y == 0;
	}

	public static boolean isZero(SPTCard_Rect rect)
	{
		return rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0;
	}

	public static SPTCard_Point pos(int x, int y)
	{
		return SPTCard_Point.create(x, y);
	}

	public static SPTCard_Point pos(SPTCard_Rect rect)
	{
		return SPTCard_Point.create(rect.left, rect.top);
	}

	public static SPTCard_Point center(SPTCard_Rect rect)
	{
		return SPTCard_Point.create((rect.left + rect.right) / 2, (rect.top + rect.bottom) / 2);
	}

	public static SPTCard_Size size(int width, int height)
	{
		return SPTCard_Size.create(width, height);
	}

	public static SPTCard_Rect rectByPos(Integer left, Integer top, Integer right, Integer bottom)
	{
		return SPTCard_Rect.create(left, top, right, bottom);
	}

	public static SPTCard_Rect rectBySize(Integer left, Integer top, Integer width, Integer height)
	{
		return SPTCard_Rect.create(left, top, left + width, top + height);
	}

	public static SPTCard_Rect rectByCenter(int xCenter, int yCenter, int sizeX, int sizeY)
	{
		return SPTCard_Rect.create(xCenter - sizeX / 2, yCenter - sizeY / 2, xCenter + sizeX / 2, yCenter + sizeY / 2);
	}

	public static SPTCard_Rect rectExpand(SPTCard_Rect rect, Integer expand)
	{
		return SPTCard_Rect.create(rect.left - expand, rect.top - expand, rect.right + expand, rect.bottom + expand);
	}

	public static SPTCard_Point offset(SPTCard_Point offset, SPTCard_Point pos)
	{
		return pos(offset.x + pos.x, offset.y + pos.y);
	}

	public static SPTCard_Rect offset(SPTCard_Point offset, SPTCard_Rect rect)
	{
		return rectBySize(offset.x + rect.left, offset.y + rect.top, rectWidth(rect), rectHeight(rect));
	}

	public static int rectWidth(SPTCard_Rect rect)
	{
		return rect.right - rect.left;
	}

	public static int rectHeight(SPTCard_Rect rect)
	{
		return rect.bottom - rect.top;
	}

	public static String rectStrPos(SPTCard_Rect rect)
	{
		return String.format("%d, %d, %d, %d", rect.left, rect.top, rect.right, rect.bottom);
	}

	public static String rectStrSize(SPTCard_Rect rect)
	{
		return String.format("%d, %d, %d, %d", rect.left, rect.top, rectWidth(rect), rectHeight(rect));
	}
}
