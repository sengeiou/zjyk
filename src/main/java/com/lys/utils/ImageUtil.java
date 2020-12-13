package com.lys.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ImageUtil extends BaseUtil
{
	public static final boolean debug = false;

	public static final int Black = 0xff000000;
	public static final int White = 0xffffffff;
	public static final int Red = 0xffff0000;

	public static final int rayStep = 2;

	public static boolean isInvidPx(BufferedImage image, int x, int y)
	{
		return x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight();
	}

	public static BufferedImage readImage(InputStream input)
	{
		try
		{
			return ImageIO.read(input);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage readImage(String fullpath)
	{
		try
		{
			if (new File(fullpath).exists())
			{
				return ImageIO.read(new File(fullpath));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<BufferedImage> readImageList(InputStream... inputs)
	{
		ArrayList<BufferedImage> images = new ArrayList<>();
		for (InputStream input : inputs)
		{
			images.add(readImage(input));
		}
		return images;
	}

	public static ArrayList<BufferedImage> readImageList(String... fullpaths)
	{
		ArrayList<BufferedImage> images = new ArrayList<>();
		for (String fullpath : fullpaths)
		{
			images.add(readImage(fullpath));
		}
		return images;
	}

	public static BufferedImage[] readImageArray(String... fullpaths)
	{
		BufferedImage[] images = new BufferedImage[fullpaths.length];
		for (int i = 0; i < fullpaths.length; i++)
		{
			String fullpath = fullpaths[i];
			images[i] = readImage(fullpath);
		}
		return images;
	}

	public static void writeImage(BufferedImage image, String fullpath)
	{
		try
		{
			ImageIO.write(image, "png", new File(fullpath));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static byte[] writeImage(BufferedImage image)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			byte[] buf = baos.toByteArray();
			baos.close();
			return buf;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void drawPoint(Graphics g, Color color, int x, int y, int size)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.fillOval(x - size / 2, y - size / 2, size, size);
	}

	public static void drawPoint(Graphics g, Color color, SPTCard_Point point, int size)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.fillOval(point.x - size / 2, point.y - size / 2, size, size);
	}

	public static void drawLine(Graphics g, Color color, int x1, int y1, int x2, int y2, int lineWidth)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(lineWidth));
		g2.drawLine(x1, y1, x2, y2);
	}

	public static void drawLine(Graphics g, Color color, SPTCard_Point p1, SPTCard_Point p2, int lineWidth)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(lineWidth));
		g2.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	public static void drawRect(Graphics g, Color color, SPTCard_Rect rect, int lineWidth)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(lineWidth));
		g2.drawRect(rect.left, rect.top, rectWidth(rect), rectHeight(rect));
	}

	public static void fillRect(Graphics g, Color color, SPTCard_Rect rect)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.fillRect(rect.left, rect.top, rectWidth(rect), rectHeight(rect));
	}

	public static void drawString(Graphics g, Color color, String str, String fontName, int fontStyle, int fontSize, float anchorX, float anchorY, int x, int y)
	{
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font(fontName, fontStyle, fontSize);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(font);
		g2.setColor(color);
		FontMetrics fm = g2.getFontMetrics(font);
		int width = fm.stringWidth(str);
		int height = fm.getHeight();
		g2.drawString(str, x - anchorX * width, y + height - anchorY * height);
	}

	public static void drawString(Graphics g, Color color, String str, int fontSize, float anchorX, float anchorY, int x, int y)
	{
		drawString(g, color, str, "宋体", Font.PLAIN, fontSize, anchorX, anchorY, x, y);
	}

	public static void drawString(Graphics g, Color color, String str, int fontSize, float anchorX, float anchorY, SPTCard_Point p)
	{
		drawString(g, color, str, fontSize, anchorX, anchorY, p.x, p.y);
	}

	public static void drawImage(Graphics g, BufferedImage image, int x, int y)
	{
		g.drawImage(image, x, y, null);
	}

	public static void drawImage(Graphics g, BufferedImage image, SPTCard_Point pos)
	{
		g.drawImage(image, pos.x, pos.y, null);
	}

	public static void drawImage(Graphics g, BufferedImage image, SPTCard_Rect dstRect, SPTCard_Rect srcRect)
	{
		g.drawImage(image, dstRect.left, dstRect.top, dstRect.right, dstRect.bottom, srcRect.left, srcRect.top, srcRect.right, srcRect.bottom, null);
	}

	public static BufferedImage newImage(int width, int height)
	{
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
//		g.setColor(Color.WHITE);
//		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		return image;
	}

	public static BufferedImage cutImage(BufferedImage image, SPTCard_Rect... rects)
	{
		int width = 0;
		int height = 0;
		for (SPTCard_Rect rect : rects)
		{
			width = Math.max(width, rectWidth(rect));
			height += rectHeight(rect);
		}
		BufferedImage bufferedImage = newImage(width, height);
		Graphics g = bufferedImage.getGraphics();
		int left = 0;
		int top = 0;
		for (SPTCard_Rect rect : rects)
		{
			g.drawImage(image, left, top, left + rectWidth(rect), top + rectHeight(rect), rect.left, rect.top, rect.right, rect.bottom, null);
			top += rectHeight(rect);
		}
		return bufferedImage;
	}

	public static BufferedImage mergeImage(ArrayList<BufferedImage> images)
	{
		int width = 0;
		int height = 0;
		for (BufferedImage image : images)
		{
			width = Math.max(width, image.getWidth());
			height += image.getHeight();
		}
		BufferedImage bufferedImage = newImage(width, height);
		Graphics g = bufferedImage.getGraphics();
		int left = 0;
		int top = 0;
		for (BufferedImage image : images)
		{
			g.drawImage(image, left, top, left + image.getWidth(), top + image.getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
			top += image.getHeight();
		}
		return bufferedImage;
	}

	public static void mergeImage(String pathDst, String... pathSrcs)
	{
		ArrayList<BufferedImage> images = readImageList(pathSrcs);
		BufferedImage bufferedImage = mergeImage(images);
		writeImage(bufferedImage, pathDst);
	}

	public static BufferedImage cloneImage(Image image)
	{
		BufferedImage bufferedImage = newImage(image.getWidth(null), image.getHeight(null));
		Graphics g = bufferedImage.getGraphics();
		g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), 0, 0, image.getWidth(null), image.getHeight(null), null);
		return bufferedImage;
	}

	public static BufferedImage[] cloneImages(BufferedImage... images)
	{
		BufferedImage[] bufferedImages = new BufferedImage[images.length];
		for (int i = 0; i < images.length; i++)
		{
			BufferedImage image = images[i];
			bufferedImages[i] = cloneImage(image);
		}
		return bufferedImages;
	}

	public static BufferedImage scaleImageMax(BufferedImage image, float maxWidth)
	{
		int srcWidth = image.getWidth();
		int srcHeight = image.getHeight();
		if (maxWidth < srcWidth)
		{
			float xisu = maxWidth / srcWidth;
			int dstWidth = (int) maxWidth;
			int dstHeight = (int) (srcHeight * xisu);
			Image scaledImage = image.getScaledInstance(dstWidth, dstHeight, Image.SCALE_SMOOTH);
			return cloneImage(scaledImage);
		}
		else
		{
			return image;
		}
	}

	public static BufferedImage scaleImage(BufferedImage image, float dstWidth)
	{
		int srcWidth = image.getWidth();
		int srcHeight = image.getHeight();
		float xisu = dstWidth / srcWidth;
		float dstHeight = srcHeight * xisu;
		Image scaledImage = image.getScaledInstance((int) dstWidth, (int) dstHeight, Image.SCALE_SMOOTH);
		return cloneImage(scaledImage);
	}

	public static BufferedImage scaleImage(BufferedImage image, int dstWidth, int dstHeight)
	{
		Image scaledImage = image.getScaledInstance(dstWidth, dstHeight, Image.SCALE_SMOOTH);
		return cloneImage(scaledImage);
	}

	public static BufferedImage scaleImageRatio(BufferedImage image, double sx, double sy)
	{
		Image scaledImage = image.getScaledInstance((int) (image.getWidth() * sx), (int) (image.getHeight() * sy), Image.SCALE_SMOOTH);
		return cloneImage(scaledImage);
	}

	public static int colorCount(BufferedImage image, SPTCard_Rect rect)
	{
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int x = rect.left; x < rect.right; x++)
		{
			for (int y = rect.top; y < rect.bottom; y++)
			{
				int px = image.getRGB(x, y);
				map.put(px, px);
			}
		}
		return map.size();
	}

	public static int getBlackCount(BufferedImage image, SPTCard_Rect rect)
	{
		int count = 0;
		for (int x = rect.left; x < rect.right; x++)
		{
			for (int y = rect.top; y < rect.bottom; y++)
			{
				if (isInvidPx(image, x, y))
				{
					int px = image.getRGB(x, y);
					px = toSplit(px);
					if (px == Black)
						count++;
				}
			}
		}
		return count;
	}

	public static int toGray(int px)
	{
		byte r = (byte) ((px >> 16) & 0xff);
		byte g = (byte) ((px >> 8) & 0xff);
		byte b = (byte) ((px >> 0) & 0xff);

		int gray = (r * 19595 + g * 38469 + b * 7472) >> 16;

		int grayPx = 0;
		grayPx |= ((gray) & 0xff) << 16;
		grayPx |= ((gray) & 0xff) << 8;
		grayPx |= ((gray) & 0xff) << 0;
		return grayPx;
	}

	public static int getGrayValue(int px)
	{
		byte r = (byte) ((px >> 16) & 0xff);
		byte g = (byte) ((px >> 8) & 0xff);
		byte b = (byte) ((px >> 0) & 0xff);

		int gray = (r * 19595 + g * 38469 + b * 7472) >> 16;

		int grayValue = 0;
		grayValue |= ((gray) & 0xff) << 0;

		return grayValue;
	}

	public static int toSplit(int px)
	{
		int grayValue = getGrayValue(px);

		if (grayValue < 240)
			return Black;
		else
			return White;
	}

	public static void convertToGray(BufferedImage image, SPTCard_Rect rect)
	{
		for (int x = rect.left; x < rect.right; x++)
		{
			for (int y = rect.top; y < rect.bottom; y++)
			{
				int px = image.getRGB(x, y);
				px = toGray(px);
				image.setRGB(x, y, px);
			}
		}
	}

	public static void convertToGray(BufferedImage image)
	{
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				int px = image.getRGB(x, y);
				px = toGray(px);
				image.setRGB(x, y, px);
			}
		}
	}

	public static void convertToSplit(BufferedImage image, SPTCard_Rect rect)
	{
		for (int x = rect.left; x < rect.right; x++)
		{
			for (int y = rect.top; y < rect.bottom; y++)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				image.setRGB(x, y, px);
			}
		}
	}

	public static void convertToSplit(BufferedImage image)
	{
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				image.setRGB(x, y, px);
			}
		}
	}

	public static int rayToRight(BufferedImage image, int xFrom, int len, int y, int findColor)
	{
		for (int x = xFrom; x < xFrom + len; x++)
		{
			if (isInvidPx(image, x, y))
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == findColor)
					return x;
				if (debug)
					image.setRGB(x, y, Red);
			}
		}
		return -1;
	}

	public static int rayToLeft(BufferedImage image, int xFrom, int len, int y, int findColor)
	{
		for (int x = xFrom; x > xFrom - len; x--)
		{
			if (isInvidPx(image, x, y))
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == findColor)
					return x;
				if (debug)
					image.setRGB(x, y, Red);
			}
		}
		return -1;
	}

	public static int rayToBottom(BufferedImage image, int yFrom, int len, int x, int findColor)
	{
		for (int y = yFrom; y < yFrom + len; y++)
		{
			if (isInvidPx(image, x, y))
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == findColor)
					return y;
				if (debug)
					image.setRGB(x, y, Red);
			}
		}
		return -1;
	}

	public static int rayToTop(BufferedImage image, int yFrom, int len, int x, int findColor)
	{
		for (int y = yFrom; y > yFrom - len; y--)
		{
			if (isInvidPx(image, x, y))
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == findColor)
					return y;
				if (debug)
					image.setRGB(x, y, Red);
			}
		}
		return -1;
	}

	public static int getPing(ArrayList<Integer> values)
	{
		Collections.sort(values);

		int valueAll = 0;
		int iFrom = values.size() * 1 / 3;
		int iTo = values.size() * 2 / 3;
		for (int i = iFrom; i < iTo; i++)
		{
			valueAll += values.get(i);
		}
		if (iTo - iFrom > 0)
		{
			int ping = valueAll / (iTo - iFrom);
			return ping;
		}
		else
		{
			return 0;
		}
	}

	public static int rayToRightExt(BufferedImage image, int xFrom, int len, int yCenter, int range)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int y = yCenter - range; y <= yCenter + range; y += rayStep)
		{
			int value = rayToRight(image, xFrom, len, y, Black);
			if (value != -1)
				values.add(value);
		}
		return getPing(values);
	}

	public static int rayToLeftExt(BufferedImage image, int xFrom, int len, int yCenter, int range)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int y = yCenter - range; y <= yCenter + range; y += rayStep)
		{
			int value = rayToLeft(image, xFrom, len, y, Black);
			if (value != -1)
				values.add(value);
		}
		return getPing(values);
	}

	public static int rayToBottomExt(BufferedImage image, int yFrom, int len, int xCenter, int range)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int x = xCenter - range; x <= xCenter + range; x += rayStep)
		{
			int value = rayToBottom(image, yFrom, len, x, Black);
			if (value != -1)
				values.add(value);
		}
		return getPing(values);
	}

	public static int rayToTopExt(BufferedImage image, int yFrom, int len, int xCenter, int range)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int x = xCenter - range; x <= xCenter + range; x += rayStep)
		{
			int value = rayToTop(image, yFrom, len, x, Black);
			if (value != -1)
				values.add(value);
		}
		return getPing(values);
	}

	public static BufferedImage rotate(BufferedImage image, double angle)
	{
		BufferedImage bufferedImage = newImage(image.getWidth(), image.getHeight());
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		g2.rotate(angle, image.getWidth() / 2, image.getHeight() / 2);
		g2.drawImage(image, 0, 0, null);
		return bufferedImage;
	}

	public static BufferedImage rotate90(BufferedImage image)
	{
		BufferedImage bufferedImage = newImage(image.getHeight(), image.getWidth());
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		g2.rotate(Math.PI / 2, image.getHeight() / 2, image.getWidth() / 2);
		g2.drawImage(image, (image.getHeight() - image.getWidth()) / 2, (image.getWidth() - image.getHeight()) / 2, null);
		return bufferedImage;
	}

	public static BufferedImage rotateFu90(BufferedImage image)
	{
		BufferedImage bufferedImage = newImage(image.getHeight(), image.getWidth());
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		g2.rotate(-Math.PI / 2, image.getHeight() / 2, image.getWidth() / 2);
		g2.drawImage(image, (image.getHeight() - image.getWidth()) / 2, (image.getWidth() - image.getHeight()) / 2, null);
		return bufferedImage;
	}

	public static BufferedImage shear(BufferedImage image, double shx, double shy)
	{
		BufferedImage bufferedImage = newImage(image.getWidth(), image.getHeight());
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		g2.shear(shx, shy);
		g2.drawImage(image, 0, 0, null);
		return bufferedImage;
	}

	public static double getRotateAngleByV(SPTCard_Point p1, SPTCard_Point p2)
	{
		return Math.atan(1.0 * (p2.x - p1.x) / (p2.y - p1.y));
	}

	public static double getRotateAngleByH(SPTCard_Point p1, SPTCard_Point p2)
	{
		return Math.atan(1.0 * (p2.y - p1.y) / (p2.x - p1.x));
	}

	public static SPTCard_Point testPoint(BufferedImage image, int x, int y, int rectWidth, int rectHeight)
	{
		if (!isInvidPx(image, x, y))
			return null;
		int px = image.getRGB(x, y);
		px = toSplit(px);
		if (px == Black)
		{
			int count = getBlackCount(image, rectByCenter(x, y, rectWidth, rectHeight));
			if (count > (rectWidth * rectHeight * 2 / 3))
			{
				if (debug)
					drawPoint(image.getGraphics(), Color.RED, x, y, 10);

				int top = 0;
				for (int t = y; t >= 0; t--)
				{
					int c = getBlackCount(image, rectBySize(x - rectWidth / 2, t, rectWidth, 1));
					if (c < Math.max(6, rectWidth / 8))
					{
						top = t;
						break;
					}
				}

				int bottom = 0;
				for (int t = y; t < image.getHeight(); t++)
				{
					int c = getBlackCount(image, rectBySize(x - rectWidth / 2, t, rectWidth, 1));
					if (c < Math.max(6, rectWidth / 8))
					{
						bottom = t;
						break;
					}
				}

				int centerY = (top + bottom) / 2;

				if (debug)
				{
					drawLine(image.getGraphics(), Color.RED, x - 100, top, x + 100, top, 1);
					drawLine(image.getGraphics(), Color.RED, x - 100, bottom, x + 100, bottom, 1);
					drawLine(image.getGraphics(), Color.RED, x - 100, centerY, x + 100, centerY, 1);
				}

				int left = 0;
				for (int t = x; t >= 0; t--)
				{
					int c = getBlackCount(image, rectBySize(t, y - rectHeight / 2, 1, rectHeight));
					if (c < Math.max(6, rectHeight / 8))
					{
						left = t;
						break;
					}
				}

				int right = 0;
				for (int t = x; t < image.getWidth(); t++)
				{
					int c = getBlackCount(image, rectBySize(t, y - rectHeight / 2, 1, rectHeight));
					if (c < Math.max(6, rectHeight / 8))
					{
						right = t;
						break;
					}
				}

				int centerX = (left + right) / 2;

				if (debug)
				{
					drawLine(image.getGraphics(), Color.RED, left, y - 100, left, y + 100, 1);
					drawLine(image.getGraphics(), Color.RED, right, y - 100, right, y + 100, 1);
					drawLine(image.getGraphics(), Color.RED, centerX, y - 100, centerX, y + 100, 1);
				}

				return SPTCard_Point.create(centerX, centerY);
			}
		}
		return null;
	}

	public static SPTCard_Rect shrinkRect(BufferedImage image, SPTCard_Rect rect, int sensitivity, int lineWidth)
	{
		int left = -1;
		for (int t = rect.left; t < (rect.left + rect.right) / 2; t++)
		{
			int c = getBlackCount(image, rectBySize(t, rect.top, lineWidth, rectHeight(rect)));
			if (c > sensitivity)
			{
				left = t;
				break;
			}
		}

		int top = -1;
		for (int t = rect.top; t < (rect.top + rect.bottom) / 2; t++)
		{
			int c = getBlackCount(image, rectBySize(rect.left, t, rectWidth(rect), lineWidth));
			if (c > sensitivity)
			{
				top = t;
				break;
			}
		}

		int right = -1;
		for (int t = rect.right; t > (rect.left + rect.right) / 2; t--)
		{
			int c = getBlackCount(image, rectBySize(t, rect.top, lineWidth, rectHeight(rect)));
			if (c > sensitivity)
			{
				right = t;
				break;
			}
		}

		int bottom = -1;
		for (int t = rect.bottom; t > (rect.top + rect.bottom) / 2; t--)
		{
			int c = getBlackCount(image, rectBySize(rect.left, t, rectWidth(rect), lineWidth));
			if (c > sensitivity)
			{
				bottom = t;
				break;
			}
		}

		if (left != -1 && top != -1 && right != -1 && bottom != -1)
		{
			SPTCard_Rect shrinkRect = rectByPos(left, top, right, bottom);
			return shrinkRect;
		}
		else
		{
			return null;
		}
	}

	public static SPTCard_Rect shrinkLeftTop(BufferedImage image, SPTCard_Rect rect, int sensitivity, int lineWidth)
	{
		int left = -1;
		for (int t = rect.left; t < (rect.left + rect.right) / 2; t++)
		{
			int c = getBlackCount(image, rectBySize(t, rect.top, lineWidth, rectHeight(rect)));
			if (c > sensitivity)
			{
				left = t;
				break;
			}
		}

		int top = -1;
		for (int t = rect.top; t < (rect.top + rect.bottom) / 2; t++)
		{
			int c = getBlackCount(image, rectBySize(rect.left, t, rectWidth(rect), lineWidth));
			if (c > sensitivity)
			{
				top = t;
				break;
			}
		}

		if (left != -1 && top != -1)
		{
			SPTCard_Rect shrinkRect = rectByPos(left, top, rect.right, rect.bottom);
			return shrinkRect;
		}
		else
		{
			return null;
		}
	}

	public static SPTCard_Point findLeftTopPoint(BufferedImage image, int rectWidth, int rectHeight)
	{
		int range = Math.min(image.getWidth(), image.getHeight()) / 2;
		for (int i = 0; i < range; i++)
		{
			{
				int x = i;
				int y = i;
				SPTCard_Point point = testPoint(image, x, y, rectWidth, rectHeight);
				if (point != null)
					return point;
			}
			{
				int x = i;
				for (int y = 0; y < i; y++)
				{
					SPTCard_Point point = testPoint(image, x, y, rectWidth, rectHeight);
					if (point != null)
						return point;
				}
			}
			{
				int y = i;
				for (int x = 0; x < i; x++)
				{
					SPTCard_Point point = testPoint(image, x, y, rectWidth, rectHeight);
					if (point != null)
						return point;
				}
			}
		}
		return null;
	}

	public static SPTCard_Point findRightTopPoint(BufferedImage image, int rectWidth, int rectHeight)
	{
		int range = Math.min(image.getWidth(), image.getHeight()) / 2;
		for (int i = 0; i < range; i++)
		{
			{
				int x = i;
				int y = i;
				SPTCard_Point point = testPoint(image, image.getWidth() - 1 - x, y, rectWidth, rectHeight);
				if (point != null)
					return point;
			}
			{
				int x = i;
				for (int y = 0; y < i; y++)
				{
					SPTCard_Point point = testPoint(image, image.getWidth() - 1 - x, y, rectWidth, rectHeight);
					if (point != null)
						return point;
				}
			}
			{
				int y = i;
				for (int x = 0; x < i; x++)
				{
					SPTCard_Point point = testPoint(image, image.getWidth() - 1 - x, y, rectWidth, rectHeight);
					if (point != null)
						return point;
				}
			}
		}
		return null;
	}

	public static SPTCard_Point findPoint(BufferedImage image, SPTCard_Point center, int range, int step, int rectWidth, int rectHeight)
	{
		SPTCard_Point point = testPoint(image, center.x, center.y, rectWidth, rectHeight);
		if (point != null)
			return point;
		// ImageUtil.drawPoint(image.getGraphics(), Color.RED, center, 2);
		for (int ran = step; ran < range; ran += step)
		{
			{
				int x = center.x + ran;
				for (int y = center.y - ran; y < center.y + ran; y += step)
				{
					point = testPoint(image, x, y, rectWidth, rectHeight);
					if (point != null)
						return point;
					// ImageUtil.drawPoint(image.getGraphics(), Color.RED, pos(x, y), 2);
				}
				x = center.x - ran;
				for (int y = center.y - ran + step; y <= center.y + ran; y += step)
				{
					point = testPoint(image, x, y, rectWidth, rectHeight);
					if (point != null)
						return point;
					// ImageUtil.drawPoint(image.getGraphics(), Color.GREEN, pos(x, y), 2);
				}
			}
			{
				int y = center.y - ran;
				for (int x = center.x - ran; x < center.x + ran; x += step)
				{
					point = testPoint(image, x, y, rectWidth, rectHeight);
					if (point != null)
						return point;
					// ImageUtil.drawPoint(image.getGraphics(), Color.BLUE, pos(x, y), 2);
				}
				y = center.y + ran;
				for (int x = center.x - ran + step; x <= center.x + ran; x += step)
				{
					point = testPoint(image, x, y, rectWidth, rectHeight);
					if (point != null)
						return point;
					// ImageUtil.drawPoint(image.getGraphics(), Color.YELLOW, pos(x, y), 2);
				}
			}
		}
		return null;
	}

	public static ArrayList<SPTCard_Point> findPoints(BufferedImage image, SPTCard_Point posRightTop, SPTCard_Point posRightBottom, int rectWidth, int rectHeight)
	{
		ArrayList<SPTCard_Point> points = new ArrayList<>();
		int x = posRightTop.x;
		for (int y = posRightTop.y + 100; y < posRightBottom.y - 100; y++)
		{
			// ImageUtil.drawPoint(image.getGraphics(), Color.RED, pos(x, y), 5);
			SPTCard_Point point = testPoint(image, x, y, rectWidth, rectHeight);
			if (point != null)
			{
				points.add(point);
				y += 24;
				// break;
			}
		}
		return points;
	}

	public static SPTCard_Point findLeftBottomPoint(BufferedImage image, int rectWidth, int rectHeight)
	{
		int range = Math.min(image.getWidth(), image.getHeight()) / 2;
		for (int i = 0; i < range; i++)
		{
			{
				int x = i;
				int y = i;
				SPTCard_Point point = testPoint(image, x, image.getHeight() - 1 - y, rectWidth, rectHeight);
				if (point != null)
					return point;
			}
			{
				int x = i;
				for (int y = 0; y < i; y++)
				{
					SPTCard_Point point = testPoint(image, x, image.getHeight() - 1 - y, rectWidth, rectHeight);
					if (point != null)
						return point;
				}
			}
			{
				int y = i;
				for (int x = 0; x < i; x++)
				{
					SPTCard_Point point = testPoint(image, x, image.getHeight() - 1 - y, rectWidth, rectHeight);
					if (point != null)
						return point;
				}
			}
		}
		return null;
	}

	public static SPTCard_Point findRightBottomPoint(BufferedImage image, int rectWidth, int rectHeight)
	{
		int range = Math.min(image.getWidth(), image.getHeight()) / 2;
		for (int i = 0; i < range; i++)
		{
			{
				int x = i;
				int y = i;
				SPTCard_Point point = testPoint(image, image.getWidth() - 1 - x, image.getHeight() - 1 - y, rectWidth, rectHeight);
				if (point != null)
					return point;
			}
			{
				int x = i;
				for (int y = 0; y < i; y++)
				{
					SPTCard_Point point = testPoint(image, image.getWidth() - 1 - x, image.getHeight() - 1 - y, rectWidth, rectHeight);
					if (point != null)
						return point;
				}
			}
			{
				int y = i;
				for (int x = 0; x < i; x++)
				{
					SPTCard_Point point = testPoint(image, image.getWidth() - 1 - x, image.getHeight() - 1 - y, rectWidth, rectHeight);
					if (point != null)
						return point;
				}
			}
		}
		return null;
	}

	public static ArrayList<Integer> testLeftBlack(BufferedImage image)
	{
		ArrayList<Integer> wave = new ArrayList<Integer>();
		for (int y = 0; y < image.getHeight(); y++)
		{
			int x;
			for (x = 0; x < image.getWidth(); x++)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == Black)
				{
					// image.setRGB(x, y, White);
				}
				else
				{
					break;
				}
			}
			wave.add(x);
		}
		return wave;
	}

	public static ArrayList<Integer> testTopBlack(BufferedImage image)
	{
		ArrayList<Integer> wave = new ArrayList<Integer>();
		for (int x = 0; x < image.getWidth(); x++)
		{
			int y;
			for (y = 0; y < image.getHeight(); y++)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == Black)
				{
					// image.setRGB(x, y, White);
				}
				else
				{
					break;
				}
			}
			wave.add(y);
		}
		return wave;
	}

	public static ArrayList<Integer> testRightBlack(BufferedImage image)
	{
		ArrayList<Integer> wave = new ArrayList<Integer>();
		for (int y = 0; y < image.getHeight(); y++)
		{
			int x;
			for (x = image.getWidth() - 1; x >= 0; x--)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == Black)
				{
					// image.setRGB(x, y, White);
				}
				else
				{
					break;
				}
			}
			wave.add(x);
		}
		return wave;
	}

	public static ArrayList<Integer> testBottomBlack(BufferedImage image)
	{
		ArrayList<Integer> wave = new ArrayList<Integer>();
		for (int x = 0; x < image.getWidth(); x++)
		{
			int y;
			for (y = image.getHeight() - 1; y >= 0; y--)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == Black)
				{
					// image.setRGB(x, y, White);
				}
				else
				{
					break;
				}
			}
			wave.add(y);
		}
		return wave;
	}

	public static void clearLeftBlack(BufferedImage image, ArrayList<Integer> wave)
	{
		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < wave.get(y); x++)
			{
				image.setRGB(x, y, White);
			}
		}
	}

	public static void clearTopBlack(BufferedImage image, ArrayList<Integer> wave)
	{
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < wave.get(x); y++)
			{
				image.setRGB(x, y, White);
			}
		}
	}

	public static void clearRightBlack(BufferedImage image, ArrayList<Integer> wave)
	{
		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = image.getWidth() - 1; x > wave.get(y); x--)
			{
				image.setRGB(x, y, White);
			}
		}
	}

	public static void clearBottomBlack(BufferedImage image, ArrayList<Integer> wave)
	{
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = image.getHeight() - 1; y > wave.get(x); y--)
			{
				image.setRGB(x, y, White);
			}
		}
	}

	public static void clearLeftBlack(BufferedImage image)
	{
		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == Black)
				{
					image.setRGB(x, y, White);
				}
				else
				{
					break;
				}
			}
		}
	}

	public static void clearTopBlack(BufferedImage image)
	{
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == Black)
				{
					image.setRGB(x, y, White);
				}
				else
				{
					break;
				}
			}
		}
	}

	public static void clearRightBlack(BufferedImage image)
	{
		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = image.getWidth() - 1; x >= 0; x--)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == Black)
				{
					image.setRGB(x, y, White);
				}
				else
				{
					break;
				}
			}
		}
	}

	public static void clearBottomBlack(BufferedImage image)
	{
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = image.getHeight() - 1; y >= 0; y--)
			{
				int px = image.getRGB(x, y);
				px = toSplit(px);
				if (px == Black)
				{
					image.setRGB(x, y, White);
				}
				else
				{
					break;
				}
			}
		}
	}

	public static void clearBlack(BufferedImage image)
	{
		ArrayList<Integer> waveLeft = testLeftBlack(image);
		ArrayList<Integer> waveTop = testTopBlack(image);
		ArrayList<Integer> waveRight = testRightBlack(image);
		ArrayList<Integer> waveBottom = testBottomBlack(image);
		clearLeftBlack(image, waveLeft);
		clearTopBlack(image, waveTop);
		clearRightBlack(image, waveRight);
		clearBottomBlack(image, waveBottom);
	}
}
