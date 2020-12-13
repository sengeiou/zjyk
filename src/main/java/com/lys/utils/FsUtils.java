package com.lys.utils;

import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;


public class FsUtils
{
	public static long getFileSize(File file)
	{
		if (file.exists())
		{
			try
			{
				BasicFileAttributeView basicView = Files.getFileAttributeView(Paths.get(file.getAbsolutePath()), BasicFileAttributeView.class);
				BasicFileAttributes basicAttribs = basicView.readAttributes();
				return basicAttribs.size();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static void createDir(File dir)
	{
		if (!dir.exists())
			dir.mkdirs();
	}

	public static String getSuffix(String fileName)
	{
		int pos = fileName.lastIndexOf(".");
		if (pos >= 0)
			return fileName.substring(pos);
		return "";
	}

	public static String getSuffix(File file)
	{
		return getSuffix(file.getName());
	}

	public static void delete(File file)
	{
		if (file.exists())
		{
			if (file.isFile())
			{
				file.delete();
			}
			else
			{
				file.listFiles(new FileFilter()
				{
					@Override
					public boolean accept(File f)
					{
						delete(f);
						return false;
					}
				});
				file.delete();
			}
		}
	}

	public static void deleteDirectoryIfEmpty(File file)
	{
		if (file.exists())
		{
			if (file.isDirectory())
			{
				String[] children = file.list();
				if (children == null || children.length == 0)
				{
					file.delete();
					deleteDirectoryIfEmpty(file.getParentFile());
				}
			}
		}
	}

	private static void searchFilesAdd(final List<File> files, File file, String endStrGroup)
	{
		if (StringUtils.isEmpty(endStrGroup))
		{
			files.add(file);
		}
		else
		{
			String[] endStrArray = endStrGroup.split(";");
			for (String endStr : endStrArray)
			{
				if (file.getName().toLowerCase().endsWith(endStr.toLowerCase()))
				{
					files.add(file);
					break;
				}
			}
		}
	}

	private static void searchFilesImpl(final List<File> files, File dir, String endStrGroup)
	{
		dir.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File file)
			{
				if (file.isFile())
				{
					searchFilesAdd(files, file, endStrGroup);
				}
				else
				{
					searchFilesImpl(files, file, endStrGroup);
				}
				return false;
			}
		});
	}

	public static List<File> searchFiles(File file, String endStrGroup)
	{
		List<File> files = new ArrayList<>();
		if (file.exists())
		{
			if (file.isFile())
			{
				searchFilesAdd(files, file, endStrGroup);
			}
			else
			{
				searchFilesImpl(files, file, endStrGroup);
			}
		}
		return files;
	}

	public static List<File> searchFiles(File file)
	{
		return searchFiles(file, null);
	}

	public static void copyFile(File src, File dst)
	{
		if (src.exists() && src.isFile())
		{
			createDir(dst.getParentFile());
			try
			{
				FileInputStream fis = new FileInputStream(src);
				FileOutputStream fos = new FileOutputStream(dst);
				byte[] buffer = new byte[16 * 1024];
				int hasRead = 0;
				while ((hasRead = fis.read(buffer)) > 0)
				{
					fos.write(buffer, 0, hasRead);
				}
				fis.close();
				fos.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// 拷贝文件或目录
	public static void copyPath(File srcFile, File dstFile)
	{
		if (srcFile.exists())
		{
			if (srcFile.isFile())
			{
				copyFile(srcFile, dstFile);
			}
			else
			{
				int len = srcFile.getAbsolutePath().length();
				for (File srcF : searchFiles(srcFile))
				{
					File dstF = new File(dstFile.getAbsolutePath() + srcF.getAbsolutePath().substring(len));
					copyFile(srcF, dstF);
				}
			}
		}
	}

	public static byte[] readBytes(File file)
	{
		if (file.exists() && file.isFile())
		{
			byte[] buffer = null;
			try
			{
				FileInputStream fis = new FileInputStream(file);
				buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return buffer;
		}
		else
		{
			return null;
		}
	}

	public static String readText(File file)
	{
		byte[] buffer = readBytes(file);
		if (buffer != null)
			return new String(buffer, 0, buffer.length, Charset.forName("UTF-8"));
		else
			return null;
	}

	public static String readText(File file, String charsetName)
	{
		byte[] buffer = readBytes(file);
		if (buffer != null)
			return new String(buffer, 0, buffer.length, Charset.forName(charsetName));
		else
			return null;
	}

	public static void writeBytes(File file, InputStream is)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[16 * 1024];
			int hasRead = 0;
			while ((hasRead = is.read(buffer)) > 0)
			{
				fos.write(buffer, 0, hasRead);
			}
			fos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeBytes(File file, byte[] bytes, int off, int len)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes, off, len);
			fos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeBytes(File file, byte[] bytes)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes, 0, bytes.length);
			fos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeText(File file, String text)
	{
		writeBytes(file, text.getBytes(Charset.forName("UTF-8")));
	}

	public static void appendBytes(File file, byte[] bytes)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file, true);
			fos.write(bytes, 0, bytes.length);
			fos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void appendText(File file, String text)
	{
		appendBytes(file, text.getBytes(Charset.forName("UTF-8")));
	}
}
