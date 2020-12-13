package com.lys.app.other;

import com.lys.base.utils.JsonHelper;
import com.lys.protobuf.SZXTopic;
import com.lys.utils.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class zhixue
{
//	public static final Table T = Table.instance;
//
//	public static void addOneAccount(String account, String psw)
//	{
//		if (!DBHelper.hasRecord(T.zhixueAccount, T.zhixueAccount.account, account))
//		{
//			DBHelper.insert(T.zhixueAccount, //
//					T.zhixueAccount.account, account, //
//					T.zhixueAccount.psw, psw, //
//					T.zhixueAccount.state, "", //
//					T.zhixueAccount.deviceId, "");
//		}
//	}
//
//	public static void addAccount(File accountFile)
//	{
//		if (accountFile.exists())
//		{
//			String text = FsUtils.readText(accountFile);
//			String[] lines = text.split("\r\n");
//			LOG.v("lines : " + lines.length);
//			for (String line : lines)
//			{
//				String[] parts = line.split("\t");
//				addOneAccount(parts[0], parts[1]);
//			}
//		}
//	}

	public static List<SZXTopic> loadTopics(File jsonFile)
	{
		File jsonRawFile = new File(jsonFile.toString() + ".raw");
		if (jsonRawFile.exists())
		{
			String text = FsUtils.readText(jsonRawFile);
			List<SZXTopic> topics = SZXTopic.loadList(JsonHelper.getJSONArray(text));
			return topics;
		}
		else
		{
			String text = FsUtils.readText(jsonFile);

			List<String> contents = new ArrayList<String>();
			List<String> answers = new ArrayList<String>();
			List<String> parses = new ArrayList<String>();

			StringBuilder sb = new StringBuilder();

			String[] lines = text.split("\r\n");
			for (String line : lines)
			{
//				LOG.v(line);
				if (line.trim().startsWith("\"content\":\""))
				{
					contents.add(line.trim().substring("\"content\":\"".length(), line.trim().length() - 2));
				}
				else if (line.trim().startsWith("\"answer\":\""))
				{
					answers.add(line.trim().substring("\"answer\":\"".length(), line.trim().length() - 2));
				}
				else if (line.trim().startsWith("\"parse\":\""))
				{
					parses.add(line.trim().substring("\"parse\":\"".length(), line.trim().length() - 2));
				}
				else
				{
					sb.append(line + "\r\n");
				}
			}

			List<SZXTopic> topics = SZXTopic.loadList(JsonHelper.getJSONArray(sb.toString()));
			if (topics.size() == 0 || topics.size() != contents.size() || topics.size() != answers.size() || topics.size() != parses.size())
			{
				LOG.e(String.format("loadTopics fail : topics.size = %s, contents.size = %s, answers.size = %s, parses.size = %s", topics.size(), contents.size(), answers.size(), parses.size()));
				return null;
			}
			for (int i = 0; i < topics.size(); i++)
			{
				SZXTopic topic = topics.get(i);
				topic.content = contents.get(i);
				topic.answer = answers.get(i);
				topic.parse = parses.get(i);
			}
			FsUtils.writeText(jsonRawFile, SZXTopic.saveList(topics).toString());

			return topics;
		}
	}

	// -------------------------------------------------------------------------------------

//	public static void copyToFailImpl(File file)
//	{
//		if (file.exists())
//		{
//			File dstFile = new File(file.toString().replaceFirst("catch_zhixue", "error_zhixue"));
//			FsUtils.copyFile(file, dstFile, false);
//		}
//	}
//
//	public static void copyToFail(File file)
//	{
//		LOG.v("copyToFail : " + file);
//
//		String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
//
//		File jsonFile = new File(file.getParentFile(), name + ".json");
//		File jsonRawFile = new File(file.getParentFile(), name + ".json.raw");
//		File htmlFile = new File(file.getParentFile(), name + ".html");
////		File topicImageDir = new File(jsonFile.getParentFile(), name);
//		File pngFile = new File(file.getParentFile(), name + ".png");
//
//		ArrayList<File> pngFileParts = new ArrayList<>();
//		for (int i = 0;; i++)
//		{
//			File pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
//			if (pngFilePart.exists())
//				pngFileParts.add(pngFilePart);
//			else
//				break;
//		}
//
//		copyToFailImpl(jsonFile);
//		copyToFailImpl(jsonRawFile);
//		copyToFailImpl(htmlFile);
//		copyToFailImpl(pngFile);
//		for (File pngFilePart : pngFileParts)
//		{
//			copyToFailImpl(pngFilePart);
//		}
//
//		FsUtils.delete(jsonFile);
//		FsUtils.delete(jsonRawFile);
//		FsUtils.delete(htmlFile);
//		FsUtils.delete(pngFile);
//		for (File pngFilePart : pngFileParts)
//		{
//			FsUtils.delete(pngFilePart);
//		}
//	}
//
//	public static boolean processImpl(File file)
//	{
//		String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
//
//		File jsonFile = new File(file.getParentFile(), name + ".json");
//		File topicImageDir = new File(file.getParentFile(), name);
//		File pngFile = new File(file.getParentFile(), name + ".png");
//		File pngFilePart = new File(file.getParentFile(), name + ".00.png");
//		if (jsonFile.exists())
//		{
//			List<SZXTopic> topics = loadTopics(jsonFile);
//
//			if (topics != null && topics.size() > 0)
//			{
//				if (pngFilePart.exists())
//				{
//					TimeDebug.record(name);
//
//					ArrayList<String> pngFilePaths = new ArrayList<>();
//					for (int i = 0;; i++)
//					{
//						pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
//						if (pngFilePart.exists() && FsUtils.getFileSize(pngFilePart) != 92769)
//							pngFilePaths.add(pngFilePart.toString());
//						else
//							break;
//					}
//					ArrayList<BufferedImage> images = ImageUtil.readImageList(pngFilePaths.toArray(new String[pngFilePaths.size()]));
//					for (BufferedImage image : images)
//					{
//						if (image == null)
//						{
//							LOG.e("read images fail");
//							return false;
//						}
//					}
//					BufferedImage image;
//					try
//					{
//						image = ImageUtil.mergeImage(images);
//					}
//					catch (OutOfMemoryError e)
//					{
//						LOG.e("merge images OutOfMemoryError");
//						return false;
//					}
//
//					ImageUtil.writeImage(image, pngFile.toString());
//					LOG.v("write pngFile over : " + pngFile);
//
//					ArrayList<BufferedImage> contentImages = new ArrayList<>();
//					ArrayList<BufferedImage> parseImages = new ArrayList<>();
//					if (cutTopicImages(image, topics, contentImages, parseImages))
//					{
//						FsUtils.createDir(topicImageDir);
//						for (int i = 0; i < contentImages.size(); i++)
//						{
//							BufferedImage contentImage = contentImages.get(i);
//							File contentImageFile = new File(topicImageDir, String.format("%02d.content.png", i + 1));
//							ImageUtil.writeImage(whiteToTransparent(contentImage), contentImageFile.toString());
//						}
//						for (int i = 0; i < parseImages.size(); i++)
//						{
//							BufferedImage parseImage = parseImages.get(i);
//							File parseImageFile = new File(topicImageDir, String.format("%02d.parse.png", i + 1));
//							ImageUtil.writeImage(whiteToTransparent(parseImage), parseImageFile.toString());
//						}
//						LOG.v("cut images success");
//
//						for (int i = 0;; i++)
//						{
//							pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
//							if (pngFilePart.exists())
//								pngFilePart.delete();
//							else
//								break;
//						}
//						LOG.v("delete part images success");
//					}
//					else
//					{
//						LOG.e("cut images fail");
//						return false;
//					}
//				}
//			}
//			else
//			{
//				LOG.e("json parse fail");
//				return false;
//			}
//		}
//		return true;
//	}

	public static boolean processJuan(File file)
	{
		String name = file.getName().substring(0, file.getName().lastIndexOf('.'));

		File jsonFile = new File(file.getParentFile(), name + ".json");
		File topicImageDir = new File(file.getParentFile(), name);
		File pngFile = new File(file.getParentFile(), name + ".png");
		File pngFilePart = new File(file.getParentFile(), name + ".00.png");
		if (jsonFile.exists())
		{
			List<SZXTopic> topics = loadTopics(jsonFile);

			if (topics != null && topics.size() > 0)
			{
				if (pngFilePart.exists())
				{
					TimeDebug.record(name);

					ArrayList<String> pngFilePaths = new ArrayList<>();
					for (int i = 0; ; i++)
					{
						pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
						if (pngFilePart.exists() && FsUtils.getFileSize(pngFilePart) > 100 * 1024)
							pngFilePaths.add(pngFilePart.toString());
						else
							break;
					}
					ArrayList<BufferedImage> images = ImageUtil.readImageList(pngFilePaths.toArray(new String[pngFilePaths.size()]));
					for (BufferedImage image : images)
					{
						if (image == null)
						{
							LOG.e("read images fail");
							return false;
						}
					}
					BufferedImage image;
					try
					{
						image = ImageUtil.mergeImage(images);
					}
					catch (OutOfMemoryError e)
					{
						LOG.e("merge images OutOfMemoryError");
						return false;
					}

					ImageUtil.writeImage(image, pngFile.toString());
					LOG.v("write pngFile over : " + pngFile);

					ArrayList<BufferedImage> contentImages = new ArrayList<>();
					ArrayList<BufferedImage> parseImages = new ArrayList<>();
					if (cutTopicImagesJuan(image, topics, contentImages, parseImages))
					{
						FsUtils.createDir(topicImageDir);
						for (int i = 0; i < contentImages.size(); i++)
						{
							BufferedImage contentImage = contentImages.get(i);
							File contentImageFile = new File(topicImageDir, String.format("%02d.content.png", i + 1));
							ImageUtil.writeImage(whiteToTransparent(contentImage), contentImageFile.toString());
						}
						for (int i = 0; i < parseImages.size(); i++)
						{
							BufferedImage parseImage = parseImages.get(i);
							File parseImageFile = new File(topicImageDir, String.format("%02d.parse.png", i + 1));
							ImageUtil.writeImage(whiteToTransparent(parseImage), parseImageFile.toString());
						}
						LOG.v("cut images success");

//						for (int i = 0;; i++)
//						{
//							pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
//							if (pngFilePart.exists())
//								pngFilePart.delete();
//							else
//								break;
//						}
//						LOG.v("delete part images success");
					}
					else
					{
						LOG.e("cut images fail");
						return false;
					}
				}
			}
			else
			{
				LOG.e("json parse fail");
				return false;
			}
		}
		return true;
	}

	public static boolean processJuan2(File file)
	{
		String name = file.getName().substring(0, file.getName().lastIndexOf('.'));

		File jsonFile = new File(file.getParentFile(), name + ".json");
		File topicImageDir = new File(file.getParentFile(), name);
		File pngFile = new File(file.getParentFile(), name + ".png");
		File pngFilePart = new File(file.getParentFile(), name + ".00.png");
		if (jsonFile.exists())
		{
			List<SZXTopic> topics = loadTopics(jsonFile);

			if (topics != null && topics.size() > 0)
			{
				if (pngFilePart.exists())
				{
					TimeDebug.record(name);

					ArrayList<String> pngFilePaths = new ArrayList<>();
					for (int i = 0; ; i++)
					{
						pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
						if (pngFilePart.exists() && FsUtils.getFileSize(pngFilePart) > 100 * 1024)
							pngFilePaths.add(pngFilePart.toString());
						else
							break;
					}
					ArrayList<BufferedImage> images = ImageUtil.readImageList(pngFilePaths.toArray(new String[pngFilePaths.size()]));
					for (BufferedImage image : images)
					{
						if (image == null)
						{
							LOG.e("read images fail");
							return false;
						}
					}
					BufferedImage image;
					try
					{
						image = ImageUtil.mergeImage(images);
					}
					catch (OutOfMemoryError e)
					{
						LOG.e("merge images OutOfMemoryError");
						return false;
					}

					ImageUtil.writeImage(image, pngFile.toString());
					LOG.v("write pngFile over : " + pngFile);

					ArrayList<BufferedImage> contentImages = new ArrayList<>();
					ArrayList<BufferedImage> parseImages = new ArrayList<>();
					if (cutTopicImagesJuan2(image, topics, contentImages, parseImages))
					{
						FsUtils.createDir(topicImageDir);
						for (int i = 0; i < contentImages.size(); i++)
						{
							BufferedImage contentImage = contentImages.get(i);
							File contentImageFile = new File(topicImageDir, String.format("%02d.content.png", i + 1));
							ImageUtil.writeImage(whiteToTransparent(contentImage), contentImageFile.toString());
						}
						for (int i = 0; i < parseImages.size(); i++)
						{
							BufferedImage parseImage = parseImages.get(i);
							File parseImageFile = new File(topicImageDir, String.format("%02d.parse.png", i + 1));
							ImageUtil.writeImage(whiteToTransparent(parseImage), parseImageFile.toString());
						}
						LOG.v("cut images success");

//						for (int i = 0;; i++)
//						{
//							pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
//							if (pngFilePart.exists())
//								pngFilePart.delete();
//							else
//								break;
//						}
//						LOG.v("delete part images success");
					}
					else
					{
						LOG.e("cut images fail");
						return false;
					}
				}
			}
			else
			{
				LOG.e("json parse fail");
				return false;
			}
		}
		return true;
	}

//	public static boolean process(File dir, boolean failCopy)
//	{
//		boolean success = true;
//		File[] files = dir.listFiles();
//		for (File file : files)
//		{
//			if (file.isFile() && file.getName().endsWith(".html"))
//			{
//				if (!processImpl(file))
//				{
//					success = false;
//					if (failCopy)
//						copyToFail(file);
//				}
//			}
//		}
//		return success;
//	}
//
//	// -------------------------------------------------------------------------------------
//
//	public static boolean process2(File dir)
//	{
//		boolean success = true;
//		File[] files = dir.listFiles();
//		for (File file : files)
//		{
//			if (file.isFile() && file.getName().endsWith(".html"))
//			{
//				String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
//
//				File jsonFile = new File(file.getParentFile(), name + ".json");
//				File topicImageDir = new File(file.getParentFile(), name);
//				File pngFile = new File(file.getParentFile(), name + ".png");
//				File pngFilePart = new File(file.getParentFile(), name + ".00.png");
//				if (jsonFile.exists() && topicImageDir.exists() && topicImageDir.listFiles().length == 0)
//				{
//					LOG.v(name);
//
//					List<SZXTopic> topics = loadTopics(jsonFile);
//
//					if (topics != null && topics.size() > 0)
//					{
//						BufferedImage image = ImageUtil.readImage(pngFile.toString());
//
//						ArrayList<BufferedImage> contentImages = new ArrayList<>();
//						ArrayList<BufferedImage> parseImages = new ArrayList<>();
//						if (cutTopicImages(image, topics, contentImages, parseImages))
//						{
//							FsUtils.createDir(topicImageDir);
//							for (int i = 0; i < contentImages.size(); i++)
//							{
//								BufferedImage contentImage = contentImages.get(i);
//								File contentImageFile = new File(topicImageDir, String.format("%02d.content.png", i + 1));
//								ImageUtil.writeImage(whiteToTransparent(contentImage), contentImageFile.toString());
//							}
//							for (int i = 0; i < parseImages.size(); i++)
//							{
//								BufferedImage parseImage = parseImages.get(i);
//								File parseImageFile = new File(topicImageDir, String.format("%02d.parse.png", i + 1));
//								ImageUtil.writeImage(whiteToTransparent(parseImage), parseImageFile.toString());
//							}
//							LOG.v("cut images success");
//
//							for (int i = 0;; i++)
//							{
//								pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
//								if (pngFilePart.exists())
//									pngFilePart.delete();
//								else
//									break;
//							}
//							LOG.v("delete part images success");
//						}
//						else
//						{
//							LOG.e("cut images fail");
//							success = false;
//						}
//					}
//					else
//					{
//						LOG.e("json parse fail");
//						success = false;
//					}
//				}
////				break;
//			}
//		}
//		return success;
//	}
//
//	// -------------------------------------------------------------------------------------
//
//	public static void copyToSuccessImpl(File dstDir, File file)
//	{
//		if (file.exists())
//		{
//			File dstFile = new File(dstDir, file.getName());
//			FsUtils.copyPath(file, dstFile, false);
//		}
//	}
//
//	public static void copyToSuccess(File file)
//	{
//		LOG.v("copyToSuccess : " + file);
//
//		String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
//
//		File jsonFile = new File(file.getParentFile(), name + ".json");
//		File jsonRawFile = new File(file.getParentFile(), name + ".json.raw");
//		File htmlFile = new File(file.getParentFile(), name + ".html");
//		File topicImageDir = new File(file.getParentFile(), name);
//		File pngFile = new File(file.getParentFile(), name + ".png");
//
//		File dstDir = new File(file.getParentFile().toString().replaceFirst("error_zhixue", "catch_zhixue"));
//		if (!dstDir.exists())
//		{
//			dstDir = new File(dstDir.getParentFile(), dstDir.getName().replaceFirst("over_", "process_"));
//		}
//
//		if (dstDir.exists())
//		{
//			copyToSuccessImpl(dstDir, jsonFile);
//			copyToSuccessImpl(dstDir, jsonRawFile);
//			copyToSuccessImpl(dstDir, htmlFile);
//			copyToSuccessImpl(dstDir, pngFile);
//			copyToSuccessImpl(dstDir, topicImageDir);
//
//			FsUtils.delete(jsonFile);
//			FsUtils.delete(jsonRawFile);
//			FsUtils.delete(htmlFile);
//			FsUtils.delete(pngFile);
//			FsUtils.delete(topicImageDir);
//		}
//		else
//		{
//			LOG.e("error : " + dstDir);
//		}
//	}
//
//	public static void createOneTask(File file, SZXTopic topic)
//	{
//		LOG.v("createOneTask");
//		String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
//		int currPage = Integer.valueOf(name.substring(name.lastIndexOf('.') + 1));
//		if (topic != null)
//		{
//			if (!DBHelper.hasRecord(T.zhixue, //
//					T.zhixue.phase, topic.phase, //
//					T.zhixue.subject, topic.subject, //
//					T.zhixue.material, topic.material, //
//					T.zhixue.diff, topic.diff, //
//					T.zhixue.area, topic.area, //
//					T.zhixue.year, topic.year, //
//					T.zhixue.currChapterPath, topic.chapterPath, //
//					T.zhixue.currPage, currPage))
//			{
//				DBHelper.insert(T.zhixue, //
//						T.zhixue.id, CommonUtils.uuid(), //
//						T.zhixue.phase, topic.phase, //
//						T.zhixue.subject, topic.subject, //
//						T.zhixue.material, topic.material, //
//						T.zhixue.diff, topic.diff, //
//						T.zhixue.area, topic.area, //
//						T.zhixue.year, topic.year, //
//						T.zhixue.currChapterPath, topic.chapterPath, //
//						T.zhixue.currPage, currPage, //
//						T.zhixue.totalPage, -1, //
//						T.zhixue.sort, 100, //
//						T.zhixue.deviceId, "");
//			}
//		}
//		else
//		{
//			File taskDir = file.getParentFile();
//			File subjectDir = taskDir.getParentFile();
//
//			String[] taskDirParts = taskDir.getName().split("_");
//			String[] subjectDirParts = subjectDir.getName().split("_");
//
//			if (!DBHelper.hasRecord(T.zhixue, //
//					T.zhixue.phase, subjectDirParts[0], //
//					T.zhixue.subject, subjectDirParts[1], //
//					T.zhixue.material, subjectDirParts[2], //
//					T.zhixue.diff, taskDirParts[1], //
//					T.zhixue.area, taskDirParts[2], //
//					T.zhixue.year, taskDirParts[3], //
//					T.zhixue.currChapterPath, name.substring(0, name.lastIndexOf('.')), //
//					T.zhixue.currPage, currPage))
//			{
//				DBHelper.insert(T.zhixue, //
//						T.zhixue.id, CommonUtils.uuid(), //
//						T.zhixue.phase, subjectDirParts[0], //
//						T.zhixue.subject, subjectDirParts[1], //
//						T.zhixue.material, subjectDirParts[2], //
//						T.zhixue.diff, taskDirParts[1], //
//						T.zhixue.area, taskDirParts[2], //
//						T.zhixue.year, taskDirParts[3], //
//						T.zhixue.currChapterPath, name.substring(0, name.lastIndexOf('.')), //
//						T.zhixue.currPage, currPage, //
//						T.zhixue.totalPage, -1, //
//						T.zhixue.sort, 100, //
//						T.zhixue.deviceId, "");
//			}
//		}
//	}
//
//	public static boolean processErrorImpl(File file, boolean genTask)
//	{
//		String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
//
//		File jsonFile = new File(file.getParentFile(), name + ".json");
//		File topicImageDir = new File(file.getParentFile(), name);
//		File pngFile = new File(file.getParentFile(), name + ".png");
//		File pngFilePart = new File(file.getParentFile(), name + ".00.png");
//		if (jsonFile.exists())
//		{
//			List<SZXTopic> topics = loadTopics(jsonFile);
//
//			if (topics != null && topics.size() > 0)
//			{
//				if (genTask)
//				{
//					createOneTask(file, topics.get(0));
//					return false;
//				}
//				if (pngFilePart.exists())
//				{
//					LOG.v(name);
//
//					ArrayList<String> pngFilePaths = new ArrayList<>();
//					for (int i = 0;; i++)
//					{
//						pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
//						if (pngFilePart.exists() && FsUtils.getFileSize(pngFilePart) != 92769)
//							pngFilePaths.add(pngFilePart.toString());
//						else
//							break;
//					}
//					ArrayList<BufferedImage> images = ImageUtil.readImageList(pngFilePaths.toArray(new String[pngFilePaths.size()]));
//					for (BufferedImage image : images)
//					{
//						if (image == null)
//						{
//							LOG.e("read images fail");
//							return false;
//						}
//					}
//					BufferedImage image;
//					try
//					{
//						image = ImageUtil.mergeImage(images);
//					}
//					catch (OutOfMemoryError e)
//					{
//						LOG.e("merge images OutOfMemoryError");
//						return false;
//					}
//
//					ImageUtil.writeImage(image, pngFile.toString());
//					LOG.v("write pngFile over : " + pngFile);
//
//					ArrayList<BufferedImage> contentImages = new ArrayList<>();
//					ArrayList<BufferedImage> parseImages = new ArrayList<>();
//					if (cutTopicImages(image, topics, contentImages, parseImages))
//					{
//						FsUtils.createDir(topicImageDir);
//						for (int i = 0; i < contentImages.size(); i++)
//						{
//							BufferedImage contentImage = contentImages.get(i);
//							File contentImageFile = new File(topicImageDir, String.format("%02d.content.png", i + 1));
//							ImageUtil.writeImage(whiteToTransparent(contentImage), contentImageFile.toString());
//						}
//						for (int i = 0; i < parseImages.size(); i++)
//						{
//							BufferedImage parseImage = parseImages.get(i);
//							File parseImageFile = new File(topicImageDir, String.format("%02d.parse.png", i + 1));
//							ImageUtil.writeImage(whiteToTransparent(parseImage), parseImageFile.toString());
//						}
//						LOG.v("cut images success");
//
//						for (int i = 0;; i++)
//						{
//							pngFilePart = new File(file.getParentFile(), String.format("%s.%02d.png", name, i));
//							if (pngFilePart.exists())
//								pngFilePart.delete();
//							else
//								break;
//						}
//						LOG.v("delete part images success");
//					}
//					else
//					{
//						LOG.e("cut images fail");
//						return false;
//					}
//				}
//			}
//			else
//			{
//				if (genTask)
//				{
//					createOneTask(file, null);
//				}
//				LOG.e("json parse fail");
//				return false;
//			}
//		}
//		return true;
//	}
//
//	public static void process3(File dir, boolean genTask)
//	{
//		File[] files = dir.listFiles();
//		for (File file : files)
//		{
//			if (file.isFile() && file.getName().endsWith(".html"))
//			{
//				if (genTask)
//				{
//					processErrorImpl(file, genTask);
//				}
//				else
//				{
//					if (processErrorImpl(file, genTask))
//					{
//						copyToSuccess(file);
//					}
//				}
//			}
//		}
//	}

	// -------------------------------------------------------------------------------------

	public static BufferedImage whiteToTransparent(BufferedImage image)
	{
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				int px = image.getRGB(x, y);
				if (px == ImageUtil.White)
					image.setRGB(x, y, 0x00000000);
			}
		}
		return image;
	}

//	public static boolean cutTopicImages(BufferedImage image, List<SZXTopic> topics, ArrayList<BufferedImage> contentImages, ArrayList<BufferedImage> parseImages)
//	{
////		ImageUtil.convertToSplit(image);
//
//		int x = 1745;
//		int y = 627;
//
//		y = ImageUtil.rayToBottom(image, y, image.getHeight(), x, ImageUtil.Black);
//		if (y == -1)
//		{
//			LOG.v("error @ 001");
//			return false;
//		}
//
//		x = 558;
//		y += 65;
//
////		ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);
//
//		for (int i = 0; i < topics.size(); i++)
//		{
////			LOG.v("topic " + (i + 1));
//
//			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
//			if (y == -1)
//			{
//				LOG.v("error @ code top " + i);
//				return false;
//			}
//			int codeTop = y;
////			LOG.v("code top = " + y);
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);
//
//			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.White);
//			if (y == -1)
//			{
//				LOG.v("error @ code bottom " + i);
//				return false;
//			}
//			int codeBottom = y;
////			LOG.v("code bottom = " + y);
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);
//
//			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
//			if (y == -1)
//			{
//				LOG.v("error @ content bottom " + i);
//				return false;
//			}
//			int contentBottom = y;
////			LOG.v("content bottom = " + y);
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);
//
//			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
//			if (y == -1)
//			{
//				LOG.v("error @ analy top " + i);
//				return false;
//			}
//			int analyTop = y;
////			LOG.v("analy top = " + y);
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);
//
//			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
//			if (y == -1)
//			{
//				LOG.v("error @ analy bottom " + i);
//				return false;
//			}
//			int analyBottom = y;
////			LOG.v("analy bottom = " + y);
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);
//
//			int x2 = 605;
//			int y2 = analyTop;
//
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2, analyTop, x2, analyBottom, 1); // 这个打开会影响程序执行
//
//			y2 = ImageUtil.rayToBottom(image, y2 + 10, analyBottom - analyTop, x2, ImageUtil.Black);
//			if (y2 == -1)
//			{
//				LOG.v("error @ answer arrow " + i);
//				return false;
//			}
//			int answerArrow = y2;
////			LOG.v("answer arrow = " + y2);
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2 - 10, y2, x2 - 2, y2, 1);
//
//			y2 = ImageUtil.rayToBottom(image, y2 + 20, analyBottom - analyTop, x2, ImageUtil.Black);
//			if (y2 == -1)
//			{
//				LOG.v("error @ answer line " + i);
//				return false;
//			}
//			int answerLine = y2;
////			LOG.v("answer line = " + y2);
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2 - 10, y2, x2 - 2, y2, 1);
//
//			ImageUtil.drawLine(image.getGraphics(), Color.WHITE, x2 + 10, answerLine, x2 + 10 + 1176, answerLine, 3);
//
//			y2 = ImageUtil.rayToBottom(image, y2 + 10, analyBottom - analyTop, x2, ImageUtil.Black);
//			if (y2 == -1)
//			{
//				LOG.v("error @ parse arrow " + i);
//				return false;
//			}
//			int parseArrow = y2;
////			LOG.v("parse arrow = " + y2);
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2 - 10, y2, x2 - 2, y2, 1);
//
//			y2 = ImageUtil.rayToBottom(image, y2 + 20, analyBottom - analyTop, x2, ImageUtil.Black);
//			if (y2 == -1)
//			{
//				LOG.v("error @ parse line " + i);
//				return false;
//			}
//			int parseLine = y2;
////			LOG.v("parse line = " + y2);
////			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2 - 10, y2, x2 - 2, y2, 1);
//
//			try
//			{
//				SPTCard_Rect contentRect = BaseUtil.rectByPos(x + 20, codeBottom + 5, x + 20 + 1216, contentBottom - 5);
////				ImageUtil.drawRect(image.getGraphics(), Color.RED, contentRect, 1);
//				contentImages.add(ImageUtil.cutImage(image, contentRect));
//
//				SPTCard_Rect analyRect = BaseUtil.rectByPos(x2 + 170, analyTop + 5, x2 + 170 + 1018, parseLine - 5);
////				ImageUtil.drawRect(image.getGraphics(), Color.RED, analyRect, 1);
//				parseImages.add(ImageUtil.cutImage(image, analyRect));
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//				return false;
//			}
//
////			break;
//		}
//
//		return true;
//	}

	public static boolean cutTopicImagesJuan(BufferedImage image, List<SZXTopic> topics, ArrayList<BufferedImage> contentImages, ArrayList<BufferedImage> parseImages)
	{
//		ImageUtil.convertToSplit(image);

		int x = 490;
		int y = 462;

		y = ImageUtil.rayToBottom(image, y, image.getHeight(), x, ImageUtil.Black);
		if (y == -1)
		{
			LOG.v("error @ 001");
			return false;
		}

//		x = 558;
//		y += 65;

//		ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);

		for (int i = 0; i < topics.size(); i++)
		{
//			LOG.v("topic " + (i + 1));

			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
			if (y == -1)
			{
				LOG.v("error @ code top " + i);
				return false;
			}
			int codeTop = y;
//			LOG.v("code top = " + y);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);

			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.White);
			if (y == -1)
			{
				LOG.v("error @ code bottom " + i);
				return false;
			}
			int codeBottom = y;
//			LOG.v("code bottom = " + y);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);

			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
			if (y == -1)
			{
				LOG.v("error @ content bottom " + i);
				return false;
			}
			int contentBottom = y;
//			LOG.v("content bottom = " + y);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);

			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
			if (y == -1)
			{
				LOG.v("error @ analy top " + i);
				return false;
			}
			int analyTop = y;
//			LOG.v("analy top = " + y);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);

			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
			if (y == -1)
			{
				LOG.v("error @ analy bottom " + i);
				return false;
			}
			int analyBottom = y;
//			LOG.v("analy bottom = " + y);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);

			int x2 = 533;
			int y2 = analyTop;

//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2, analyTop, x2, analyBottom, 1); // 这个打开会影响程序执行

			y2 = ImageUtil.rayToBottom(image, y2 + 10, analyBottom - analyTop, x2, ImageUtil.Black);
			if (y2 == -1)
			{
				LOG.v("error @ answer arrow " + i);
				return false;
			}
			int answerArrow = y2;
//			LOG.v("answer arrow = " + y2);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2 - 10, y2, x2 - 2, y2, 1);

			y2 = ImageUtil.rayToBottom(image, y2 + 20, analyBottom - analyTop, x2, ImageUtil.Black);
			if (y2 == -1)
			{
				LOG.v("error @ answer line " + i);
				return false;
			}
			int answerLine = y2;
//			LOG.v("answer line = " + y2);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2 - 10, y2, x2 - 2, y2, 1);

			ImageUtil.drawLine(image.getGraphics(), Color.WHITE, x2 + 10, answerLine, x2 + 10 + 1176 + 80, answerLine, 3);

			y2 = ImageUtil.rayToBottom(image, y2 + 10, analyBottom - analyTop, x2, ImageUtil.Black);
			if (y2 == -1)
			{
				LOG.v("error @ parse arrow " + i);
				return false;
			}
			int parseArrow = y2;
//			LOG.v("parse arrow = " + y2);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2 - 10, y2, x2 - 2, y2, 1);

			y2 = ImageUtil.rayToBottom(image, y2 + 20, analyBottom - analyTop, x2, ImageUtil.Black);
			if (y2 == -1)
			{
				LOG.v("error @ parse line " + i);
				return false;
			}
			int parseLine = y2;
//			LOG.v("parse line = " + y2);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x2 - 10, y2, x2 - 2, y2, 1);

			try
			{
				SPTCard_Rect contentRect = BaseUtil.rectByPos(x + 20, codeBottom + 5, x + 20 + 1216 + 85, contentBottom - 5);
//				ImageUtil.drawRect(image.getGraphics(), Color.RED, contentRect, 1);
				contentImages.add(ImageUtil.cutImage(image, contentRect));

				SPTCard_Rect analyRect = BaseUtil.rectByPos(x2 + 170, analyTop + 5, x2 + 170 + 1018 + 90, parseLine - 5);
//				ImageUtil.drawRect(image.getGraphics(), Color.RED, analyRect, 1);
				parseImages.add(ImageUtil.cutImage(image, analyRect));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}

//			break;
		}

		return true;
	}

	public static boolean cutTopicImagesJuan2(BufferedImage image, List<SZXTopic> topics, ArrayList<BufferedImage> contentImages, ArrayList<BufferedImage> parseImages)
	{
//		ImageUtil.convertToSplit(image);

		int x = 302;
		int y = 226 - 10 - 50;

		for (int i = 0; i < topics.size(); i++)
		{
//			LOG.v("topic " + (i + 1));

			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
			if (y == -1)
			{
				LOG.v("error @ content top " + i);
				return false;
			}
			int contentTop = y;
//			LOG.v("content top = " + y);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);

			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
			if (y == -1)
			{
				LOG.v("error @ content bottom " + i);
				return false;
			}
			int contentBottom = y;
//			LOG.v("content bottom = " + y);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);

			y = ImageUtil.rayToBottom(image, y + 10, image.getHeight(), x, ImageUtil.Black);
			if (y == -1)
			{
				LOG.v("error @ analy bottom " + i);
				return false;
			}
			int analyBottom = y;
//			LOG.v("analy bottom = " + y);
//			ImageUtil.drawLine(image.getGraphics(), Color.RED, x - 10, y, x - 2, y, 1);

			try
			{
				SPTCard_Rect contentRect = BaseUtil.rectByPos(x, contentTop + 5, x + 1060, contentBottom - 5);
//				ImageUtil.drawRect(image.getGraphics(), Color.RED, contentRect, 1);
				contentImages.add(ImageUtil.cutImage(image, contentRect));

				SPTCard_Rect analyRect = BaseUtil.rectByPos(x + 48, contentBottom + 5, x + 1060, analyBottom - 5);
//				ImageUtil.drawRect(image.getGraphics(), Color.RED, analyRect, 1);
				parseImages.add(ImageUtil.cutImage(image, analyRect));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}

//			break;
		}

		return true;
	}

	// -------------------------------------------------------------------------------------

//	public static boolean doRepeatOneImpl(File file, File topicDir)
//	{
//		String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
//
////		LOG.v(name);
//
//		File jsonRawFile = new File(file.getParentFile(), name + ".json.raw");
//		File topicImageDir = new File(file.getParentFile(), name);
//		if (jsonRawFile.exists())
//		{
//			List<SZXTopic> topics = SZXTopic.loadList(JsonHelper.getJSONArray(FsUtils.readText(jsonRawFile)));
//
//			if (topics != null && topics.size() > 0)
//			{
//				File taskDir = file.getParentFile();
//				String[] taskDirParts = taskDir.getName().split("_");
//				String srcStr = String.format("%s_%s_%s.%s", taskDirParts[1], taskDirParts[2], taskDirParts[3], name);
//
//				for (int i = 0; i < topics.size(); i++)
//				{
//					SZXTopic topic = topics.get(i);
//
//					File contentImageFile = new File(topicImageDir, String.format("%02d.content.png", i + 1));
//					File parseImageFile = new File(topicImageDir, String.format("%02d.parse.png", i + 1));
//
//					if (contentImageFile.exists() && parseImageFile.exists())
//					{
//						int phaseCode = AppConfig.getPhase(topic.phase);
//						int subjectCode = AppConfig.getSubject(topic.subject);
//
//						File dstDir = new File(topicDir, String.format("%d_%d/%s/%s/%s.%s", phaseCode, subjectCode, topic.id.substring(topic.id.length() - 2), topic.id, srcStr, i));
//
//						FsUtils.createDir(dstDir);
//
//						File contentImageFileDst = new File(dstDir, "content.png");
//						File parseImageFileDst = new File(dstDir, "parse.png");
//						File topicJsonFileDst = new File(dstDir, "topic.json");
//						File topicJsonRawFileDst = new File(dstDir, "topic.json.raw");
//
//						FsUtils.copyFile(contentImageFile, contentImageFileDst, false);
//						FsUtils.copyFile(parseImageFile, parseImageFileDst, false);
//						FsUtils.writeText(topicJsonFileDst, LOGJson.getStr(topic.saveToStr()));
//						FsUtils.writeText(topicJsonRawFileDst, topic.saveToStr());
//					}
//					else
//					{
//						LOG.e("content or parse not exists");
//						return false;
//					}
//				}
//			}
//			else
//			{
//				LOG.e("json parse fail");
//				return false;
//			}
//		}
//		else
//		{
//			LOG.e("json raw not exists");
//			return false;
//		}
//		return true;
//	}
//
//	public static boolean doRepeatImpl(File dir, File topicDir)
//	{
//		File[] files = dir.listFiles();
//		for (File file : files)
//		{
//			if (file.isFile() && file.getName().endsWith(".html"))
//			{
//				if (!doRepeatOneImpl(file, topicDir))
//				{
//					return false;
//				}
//			}
//		}
//		return true;
//	}
//
//	// -------------------------------------------------------------------------------------
//
//	public static int[] doTongjiImpl(File topicDir)
//	{
//		int[] records = new int[] { 0, 0, 0, 0, 0 };
//
//		File[] files = topicDir.listFiles();
//		for (File file : files)
//		{
//			if (file.isDirectory())
//			{
//				records[0]++;
//			}
//			else
//			{
////				if (file.getName().endsWith(".png"))
////				{
////					records[1] += FsUtils.getFileSize(file);
////				}
//			}
//		}
//
//		return records;
//	}
//
//	public static final String CheckResult_Good = "good";
//	public static final String CheckResult_BadContent = "badContent";
//	public static final String CheckResult_BadParse = "badParse";
//
//	public static boolean checkTopic(String text)
//	{
//		if (text.indexOf("\\(") >= 0 || text.indexOf("(\\") >= 0 || text.indexOf("^{") >= 0)
//			return false;
//		else
//			return true;
//	}
//
//	public static boolean doCheckImpl(File topicDir, boolean checkDebug)
//	{
//		boolean hasGood = false;
//
//		File[] files = topicDir.listFiles();
//		for (File file : files)
//		{
//			if (file.isDirectory())
//			{
//				File contentImageFileSub = new File(file, "content.png");
//				File parseImageFileSub = new File(file, "parse.png");
//				File topicJsonFileSub = new File(file, "topic.json");
//				File topicJsonRawFileSub = new File(file, "topic.json.raw");
//
//				if (contentImageFileSub.exists() && parseImageFileSub.exists() && topicJsonFileSub.exists() && topicJsonRawFileSub.exists())
//				{
//					SZXTopic topicSub = SZXTopic.load(FsUtils.readText(topicJsonRawFileSub));
//					if (topicSub != null)
//					{
//						String checkResult = CheckResult_Good;
//
//						if (!checkTopic(topicSub.content))
//						{
////							LOG.v("bad content at : " + contentImageFileSub);
//							if (checkResult.equals(CheckResult_Good))
//								checkResult = CheckResult_BadContent;
//							else
//								checkResult += ("|" + CheckResult_BadContent);
//
//							if (checkDebug)
//							{
//								File toFile = contentImageFileSub;
//								for (int i = 0; i < 3; i++)
//								{
//									File parentFile = toFile.getParentFile();
//									toFile = new File(parentFile.getParentFile(), parentFile.getName() + "_" + toFile.getName());
//								}
//								toFile = new File(toFile.getParentFile() + "_bad_content", toFile.getName());
////								LOG.v(toFile.toString());
//								FsUtils.copyFile(contentImageFileSub, toFile, false);
//							}
//						}
//						else
//						{
//							if (checkDebug)
//							{
//								File toFile = contentImageFileSub;
//								for (int i = 0; i < 3; i++)
//								{
//									File parentFile = toFile.getParentFile();
//									toFile = new File(parentFile.getParentFile(), parentFile.getName() + "_" + toFile.getName());
//								}
//								toFile = new File(toFile.getParentFile() + "_good_content", toFile.getName());
////								LOG.v(toFile.toString());
//								FsUtils.copyFile(contentImageFileSub, toFile, false);
//							}
//						}
//
//						if (!checkTopic(topicSub.answer) || !checkTopic(topicSub.parse))
//						{
////							LOG.v("bad parse at   : " + parseImageFileSub);
//							if (checkResult.equals(CheckResult_Good))
//								checkResult = CheckResult_BadParse;
//							else
//								checkResult += ("|" + CheckResult_BadParse);
//
//							if (checkDebug)
//							{
//								File toFile = parseImageFileSub;
//								for (int i = 0; i < 3; i++)
//								{
//									File parentFile = toFile.getParentFile();
//									toFile = new File(parentFile.getParentFile(), parentFile.getName() + "_" + toFile.getName());
//								}
//								toFile = new File(toFile.getParentFile() + "_bad_parse", toFile.getName());
////								LOG.v(toFile.toString());
//								FsUtils.copyFile(parseImageFileSub, toFile, false);
//							}
//						}
//						else
//						{
//							if (checkDebug)
//							{
//								File toFile = parseImageFileSub;
//								for (int i = 0; i < 3; i++)
//								{
//									File parentFile = toFile.getParentFile();
//									toFile = new File(parentFile.getParentFile(), parentFile.getName() + "_" + toFile.getName());
//								}
//								toFile = new File(toFile.getParentFile() + "_good_parse", toFile.getName());
////								LOG.v(toFile.toString());
//								FsUtils.copyFile(parseImageFileSub, toFile, false);
//							}
//						}
//
//						if (checkResult.equals(CheckResult_Good))
//						{
//							hasGood = true;
//						}
//
//						topicSub.checkResult = checkResult;
//						FsUtils.writeText(topicJsonFileSub, LOGJson.getStr(topicSub.saveToStr()));
//						FsUtils.writeText(topicJsonRawFileSub, topicSub.saveToStr());
//					}
//					else
//					{
//						LOG.e("topic load fail at : " + file);
//						return false;
//					}
//				}
//				else
//				{
//					LOG.e("has file not exists at : " + file);
//					return false;
//				}
//			}
//		}
//
//		if (!hasGood)
//		{
//			LOG.e("not good at : " + topicDir);
//			return false;
//		}
//
//		return true;
//	}
//
//	public static String convertChapter(String chapterPath)
//	{
//		String[] chapters = chapterPath.split("---##---");
//		StringBuilder chapterSb = new StringBuilder();
//		for (String chapter : chapters)
//		{
//			if (!TextUtils.isEmpty(chapter))
//			{
//				if (!chapter.contains("#"))
//				{
//					chapterSb.append("#" + chapter + "#");
//				}
//				else
//				{
//					LOG.v("章节错误：" + chapter);
//					return null;
//				}
//			}
//		}
//		if (TextUtils.isEmpty(chapterSb.toString()))
//		{
//			LOG.v("章节为空");
//			return null;
//		}
//		return chapterSb.toString();
//	}
//
//	public static class MergeStruct
//	{
//		public File file;
//		public SZXTopic topicSub;
//
//		public MergeStruct(File file, SZXTopic topicSub)
//		{
//			this.file = file;
//			this.topicSub = topicSub;
//		}
//	}
//
//	public static boolean doMergeImpl(File topicDir)
//	{
//		ArrayList<MergeStruct> mergeStructList = new ArrayList<MergeStruct>();
//
//		File[] files = topicDir.listFiles();
//		for (File file : files)
//		{
//			if (file.isDirectory())
//			{
//				File contentImageFileSub = new File(file, "content.png");
//				File parseImageFileSub = new File(file, "parse.png");
//				File topicJsonFileSub = new File(file, "topic.json");
//				File topicJsonRawFileSub = new File(file, "topic.json.raw");
//
//				if (contentImageFileSub.exists() && parseImageFileSub.exists() && topicJsonFileSub.exists() && topicJsonRawFileSub.exists())
//				{
//					SZXTopic topicSub = SZXTopic.load(FsUtils.readText(topicJsonRawFileSub));
//					if (topicSub != null)
//					{
//						mergeStructList.add(new MergeStruct(file, topicSub));
//					}
//					else
//					{
//						LOG.e("topic load fail at : " + file);
//						return false;
//					}
//				}
//				else
//				{
//					LOG.e("has file not exists at : " + file);
//					return false;
//				}
//			}
//		}
//
//		long findGoodSize = 0;
//		int findGoodIndex = -1;
//		for (int i = 0; i < mergeStructList.size(); i++)
//		{
//			MergeStruct mergeStruct = mergeStructList.get(i);
//			if (mergeStruct.topicSub.checkResult.equals(CheckResult_Good))
//			{
//				File file = mergeStruct.file;
//
//				File contentImageFileSub = new File(file, "content.png");
//				File parseImageFileSub = new File(file, "parse.png");
//				File topicJsonFileSub = new File(file, "topic.json");
//				File topicJsonRawFileSub = new File(file, "topic.json.raw");
//
//				long goodSize = FsUtils.getFileSize(contentImageFileSub) + FsUtils.getFileSize(parseImageFileSub);
//				if (goodSize > findGoodSize)
//				{
//					findGoodSize = goodSize;
//					findGoodIndex = i;
//				}
//			}
//		}
//
//		if (findGoodIndex >= 0)
//		{
//			MergeStruct mergeStructHead = mergeStructList.get(findGoodIndex);
//			mergeStructList.remove(mergeStructHead);
//			mergeStructList.add(0, mergeStructHead);
//
//			File contentImageFileMain = new File(topicDir, "content.png");
//			File parseImageFileMain = new File(topicDir, "parse.png");
//			File topicJsonFileMain = new File(topicDir, "topic.json");
//			File topicJsonRawFileMain = new File(topicDir, "topic.json.raw");
//
//			SZXTopic topicMain = null;
//
//			for (MergeStruct mergeStruct : mergeStructList)
//			{
//				File file = mergeStruct.file;
//				SZXTopic topicSub = mergeStruct.topicSub;
//
//				File contentImageFileSub = new File(file, "content.png");
//				File parseImageFileSub = new File(file, "parse.png");
//				File topicJsonFileSub = new File(file, "topic.json");
//				File topicJsonRawFileSub = new File(file, "topic.json.raw");
//
//				String chapter = convertChapter(topicSub.chapterPath);
//
//				if (TextUtils.isEmpty(chapter))
//				{
//					LOG.e("convertChapter fail at : " + file);
//					return false;
//				}
//
//				if (topicMain == null)
//				{
//					topicMain = topicSub;
//
//					topicMain.chapterPath = chapter;
//
//					FsUtils.copyFile(contentImageFileSub, contentImageFileMain, false);
//					FsUtils.copyFile(parseImageFileSub, parseImageFileMain, false);
//				}
//				else
//				{
//					if (!topicMain.chapterPath.contains(chapter))
//						topicMain.chapterPath = topicMain.chapterPath + "#" + chapter;
//					if (!topicMain.area.contains(topicSub.area))
//						topicMain.area = topicMain.area + "#" + topicSub.area;
//					if (!topicMain.year.contains(topicSub.year))
//						topicMain.year = topicMain.year + "#" + topicSub.year;
//					for (String knowledge : topicSub.knowledges)
//					{
//						if (!topicMain.knowledges.contains(knowledge))
//							topicMain.knowledges.add(knowledge);
//					}
//					if (!topicMain.style.equals(topicSub.style))
//						LOG.v("style not equals at : " + file);
////					if (!topicMain.diff.equals(topicSub.diff))
////						LOG.v("diff not equals at : " + file);
//				}
//			}
//
//			if (topicMain != null)
//			{
//				FsUtils.writeText(topicJsonFileMain, LOGJson.getStr(topicMain.saveToStr()));
//				FsUtils.writeText(topicJsonRawFileMain, topicMain.saveToStr());
//				return true;
//			}
//			else
//			{
//				LOG.e("topicMain is null at : " + topicDir);
//				return false;
//			}
//		}
//		else
//		{
//			LOG.e("not good at : " + topicDir);
//			return false;
//		}
//
//	}
//
//	public static STopic convertTopic(SZXTopic zxTopic, StringBuilder knowledgeSb, StringBuilder chapterSb)
//	{
//		STopic topic = new STopic();
//
//		topic.id = zxTopic.id;
//
//		topic.phase = AppConfig.getPhase(zxTopic.phase);
//		if (topic.phase == AppConfig.ErrorCode)
//		{
//			LOG.v("参数错误：" + zxTopic.phase);
//			return null;
//		}
//
//		topic.subject = AppConfig.getSubject(zxTopic.subject);
//		if (topic.subject == AppConfig.ErrorCode)
//		{
//			LOG.v("参数错误：" + zxTopic.subject);
//			return null;
//		}
//
//		topic.material = zxTopic.material;
//		topic.style = zxTopic.style;
//
//		topic.diff = AppConfig.getDifficulty(zxTopic.diff);
//		if (topic.diff == AppConfig.ErrorCode)
//		{
//			LOG.v("参数错误：" + zxTopic.diff);
//			return null;
//		}
//
//		topic.area = zxTopic.area;
//
//		topic.year = zxTopic.year;
//
//		for (String knowledge : zxTopic.knowledges)
//		{
//			if (!TextUtils.isEmpty(knowledge))
//			{
//				if (!knowledge.contains("#"))
//				{
//					knowledgeSb.append("#" + knowledge + "#");
//				}
//				else
//				{
//					LOG.v("知识点错误：" + knowledge);
//					return null;
//				}
//			}
//		}
//		if (TextUtils.isEmpty(knowledgeSb.toString()))
//		{
//			LOG.v("知识点为空");
//			return null;
//		}
//
//		topic.zujuan = zxTopic.zujuan;
//		topic.zuoda = zxTopic.zuoda;
//
//		topic.defen = -1f;
//		if (!TextUtils.isEmpty(zxTopic.defen))
//		{
//			if (zxTopic.defen.endsWith("%"))
//			{
//				try
//				{
//					topic.defen = Float.valueOf(zxTopic.defen.substring(0, zxTopic.defen.length() - 1));
//				}
//				catch (Exception e)
//				{
//					LOG.v("参数错误：" + zxTopic.defen);
//					return null;
//				}
//			}
//			else
//			{
//				LOG.v("参数错误：" + zxTopic.defen);
//				return null;
//			}
//		}
//
//		topic.nandu = TextUtils.isEmpty(zxTopic.nandu) ? "" : zxTopic.nandu;
//
//		chapterSb.append(zxTopic.chapterPath);
//
//		topic.content = zxTopic.content.replace(" ", "").replace("\t", "").replace(" ", "");
//		topic.answer = zxTopic.answer.replace(" ", "").replace("\t", "").replace(" ", "");
//		topic.parse = zxTopic.parse.replace(" ", "").replace("\t", "").replace(" ", "");
//
//		return topic;
//	}
//
//	public static boolean doDBImpl(File topicDir)
//	{
//		File topicJsonRawFileMain = new File(topicDir, "topic.json.raw");
//
//		if (topicJsonRawFileMain.exists())
//		{
//			SZXTopic zxTopic = SZXTopic.load(FsUtils.readText(topicJsonRawFileMain));
//			if (zxTopic != null)
//			{
//				String tableName = AppConfig.getTopicTableName(zxTopic.phase, zxTopic.subject);
//				if (!TextUtils.isEmpty(tableName))
//				{
//					TopicManager.insureTopicTableExists(tableName);
//
//					StringBuilder knowledgeSb = new StringBuilder();
//					StringBuilder chapterSb = new StringBuilder();
//					STopic topicNew = convertTopic(zxTopic, knowledgeSb, chapterSb);
//					if (topicNew != null)
//					{
//						boolean result = DBHelper.insert(tableName, //
//								T.topic.id, topicNew.id, //
//								T.topic.phase, topicNew.phase, //
//								T.topic.subject, topicNew.subject, //
//								T.topic.material, topicNew.material, //
//								T.topic.style, topicNew.style, //
//								T.topic.diff, topicNew.diff, //
//								T.topic.area, topicNew.area, //
//								T.topic.year, topicNew.year, //
//								T.topic.knowledges, knowledgeSb.toString(), //
//								T.topic.zujuan, topicNew.zujuan, //
//								T.topic.zuoda, topicNew.zuoda, //
//								T.topic.defen, topicNew.defen, //
//								T.topic.nandu, topicNew.nandu, //
//								T.topic.chapters, chapterSb.toString(), //
//								T.topic.content, topicNew.content, //
//								T.topic.answer, topicNew.answer, //
//								T.topic.parse, topicNew.parse);
//						if (result)
//						{
//							return true;
//						}
//						else
//						{
//							LOG.e("insert error at : " + topicDir);
//							return false;
//						}
//					}
//					else
//					{
//						LOG.e("转换错误 at : " + topicDir);
//						return false;
//					}
//				}
//				else
//				{
//					LOG.e("参数错误 at : " + topicDir);
//					return false;
//				}
//			}
//			else
//			{
//				LOG.e("topic load fail at : " + topicDir);
//				return false;
//			}
//		}
//		else
//		{
//			LOG.e("has file not exists at : " + topicDir);
//			return false;
//		}
//	}
//
//	public static boolean doUploadImpl(File topicDir)
//	{
//		File contentImageFileMain = new File(topicDir, "content.png");
//		File parseImageFileMain = new File(topicDir, "parse.png");
//
//		if (contentImageFileMain.exists() && parseImageFileMain.exists())
//		{
//			String path = contentImageFileMain.toString().replace('\\', '/');
//			path = path.substring(path.indexOf("/zhixue_topic/")).replaceFirst("/zhixue_topic/", "").replaceFirst("/_db_", "/");
//			String url = OssUtils.doUploadUntil(OssUtils.ZjykTopic, contentImageFileMain, path);
//			if (TextUtils.isEmpty(url))
//			{
//				LOG.e("upload content fail at : " + topicDir);
//				return false;
//			}
//
//			path = parseImageFileMain.toString().replace('\\', '/');
//			path = path.substring(path.indexOf("/zhixue_topic/")).replaceFirst("/zhixue_topic/", "").replaceFirst("/_db_", "/");
//			url = OssUtils.doUploadUntil(OssUtils.ZjykTopic, parseImageFileMain, path);
//			if (TextUtils.isEmpty(url))
//			{
//				LOG.e("upload parse fail at : " + topicDir);
//				return false;
//			}
//
//			return true;
//		}
//		else
//		{
//			LOG.e("has file not exists at : " + topicDir);
//			return false;
//		}
//	}
//
//	// -------------------------------------------------------------------------------------
//
//	public static void processOver(File rootDir)
//	{
//		File[] subjectDirs = rootDir.listFiles();
//		for (File subjectDir : subjectDirs)
//		{
//			if (subjectDir.isDirectory())
//			{
//				File[] taskDirs = subjectDir.listFiles();
//				for (File taskDir : taskDirs)
//				{
//					if (taskDir.isDirectory() && taskDir.getName().startsWith("over_"))
//					{
//						LOG.v("process : " + taskDir);
//
//						boolean success = zhixue.process(taskDir, true);
//
//						File taskDirFixed = new File(taskDir.getParentFile(), taskDir.getName().replace("over_", "process_"));
//						LOG.v("rename to : " + taskDirFixed.toString());
//						taskDir.renameTo(taskDirFixed);
//					}
//				}
//			}
//		}
//	}
//
//	public static void processError(File rootDir, boolean genTask)
//	{
//		File[] subjectDirs = rootDir.listFiles();
//		for (File subjectDir : subjectDirs)
//		{
//			if (subjectDir.isDirectory())
//			{
//				File[] taskDirs = subjectDir.listFiles();
//				for (File taskDir : taskDirs)
//				{
//					if (taskDir.isDirectory() && taskDir.getName().startsWith("over_"))
//					{
//						LOG.v("process : " + taskDir);
//						zhixue.process3(taskDir, genTask);
//					}
//				}
//			}
//		}
//	}
//
//	public static void tongji(File rootDir, int indent)
//	{
//		if (rootDir.exists() && rootDir.isDirectory())
//		{
////			if (rootDir.getName().startsWith("---##---"))
////				return;
//			HashMap<String, Integer> map = new HashMap<String, Integer>();
//			File[] files = rootDir.listFiles();
//			ArrayList<File> dirs = new ArrayList<File>();
//			for (File file : files)
//			{
//				String type;
//				if (file.isFile())
//				{
//					type = file.getName().substring(file.getName().lastIndexOf('.'));
//				}
//				else
//				{
//					type = "dir";
//					dirs.add(file);
//				}
//				if (map.containsKey(type))
//					map.put(type, map.get(type) + 1);
//				else
//					map.put(type, 1);
//			}
//			StringBuilder sb = new StringBuilder();
//			if (map.size() > 0)
//			{
//				ArrayList<String> keys = new ArrayList<String>(map.keySet());
//				Collections.sort(keys);
//				for (String key : keys)
//				{
//					sb.append(String.format("\t[%s:%d]", key, map.get(key)));
//				}
//			}
//			if (rootDir.getName().startsWith("---##---"))
//			{
//				if (map.size() == 1 && map.get(".png") > 0 && map.get(".png") <= 20 && map.get(".png") % 2 == 0)
//					sb.append(String.format("\tsuccess"));
//				else
//					sb.append(String.format("\tfail"));
//			}
//			else if (rootDir.getName().startsWith("process_") || rootDir.getName().startsWith("fixed_"))
//			{
//				if (map.size() == 5 && //
//						map.get(".html") > 0 && //
//						map.get(".html").equals(map.get(".json")) && //
//						map.get(".html").equals(map.get(".png")) && //
//						map.get(".html").equals(map.get(".raw")) && //
//						map.get(".html").equals(map.get("dir")))
//					sb.append(String.format("\tsuccess"));
//				else
//					sb.append(String.format("\tfail"));
//			}
//			if (sb.toString().endsWith("fail"))
//			{
//				System.err.println(String.format("%s%s%s", CommonUtils.getIndentStr(indent), rootDir.getName(), sb.toString()));
//			}
//			else
//			{
//				if (!rootDir.getName().startsWith("---##---"))
//					System.out.println(String.format("%s%s%s", CommonUtils.getIndentStr(indent), rootDir.getName(), sb.toString()));
//			}
////			if (!rootDir.getName().startsWith("process_") && !rootDir.getName().startsWith("over_") && !rootDir.getName().startsWith("fixed_"))
//			{
//				if (dirs.size() > 0)
//				{
//					Collections.sort(dirs);
//					for (File dir : dirs)
//					{
//						tongji(dir, indent + 1);
//					}
//				}
//			}
//		}
//	}
//
//	public static void doRepeat(File rootDir, File topicDir)
//	{
//		File[] subjectDirs = rootDir.listFiles();
//		for (File subjectDir : subjectDirs)
//		{
//			if (subjectDir.isDirectory())
//			{
//				File[] taskDirs = subjectDir.listFiles();
//				for (File taskDir : taskDirs)
//				{
//					if (taskDir.isDirectory() && taskDir.getName().startsWith("process_"))
//					{
//						LOG.v("doRepeat : " + taskDir);
//						boolean success = zhixue.doRepeatImpl(taskDir, topicDir);
//						if (success)
//						{
//							File taskDirFixed = new File(taskDir.getParentFile(), taskDir.getName().replace("process_", "fixed_"));
//							LOG.v("rename to : " + taskDirFixed.toString());
//							taskDir.renameTo(taskDirFixed);
//						}
//						else
//						{
//							return;
//						}
//					}
//				}
//			}
//		}
//	}
//
//	public static final int TopicDoType_Copy = -1;
//	public static final int TopicDoType_Tj = 0;
//	public static final int TopicDoType_Test = 1;
//	public static final int TopicDoType_Check = 2;
//	public static final int TopicDoType_Merge = 3;
//	public static final int TopicDoType_DB = 4;
//	public static final int TopicDoType_Upload = 5;
//
//	public static void doTopic(File rootDir, File rootDirTo, int doType, boolean isMove, int copyPartCount)
//	{
//		File[] subjectDirs = rootDir.listFiles();
//		for (File subjectDir : subjectDirs)
//		{
//			if (subjectDir.isDirectory())
//			{
//				File[] partDirs = subjectDir.listFiles();
//				int partCount = 0;
//
//				int count = 0;
//				int allCount = 0;
//				long uploadAllSize = 0;
//
//				int nullCount = 0;
//				int checkCount = 0;
//				int badCount = 0;
//				int mergeCount = 0;
//				int dbCount = 0;
//				int failCount = 0;
//				int uploadCount = 0;
//				int unknowCount = 0;
//
//				HashMap<String, Integer> styleMap = new HashMap<String, Integer>();
//				HashMap<String, Integer> diffMap = new HashMap<String, Integer>();
//
//				for (int k = 0; k < partDirs.length; k++)
//				{
//					File partDir = partDirs[k];
//					if (partDir.isDirectory() && partDir.getName().length() == "00".length())
//					{
//						partCount++;
//						LOG.v("================================>>part : " + partDir.toString() + "  " + k + "/" + partDirs.length + "  " + "left:" + (partDirs.length - k));
//						File[] topicDirs = partDir.listFiles();
//						LOG.v("topic count : " + topicDirs.length);
//						for (int i = 0; i < topicDirs.length; i++)
//						{
//							File topicDir = topicDirs[i];
//
//							if (i != 0 && i % 500 == 0)
//								LOG.v(String.format("%s %s%%", i, i * 100 / topicDirs.length));
//
//							if (doType == TopicDoType_Copy)
//							{
//								count++;
//								File topicDirTo = new File(rootDirTo + "/" + subjectDir.getName() + "/" + partDir.getName() + "/" + topicDir.getName());
////								LOG.v(topicDir + " --> " + topicDirTo);
//								copyDir(topicDir, topicDirTo, isMove);
//							}
//							else if (doType == TopicDoType_Tj)
//							{
////								if (topicDir.getName().length() == "000c0abc-31cb-4bca-a507-fc5aaad5675a".length())
////								if (topicDir.getName().startsWith("_check_"))
//								{
//									count++;
//									int[] records = zhixue.doTongjiImpl(topicDir);
//									allCount += records[0];
////									uploadAllSize += records[1];
//								}
//								if (topicDir.getName().length() == "000c0abc-31cb-4bca-a507-fc5aaad5675a".length())
//									nullCount++;
//								else if (topicDir.getName().startsWith("_check_"))
//									checkCount++;
//								else if (topicDir.getName().startsWith("_bad_"))
//									badCount++;
//								else if (topicDir.getName().startsWith("_merge_"))
//									mergeCount++;
//								else if (topicDir.getName().startsWith("_db_"))
//									dbCount++;
//								else if (topicDir.getName().startsWith("_fail_"))
//									failCount++;
//								else if (topicDir.getName().startsWith("_upload_"))
//								{
//									uploadCount++;
//
//									uploadAllSize += FsUtils.getFileSize(new File(topicDir, "content.png"));
//									uploadAllSize += FsUtils.getFileSize(new File(topicDir, "parse.png"));
//
//									SZXTopic topic = SZXTopic.load(FsUtils.readText(new File(topicDir, "topic.json.raw")));
//
//									if (styleMap.containsKey(topic.style))
//										styleMap.put(topic.style, styleMap.get(topic.style) + 1);
//									else
//										styleMap.put(topic.style, 1);
//
//									if (diffMap.containsKey(topic.diff))
//										diffMap.put(topic.diff, diffMap.get(topic.diff) + 1);
//									else
//										diffMap.put(topic.diff, 1);
//								}
//								else
//									unknowCount++;
//							}
//							else if (doType == TopicDoType_Test)
//							{
//								if (topicDir.getName().length() == "000c0abc-31cb-4bca-a507-fc5aaad5675a".length())
////								if (topicDir.getName().startsWith("_check_"))
//								{
//									count++;
////									File newDir = new File(topicDir.getParentFile(), topicDir.getName().replace("_check_", ""));
////									LOG.v("rename to : " + newDir.toString());
////									topicDir.renameTo(newDir);
//								}
//							}
//							else if (doType == TopicDoType_Check)
//							{
//								if (topicDir.getName().length() == "000c0abc-31cb-4bca-a507-fc5aaad5675a".length())
////								if (topicDir.getName().equals("06b6ea11-15cf-4e85-8913-c44688339a0e"))
////								if (topicDir.getName().startsWith("merge_"))
//								{
//									count++;
////									LOG.v("doCheck : " + topicDir);
//									boolean success = zhixue.doCheckImpl(topicDir, false);
//									if (success)
//									{
//										File newDir = new File(topicDir.getParentFile(), "_check_" + topicDir.getName());
////										LOG.v("rename to : " + newDir.toString());
//										topicDir.renameTo(newDir);
//									}
//									else
//									{
//										File newDir = new File(topicDir.getParentFile(), "_bad_" + topicDir.getName());
////										LOG.v("rename to : " + newDir.toString());
//										topicDir.renameTo(newDir);
//									}
//								}
//							}
//							else if (doType == TopicDoType_Merge)
//							{
//								if (topicDir.getName().startsWith("_check_"))
//								{
//									count++;
////									LOG.v("doMerge : " + topicDir);
//									boolean success = zhixue.doMergeImpl(topicDir);
//									if (success)
//									{
//										File newDir = new File(topicDir.getParentFile(), topicDir.getName().replace("_check_", "_merge_"));
////										LOG.v("rename to : " + newDir.toString());
//										topicDir.renameTo(newDir);
//									}
//								}
//							}
//							else if (doType == TopicDoType_DB)
//							{
//								if (topicDir.getName().startsWith("_merge_"))
//								{
//									count++;
////									LOG.v("doDB : " + topicDir);
//									boolean success = zhixue.doDBImpl(topicDir);
//									if (success)
//									{
//										File newDir = new File(topicDir.getParentFile(), topicDir.getName().replace("_merge_", "_db_"));
////										LOG.v("rename to : " + newDir.toString());
//										topicDir.renameTo(newDir);
//									}
//									else
//									{
//										File newDir = new File(topicDir.getParentFile(), topicDir.getName().replace("_merge_", "_fail_"));
////										LOG.v("rename to : " + newDir.toString());
//										topicDir.renameTo(newDir);
//									}
//								}
//							}
//							else if (doType == TopicDoType_Upload)
//							{
//								if (topicDir.getName().startsWith("_db_"))
//								{
//									count++;
////									LOG.v("doUpload : " + topicDir);
//									boolean success = zhixue.doUploadImpl(topicDir);
//									if (success)
//									{
//										File newDir = new File(topicDir.getParentFile(), topicDir.getName().replace("_db_", "_upload_"));
////										LOG.v("rename to : " + newDir.toString());
//										topicDir.renameTo(newDir);
//									}
//								}
//							}
//
//							if (exit)
//								break;
//						}
//						if (doType == TopicDoType_Copy)
//							partDir.renameTo(new File(partDir.getParentFile(), "__" + partDir.getName()));
//						TimeDebug.record("part over");
//						if (partCount >= (copyPartCount - 1))
//							exit = true;
//						if (exit)
//							break;
//					}
//				}
//
//				StringBuilder sb = new StringBuilder();
//
//				sb.append("去重题数 : " + count).append("\r\n");
//				sb.append("不去重题数 : " + allCount).append("\r\n");
//				if (count > 0 && allCount > 0)
//					sb.append("重复倍率 : " + String.format("%.2f", 1.0 * allCount / count)).append("\r\n");
//				sb.append("上传图片总大小 : " + uploadAllSize + "字节（" + CommonUtils.formatSize(uploadAllSize) + "）").append("\r\n");
//
//				if (doType == TopicDoType_Tj && count > 0)
//				{
//					sb.append(String.format("\t%-15s	%10s	%10s%%", "未处理", nullCount, String.format("%.0f", 100.0 * nullCount / count))).append("\r\n");
//					sb.append(String.format("\t%-15s	%10s	%10s%%", "校验成功", checkCount, String.format("%.0f", 100.0 * nullCount / count))).append("\r\n");
//					sb.append(String.format("\t%-15s	%10s	%10s%%", "校验失败", badCount, String.format("%.0f", 100.0 * badCount / count))).append("\r\n");
//					sb.append(String.format("\t%-15s	%10s	%10s%%", "合并完成", mergeCount, String.format("%.0f", 100.0 * mergeCount / count))).append("\r\n");
//					sb.append(String.format("\t%-15s	%10s	%10s%%", "入库成功", dbCount, String.format("%.0f", 100.0 * dbCount / count))).append("\r\n");
//					sb.append(String.format("\t%-15s	%10s	%10s%%", "入库失败", failCount, String.format("%.0f", 100.0 * failCount / count))).append("\r\n");
//					sb.append(String.format("\t%-15s	%10s	%10s%%", "上传完成", uploadCount, String.format("%.0f", 100.0 * uploadCount / count))).append("\r\n");
//					sb.append(String.format("\t%-15s	%10s	%10s%%", "未知文件", unknowCount, String.format("%.0f", 100.0 * unknowCount / count))).append("\r\n");
//
//					sb.append("style : " + styleMap.toString()).append("\r\n");
//					sb.append("diff : " + diffMap.toString()).append("\r\n");
//
//					sb.append("统计时间 : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\r\n");
//
//					System.out.println(sb.toString());
//					FsUtils.writeText(new File(rootDir, String.format("tongji_%s.txt", subjectDir.getName())), sb.toString());
//
//					String[] strs = subjectDir.getName().split("_");
//					genTopicStyle(rootDir, Integer.valueOf(strs[0]), Integer.valueOf(strs[1]), styleMap);
//				}
//
//				if (exit)
//					break;
//			}
//		}
//	}
//
//	public static void genTopicStyle(File outDir, int phaseCode, int subjectCode, HashMap<String, Integer> styleMap)
//	{
//		String tableName = AppConfig.getTopicStyleTableName(phaseCode, subjectCode);
//		List<SProblemStyle> styles = new ArrayList<SProblemStyle>();
//		for (Entry<String, Integer> entry : styleMap.entrySet())
//		{
//			styles.add(SProblemStyle.create(entry.getKey(), false));
//		}
//		File file = new File(outDir, String.format("%s.json", tableName));
//		File fileRaw = new File(outDir, String.format("%s.json.raw", tableName));
//		FsUtils.writeText(file, LOGJson.getStr(SProblemStyle.saveList(styles).toString()));
//		FsUtils.writeText(fileRaw, SProblemStyle.saveList(styles).toString());
//	}
//
//	public static void copyDir(File srcDir, File dstDir, boolean isMove)
//	{
//		if (srcDir.exists() && srcDir.isDirectory())
//		{
//			File[] srcFiles = srcDir.listFiles();
//			for (File srcFile : srcFiles)
//			{
//				File dstFile = new File(dstDir, srcFile.getName());
//				if (srcFile.isDirectory())
//				{
//					copyDir(srcFile, dstFile, isMove);
//				}
//				else
//				{
////					LOG.v(srcFile + " --> " + dstFile);
//					byte[] bytes = FsUtils.readBytes(srcFile);
//					FsUtils.createDir(dstFile.getParentFile());
//					FsUtils.writeBytes(dstFile, bytes);
//					if (isMove)
//						srcFile.delete();
//				}
//			}
//			if (isMove)
//			{
//				if (srcDir.listFiles().length == 0)
//					srcDir.delete();
//				else
//					LOG.e(srcDir + " is not empty !!!");
//			}
//		}
//	}
//
//	public static void copyFile(File srcFile, File dstDir)
//	{
//		File dstFile = new File(dstDir, srcFile.getName());
//		byte[] bytes = FsUtils.readBytes(srcFile);
//		FsUtils.createDir(dstFile.getParentFile());
//		FsUtils.writeBytes(dstFile, bytes);
//	}
//
//	public static boolean exit = false;
//
//	public static void checkError(File dir, String start, boolean delete)
//	{
//		File[] files = dir.listFiles();
//		for (File file : files)
//		{
//			if (file.isFile() && file.getName().endsWith(start))
//			{
//				String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
//
//				File jsonFile = new File(file.getParentFile(), name + ".json");
//				File jsonRawFile = new File(file.getParentFile(), name + ".json.raw");
//				File htmlFile = new File(file.getParentFile(), name + ".html");
//				File topicImageDir = new File(jsonFile.getParentFile(), name);
//				File pngFile = new File(file.getParentFile(), name + ".png");
//
//				boolean flag = true;
//
//				if (!jsonFile.exists())
//				{
//					LOG.v("lose json : " + name);
//					flag = false;
//				}
//				if (!jsonRawFile.exists())
//				{
//					LOG.v("lose raw : " + name);
//					flag = false;
//				}
//				if (!htmlFile.exists())
//				{
//					LOG.v("lose html : " + name);
//					flag = false;
//				}
//				if (!topicImageDir.exists())
//				{
//					LOG.v("lose dir : " + name);
//					flag = false;
//				}
//				if (!pngFile.exists())
//				{
//					LOG.v("lose png : " + name);
//					flag = false;
//				}
//
//				if (flag == false)
//					LOG.e("---------");
//
//				if (flag == false && delete)
//				{
//					jsonFile.delete();
//					jsonRawFile.delete();
//					htmlFile.delete();
//					topicImageDir.delete();
//					pngFile.delete();
//				}
//			}
//		}
//	}
//
//	public static int checkHtmlCount(File dir)
//	{
//		int count = 0;
//		if (dir.exists())
//		{
//			File[] files = dir.listFiles();
//			for (File file : files)
//			{
//				if (file.isFile() && file.getName().endsWith(".html"))
//				{
//					count++;
//				}
//			}
//		}
//		return count;
//	}
//
//	public static void checkOverErrorLv(File rootDir)
//	{
//		File[] subjectDirs = rootDir.listFiles();
//		for (File subjectDir : subjectDirs)
//		{
//			if (subjectDir.isDirectory())
//			{
//				File[] taskDirs = subjectDir.listFiles();
//				for (File taskDir : taskDirs)
//				{
//					if (taskDir.isDirectory() && taskDir.getName().startsWith("process_"))
//					{
//						int successCount = checkHtmlCount(taskDir);
//						File errorDir = new File(taskDir.toString().replaceFirst("catch_zhixue", "error_zhixue").replace("process_", "over_"));
//						int errorCount = checkHtmlCount(errorDir);
//						if (errorCount > 0)
//						{
//							double errorLv = 100.0 * errorCount / (errorCount + successCount);
//							if (errorLv > 50)
//								LOG.e(String.format("%20s\tsuccessCount = %d, errorCount = %d, errorLv = %.0f%%", taskDir.getName(), successCount, errorCount, errorLv));
//							else
//								LOG.v(String.format("%20s\tsuccessCount = %d, errorCount = %d, errorLv = %.0f%%", taskDir.getName(), successCount, errorCount, errorLv));
//						}
//					}
//				}
//			}
//		}
//	}
//
//	public static void main(String[] args)
//	{
//		StaticConfig.isTomcat = false;
//
//		TimeDebug.init();
//
//		if (true)
//		{
////			processJuan2(new File("D:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/pair/juan/试卷数学/立体几何概念判断专练/main.html"));
////			checkError(new File("F:/catch_zhixue/初中_物理_初中物理人教版/process_较难_湖南_2018"), ".png", true);
//
////			addAccount(new File("C:/wangzhiting/work/langyishi/client/java/pair/zhixue_account.txt"));
//
////			checkOverErrorLv(new File("F:/catch_zhixue"));
////			processOver(new File("F:/catch_zhixue"));
////			processError(new File("D:/error_zhixue"), false);
////			tongji(new File("E:/catch_zhixue"), 0);
////			doRepeat(new File("E:/catch_zhixue"), new File("E:/zhixue_topic"));
//
////			List<SZXTopic> topics = loadTopics(new File("F:/catch_zhixue/高中_数学_高中数学人教A版/over_困难_山东_2018/---##---选修1-1---##---第三章 导数及其应用---##---3.1 变化率与导数.00002.json"));
////			BufferedImage image = ImageUtil.readImage("F:/catch_zhixue/高中_数学_高中数学人教A版/over_困难_山东_2018/---##---选修1-1---##---第三章 导数及其应用---##---3.1 变化率与导数.00002.png");
////			ArrayList<BufferedImage> contentImages = new ArrayList<>();
////			ArrayList<BufferedImage> parseImages = new ArrayList<>();
////			cutTopicImages(image, topics, contentImages, parseImages);
////			ImageUtil.writeImage(image, "C:/Users/xnktyu/Desktop/aaaaaabbbbbbb.png");
//
////			File file = new File("D:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/pair/juan/高三数学/2019年全国统一高考数学试卷（理科）（新课标Ⅱ）（海南卷）/main.html");
////			if (file.exists())
////			{
////				if (zhixue.processJuan(file))
////				{
////					LOG.v("ssssssssssssssss");
////				}
////			}
//		}
//		else
//		{
//			new Thread(new Runnable()
//			{
//				@Override
//				public void run()
//				{
////					doTopic(new File("D:/zhixue_topic"), new File("E:/_zhixue_topic"), TopicDoType_Copy, false, 1000);
//					doTopic(new File("E:/zhixue_topic"), new File("E:/xxxx"), TopicDoType_Tj, false, 1000);
//
////					copyDir(new File("G:/zhixue_topic"), new File("H:/zhixue_topic"));
//
//					LOG.v("shutdown !!!");
//					System.exit(0);
//				}
//			}).start();
//
//			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//			String cmd;
//			try
//			{
//				while ((cmd = reader.readLine()) != null)
//				{
//					LOG.v("do cmd : " + cmd);
//					if (cmd.equals("exit"))
//					{
//						exit = true;
//						break;
//					}
//				}
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
//
//		TimeDebug.over("----- over -----");
//
//		LOG.v("-------------------------------- process over --------------------------------");
//	}
}