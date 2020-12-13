package com.lys.app.other;

import com.lys.app.manager.OssUtils;
import com.lys.protobuf.*;
import com.lys.utils.*;
import org.apache.http.util.TextUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class client
{
	//	public static final String channel_main = "main";
//	public static final String channel_yyb = "yyb";
//	public static final String channel_master = "master";
//
//	// 查老师服
////	public static final String API = "http://192.168.31.247/pair/api";
////	public static final String UPLOAD = "http://192.168.31.247/pair/upload";
//
//	// 我的服
////	public static final String RUN = "http://192.168.31.128/pair/run";
////	public static final String API = "http://192.168.31.128/pair/api";
////	public static final String UPLOAD = "http://192.168.31.128/pair/upload";
//
//	// 我的外网服
////	public static final String API = "http://47.96.82.69/pair/api";
////	public static final String UPLOAD = "http://47.96.82.69/pair/upload";
//
//	// 智学中心服
//	public static final String RUN = "http://39.104.58.109/pair/run";
	public static final String API = "http://39.104.58.109/zjyk/logic/api";
//	public static final String UPLOAD = "http://39.104.58.109/pair/upload";
//
////	public static final String SEARCH = "http://39.104.58.109:8078/getResources";
//
//	public static String doUpload(File file, String path)
//	{
//		MultipartBody.Builder contentBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
////		contentBuilder.addFormDataPart("md5", CommonUtils.md5(file));
//		contentBuilder.addFormDataPart("path", path);
//		contentBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
//		RequestBody requestBody = contentBuilder.build();
////		LOG.v("upload : " + file + " --> " + path);
//		String jsonStr = HttpUtils.doHttpPostImpl(UPLOAD, requestBody);
//		if (!TextUtils.isEmpty(jsonStr))
//		{
//			SProtocol trans = null;
//			try
//			{
////				LOG.v("upload result : " + jsonStr);
//				trans = SProtocol.load(jsonStr);
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//			if (trans != null)
//			{
//				if (trans.code == 200)
//				{
//					return trans.data;
//				}
//				else
//				{
//					return null;
//				}
//			}
//			else
//			{
//				return null;
//			}
//		}
//		else
//		{
//			return null;
//		}
//	}

	public static String doPost(String api, int handleId, String data)
	{
		SProtocol transSend = new SProtocol();
		transSend.handleId = handleId;
		transSend.data = data;
//		LOG.v("上行--------" + SHandleId.name(handleId) + "---------" + handleId + "---------");
//		LOGJson.log(transSend.saveToStr());
		String jsonStr = HttpUtils.doHttpPost(api, transSend.saveToStr());
		if (!TextUtils.isEmpty(jsonStr))
		{
			SProtocol transRcv = null;
			try
			{
//				LOG.v("下行--------" + SHandleId.name(handleId) + "---------" + handleId + "---------");
//				LOGJson.log(jsonStr);
				transRcv = SProtocol.load(jsonStr);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (transRcv != null)
			{
				if (transRcv.code == 200)
				{
					return transRcv.data;
				}
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

////	public static String doSearch(String api, String content, Integer subject, Boolean searchSort, Integer start, Integer rows)
////	{
////		SRequest_GetResources request = new SRequest_GetResources();
////		request.content = content;
////		request.recommend = 0;
////		request.share = 0;
////		request.subject = subject;
////		request.width = 960;
////		request.searchSort = searchSort;
////		request.start = start;
////		request.rows = rows;
////		LOG.v(request.saveToStr());
////		String jsonStr = HttpUtils.doHttpPost(api, request.saveToStr());
////		if (!TextUtils.isEmpty(jsonStr))
////		{
////			SProtocol transRcv = null;
////			try
////			{
//////				LOGJson.log(jsonStr);
////				transRcv = SProtocol.load(jsonStr);
////			}
////			catch (Exception e)
////			{
////				e.printStackTrace();
////			}
////			if (transRcv != null)
////			{
////				if (transRcv.code == 200)
////				{
////					return transRcv.data;
////				}
////				else
////				{
////					return null;
////				}
////			}
////			else
////			{
////				return null;
////			}
////		}
////		else
////		{
////			return null;
////		}
////	}
//
//	public static void uploadAppInfo(File apkFile, File icoFile, SSAppInfo app)
//	{
//		if (!apkFile.exists())
//		{
//			LOG.v(apkFile + "不存在");
//			return;
//		}
//
//		if (!icoFile.exists())
//		{
//			LOG.v(icoFile + "不存在");
//			return;
//		}
//
//		app.size = FsUtils.getFileSize(apkFile);
//
//		String url = OssUtils.doUpload(OssUtils.ZjykFile, apkFile, String.format("apk/%s_%s.apk", app.pkgName, app.channel));
//		if (TextUtils.isEmpty(url))
//		{
//			LOG.v("上传失败");
//			return;
//		}
//		app.apkUrl = url;
//
//		url = OssUtils.doUpload(OssUtils.ZjykFile, icoFile, String.format("apk/%s_%s.png", app.pkgName, app.channel));
//		if (TextUtils.isEmpty(url))
//		{
//			LOG.v("上传失败");
//			return;
//		}
//		app.icoUrl = url;
//
//		SRequest_SetAppInfo request = new SRequest_SetAppInfo();
//		request.app = app;
//		String data = doPost(API, SHandleId.SetAppInfo, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_SetAppInfo response = SResponse_SetAppInfo.load(data);
//		}
//	}
//
//	public static void uploadAppInfo(File dir, SSAppInfo app)
//	{
//		File jsonFile = new File(dir, "release/output.json");
//		File apkFile = new File(dir, "release/app-release.apk");
//		File icoFile = new File(dir, "ic_launcher.png");
//
//		if (!jsonFile.exists())
//		{
//			LOG.v(jsonFile + "不存在");
//			return;
//		}
//
//		JSONArray array = JsonHelper.getJSONArray(new String(FsUtils.readBytes(jsonFile)));
//		JSONObject obj = array.getJSONObject(0);
//		app.versionCode = obj.getJSONObject("apkData").getInteger("versionCode");
//		app.versionName = obj.getJSONObject("apkData").getString("versionName");
//
//		uploadAppInfo(apkFile, icoFile, app);
//	}
//
//	public static void uploadAppInfo_main()
//	{
////		if (false)
////		{
////			File apkFile = new File("C:/wangzhiting/work/code/client/as3/apk/app_pair/release/app-release.apk");
////			File icoFile = new File("C:/wangzhiting/work/code/client/as3/apk/app_pair/ic_launcher.png");
////			SSAppInfo app = new SSAppInfo();
////			app.pkgName = "com.lys.app.pair";
////			app.versionCode = 19010100;
////			app.versionName = "1.0";
////			app.probability = 1f;
////			app.name = "一对一";
////			app.des = "描述描述描述描述描述描述描述描述描述";
////			uploadAppInfo(apkFile, icoFile, app);
////		}
//		if (false)
//		{
//			SSAppInfo app = new SSAppInfo();
//			app.channel = channel_main;
//			app.pkgName = "com.lys.app.pair";
//			app.probability = 1f;
//			app.name = "小翼伴学";
//			app.des = "描述描述描述描述描述描述描述描述描述";
//			uploadAppInfo(new File("C:/wangzhiting/work/code/client/as3/apk/app_pair"), app);
//		}
//		if (false)
//		{
//			SSAppInfo app = new SSAppInfo();
//			app.channel = channel_main;
//			app.pkgName = "com.lys.app.desktop";
//			app.probability = 1f;
//			app.name = "小翼伴学";
//			app.des = "描述描述描述描述描述描述描述描述描述";
//			uploadAppInfo(new File("C:/wangzhiting/work/code/client/as3/apk/app_pair"), app);
//		}
//		if (false)
//		{
//			SSAppInfo app = new SSAppInfo();
//			app.channel = channel_main;
//			app.pkgName = "com.lys.app.math";
//			app.probability = 1f;
//			app.name = "振宇数学";
//			app.des = "描述描述描述描述描述描述描述描述描述";
//			uploadAppInfo(new File("C:/wangzhiting/work/code/client/as3/apk/app_math"), app);
//		}
//		if (false)
//		{
//			SSAppInfo app = new SSAppInfo();
//			app.channel = channel_yyb;
//			app.pkgName = "com.lys.app.math";
//			app.probability = 1f;
//			app.name = "振宇数学";
//			app.des = "描述描述描述描述描述描述描述描述描述";
//			uploadAppInfo(new File("C:/wangzhiting/work/code/client/as3/apk/app_math"), app);
//		}
////		if (true)
////		{
////			SSAppInfo app = new SSAppInfo();
////			app.channel = channel_master;
////			app.versionName = "1.0";
////			app.probability = 1f;
////			app.name = "一对一";
////			app.des = "描述描述描述描述描述描述描述描述描述";
////			uploadAppInfo(new File("C:/wangzhiting/work/code/client/as3/apk/app_pair_master"), app);
////		}
//	}
//
//	public static void uploadAppInfo_master()
//	{
//		if (true)
//		{
//			SSAppInfo app = new SSAppInfo();
//			app.channel = channel_master;
//			app.versionName = "1.0";
//			app.probability = 1f;
//			app.name = "小翼伴学管理";
//			app.des = "描述描述描述描述描述描述描述描述描述";
//			uploadAppInfo(new File("C:/wangzhiting/work/code/client/as3/apk/app_pair_master"), app);
//		}
//	}
//
//	public static void uploadAppInfo_market()
//	{
//		if (true)
//		{
//			SSAppInfo app = new SSAppInfo();
//			app.channel = "market";
//			app.versionName = "1.0";
//			app.probability = 1f;
//			app.name = "笔记本";
//			app.des = "笔记本笔记本笔记本笔记本";
//			uploadAppInfo(new File("C:/wangzhiting/work/code/client/as3/apk/app_note"), app);
//		}
//	}
//
//	public static void uploadAppInfo_zhixue()
//	{
////		if (false)
//		{
//			SSAppInfo app = new SSAppInfo();
//			app.channel = "zhixue";
//			app.pkgName = "com.lys.app.pair.zhixue";
//			app.probability = 1f;
//			app.name = "智学插件JUAN";
//			app.des = "描述描述描述描述描述描述描述描述描述";
//			uploadAppInfo(new File("C:/wangzhiting/work/code/client/as3/apk/app_pair"), app);
//		}
//	}
//
//	public static void printPx(float width)
//	{
//		for (int i = 1920; i > 0; i--)
//		{
//			System.out.println(String.format("<dimen name=\"px_f%s\">-%spx</dimen>", i, Math.round(i * width / 1920)));
//		}
//		for (int i = 0; i <= 1920; i++)
//		{
//			System.out.println(String.format("<dimen name=\"px_%s\">%spx</dimen>", i, Math.round(i * width / 1920)));
//		}
//	}
//
//	public static void printSp(float scale)
//	{
//		for (int i = 0; i <= 200; i++)
//		{
//			System.out.println(String.format("<dimen name=\"sp_%s\">%ssp</dimen>", i, Math.round(i * scale)));
//		}
//	}
//
////	public static void searchResource()
////	{
////		{
////			String data = doSearch(SEARCH, "双曲线上的点", 2, false, 0, 5);
////			if (!TextUtils.isEmpty(data))
////			{
////				SResponse_GetResources response = SResponse_GetResources.load(data);
////				LOGJson.log(response.saveToStr());
////			}
////		}
////		{
////			String data = doSearch(SEARCH, "双曲线上的点", 2, false, 5, 5);
////			if (!TextUtils.isEmpty(data))
////			{
////				SResponse_GetResources response = SResponse_GetResources.load(data);
////				LOGJson.log(response.saveToStr());
////			}
////		}
////	}
//
//	public static void genKnowledgeTree(File rootDir, int phase, int subject)
//	{
//		File[] files = rootDir.listFiles();
//		for (File file : files)
//		{
//			if (file.isFile() && file.getName().endsWith("_knowledges.json.raw"))
//			{
//				LOG.v("gen : " + file);
//
//				SZXKnowledgeTree tree = SZXKnowledgeTree.load(FsUtils.readText(file));
//
//				int phaseCode = AppConfig.getPhase(tree.phase);
//				int subjectCode = AppConfig.getSubject(tree.subject);
//				if (phaseCode == phase && subjectCode == subject)
//				{
//					SRequest_ZXGenKnowledgeTree request = new SRequest_ZXGenKnowledgeTree();
//					request.knowledgeTree = tree;
//					String data = doPost(API, SHandleId.ZXGenKnowledgeTree, request.saveToStr());
//					if (TextUtils.isEmpty(data))
//					{
//						LOG.e("gen fail !!!");
//						break;
//					}
//				}
//			}
//		}
//	}
//
//	public static void genChapterTree(File rootDir, int phase, int subject)
//	{
//		File[] subjectDirs = rootDir.listFiles();
//		for (File subjectDir : subjectDirs)
//		{
//			if (subjectDir.isDirectory())
//			{
//				File file = new File(subjectDir, "chapters.json.raw");
//				if (file.exists())
//				{
//					LOG.v("gen : " + file);
//
//					SZXChapterTree tree = SZXChapterTree.load(FsUtils.readText(file));
//
//					int phaseCode = AppConfig.getPhase(tree.phase);
//					int subjectCode = AppConfig.getSubject(tree.subject);
//					if (phaseCode == phase && subjectCode == subject)
//					{
//						SRequest_ZXGenChapterTree request = new SRequest_ZXGenChapterTree();
//						request.chapterTree = tree;
//						String data = doPost(API, SHandleId.ZXGenChapterTree, request.saveToStr());
//						if (TextUtils.isEmpty(data))
//						{
//							LOG.e("gen fail !!!");
//							break;
//						}
//					}
//				}
//				else
//				{
//					LOG.e("file not exists : " + file);
//					break;
//				}
//			}
//		}
//	}
//
//	public static boolean addTopicFile(File file, File topicImgDir)
//	{
//		String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
//
//		File jsonFile = new File(file.getParentFile(), name + ".json");
//		File topicImageDir = new File(file.getParentFile(), name);
//		if (jsonFile.exists())
//		{
//			LOG.v(name);
//
//			List<SZXTopic> topics = zhixue.loadTopics(jsonFile);
//
//			if (topics.size() > 0)
//			{
//				SRequest_ZXAddTopic request = new SRequest_ZXAddTopic();
//				request.topics = topics;
//				String data = doPost(API, SHandleId.ZXAddTopic, request.saveToStr());
//				if (!TextUtils.isEmpty(data))
//				{
//					SResponse_ZXAddTopic response = SResponse_ZXAddTopic.load(data);
//					for (int i : response.addIndexs)
//					{
//						SZXTopic topic = topics.get(i);
//
//						File contentImageFile = new File(topicImageDir, String.format("%02d.content.png", i + 1));
//						File parseImageFile = new File(topicImageDir, String.format("%02d.parse.png", i + 1));
//
//						int phaseCode = AppConfig.getPhase(topic.phase);
//						int subjectCode = AppConfig.getSubject(topic.subject);
//
//						File contentImageFileDst = new File(topicImgDir, String.format("%d_%d/%s.content.png", phaseCode, subjectCode, topic.id));
//						File parseImageFileDst = new File(topicImgDir, String.format("%d_%d/%s.parse.png", phaseCode, subjectCode, topic.id));
//
//						FsUtils.copyFile(contentImageFile, contentImageFileDst, false);
//						FsUtils.copyFile(parseImageFile, parseImageFileDst, false);
//					}
//					for (int i : response.repeatIndexs)
//					{
//						SZXTopic topic = topics.get(i);
//
//						File contentImageFile = new File(topicImageDir, String.format("%02d.content.png", i + 1));
//						File parseImageFile = new File(topicImageDir, String.format("%02d.parse.png", i + 1));
//
//						int phaseCode = AppConfig.getPhase(topic.phase);
//						int subjectCode = AppConfig.getSubject(topic.subject);
//
//						File contentImageFileDst = new File(topicImgDir, String.format("%d_%d/%s.content.png", phaseCode, subjectCode, topic.id));
//						File parseImageFileDst = new File(topicImgDir, String.format("%d_%d/%s.parse.png", phaseCode, subjectCode, topic.id));
//
//						String contentImageFileMd5 = CommonUtils.md5(contentImageFile);
//						if (!contentImageFileMd5.equals(CommonUtils.md5(contentImageFileDst)))
//						{
//							for (int k = 0;; k++)
//							{
//								contentImageFileDst = new File(String.format("%s-%d/%d_%d/%s.content.png", topicImgDir, k, phaseCode, subjectCode, topic.id));
//								if (contentImageFileDst.exists())
//								{
//									if (contentImageFileMd5.equals(CommonUtils.md5(contentImageFileDst)))
//										break;
//								}
//								else
//								{
//									FsUtils.copyFile(contentImageFile, contentImageFileDst, false);
//									break;
//								}
//							}
//						}
//
//						String parseImageFileMd5 = CommonUtils.md5(parseImageFile);
//						if (!parseImageFileMd5.equals(CommonUtils.md5(parseImageFileDst)))
//						{
//							for (int k = 0;; k++)
//							{
//								parseImageFileDst = new File(String.format("%s-%d/%d_%d/%s.parse.png", topicImgDir, k, phaseCode, subjectCode, topic.id));
//								if (parseImageFileDst.exists())
//								{
//									if (parseImageFileMd5.equals(CommonUtils.md5(parseImageFileDst)))
//										break;
//								}
//								else
//								{
//									FsUtils.copyFile(parseImageFile, parseImageFileDst, false);
//									break;
//								}
//							}
//						}
//					}
//				}
//				else
//				{
//					LOG.e("add topic fail");
//					return false;
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
//
//	public static boolean addTopicTaskDir(File dir, File topicImgDir)
//	{
//		boolean success = true;
//		File[] files = dir.listFiles();
//		for (File file : files)
//		{
//			if (file.isFile() && file.getName().endsWith(".json"))
//			{
//				if (!addTopicFile(file, topicImgDir))
//				{
//					success = false;
//					break;
//				}
//			}
//		}
//		return success;
//	}
//
//	public static void addTopic(File rootDir, File topicImgDir)
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
//						LOG.v("add : " + taskDir);
//						boolean success = addTopicTaskDir(taskDir, topicImgDir);
//						if (success)
//						{
//							File taskDirFixed = new File(taskDir.getParentFile(), taskDir.getName().replace("process_", "fixed_"));
//							LOG.v("rename to : " + taskDirFixed.toString());
//							taskDir.renameTo(taskDirFixed);
//						}
//						else
//						{
//							LOG.e("add fail !!!");
//							return;
//						}
//						LOG.v("-------------------- wait 30 ...... ------------------------");
//						try
//						{
//							Thread.sleep(30 * 1000);
//						}
//						catch (InterruptedException e)
//						{
//						}
//						LOG.v("-------------------- wait over ------------------------");
//					}
//				}
//			}
//		}
//	}
//
//	public static void genTopicStyle(File outDir)
//	{
//		{
//			String tableName = AppConfig.getTopicStyleTableName(SPhase.Gao, SSubject.Shu);
//			List<SProblemStyle> styles = new ArrayList<SProblemStyle>();
//			styles.add(SProblemStyle.create("选择题", true));
//			styles.add(SProblemStyle.create("不定项选择题", true));
//			styles.add(SProblemStyle.create("填空题", false));
//			styles.add(SProblemStyle.create("解答题", false));
////			styles.add(SProblemStyle.create("语言表达", false));
//			File file = new File(outDir, String.format("%s.json", tableName));
//			File fileRaw = new File(outDir, String.format("%s.json.raw", tableName));
//			FsUtils.writeText(file, LOGJson.getStr(SProblemStyle.saveList(styles).toString()));
//			FsUtils.writeText(fileRaw, SProblemStyle.saveList(styles).toString());
//		}
//		{
//			String tableName = AppConfig.getTopicStyleTableName(SPhase.Chu, SSubject.Shu);
//			List<SProblemStyle> styles = new ArrayList<SProblemStyle>();
//			styles.add(SProblemStyle.create("选择题", true));
//			styles.add(SProblemStyle.create("计算题", false));
//			styles.add(SProblemStyle.create("填空题", false));
//			styles.add(SProblemStyle.create("解答题", false));
//			styles.add(SProblemStyle.create("判断题", true));
//			File file = new File(outDir, String.format("%s.json", tableName));
//			File fileRaw = new File(outDir, String.format("%s.json.raw", tableName));
//			FsUtils.writeText(file, LOGJson.getStr(SProblemStyle.saveList(styles).toString()));
//			FsUtils.writeText(fileRaw, SProblemStyle.saveList(styles).toString());
//		}
//		{
//			String tableName = AppConfig.getTopicStyleTableName(SPhase.Gao, SSubject.Li);
//			List<SProblemStyle> styles = new ArrayList<SProblemStyle>();
//			styles.add(SProblemStyle.create("实验题", false));
//			styles.add(SProblemStyle.create("填空题", false));
//			styles.add(SProblemStyle.create("作图题", false));
//			styles.add(SProblemStyle.create("多选题", true));
//			styles.add(SProblemStyle.create("计算题", false));
//			styles.add(SProblemStyle.create("综合题", false));
//			styles.add(SProblemStyle.create("简答题", false));
//			styles.add(SProblemStyle.create("单选题", true));
//			File file = new File(outDir, String.format("%s.json", tableName));
//			File fileRaw = new File(outDir, String.format("%s.json.raw", tableName));
//			FsUtils.writeText(file, LOGJson.getStr(SProblemStyle.saveList(styles).toString()));
//			FsUtils.writeText(fileRaw, SProblemStyle.saveList(styles).toString());
//		}
//		{
//			String tableName = AppConfig.getTopicStyleTableName(SPhase.Chu, SSubject.Li);
//			List<SProblemStyle> styles = new ArrayList<SProblemStyle>();
//			styles.add(SProblemStyle.create("填空题", false));
//			styles.add(SProblemStyle.create("作图题", false));
//			styles.add(SProblemStyle.create("实验探究题", false));
//			styles.add(SProblemStyle.create("计算题", false));
//			styles.add(SProblemStyle.create("多选题", true));
//			styles.add(SProblemStyle.create("综合题", false));
//			styles.add(SProblemStyle.create("简答题", false));
//			styles.add(SProblemStyle.create("单选题", true));
//			styles.add(SProblemStyle.create("判断题", false));
//			File file = new File(outDir, String.format("%s.json", tableName));
//			File fileRaw = new File(outDir, String.format("%s.json.raw", tableName));
//			FsUtils.writeText(file, LOGJson.getStr(SProblemStyle.saveList(styles).toString()));
//			FsUtils.writeText(fileRaw, SProblemStyle.saveList(styles).toString());
//		}
//	}
//
//	public static void searchTopic()
//	{
//		SRequest_SearchTopics request = new SRequest_SearchTopics();
//		request.content = "集合";
//		request.subject = 2;
//		request.start = 0;
//		request.rows = 1;
//		request.knowledges.add(SKnowledge.create("", "数列求和方法", 0, 0, false, null, 0));
//		request.knowledges.add(SKnowledge.create("", "元素与集合的关系", 0, 0, false, null, 0));
//		request.styles.add("解答题");
//		request.diffs.add(5);
//		String data = doPost(API, SHandleId.SearchTopics, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_SearchTopics response = SResponse_SearchTopics.load(data);
//			LOG.v("searchTopic success");
//		}
//		else
//		{
//			LOG.e("searchTopic fail");
//		}
//	}
//
//	public static void requestLog()
//	{
//		SRequest_GetServerState request = new SRequest_GetServerState();
//		String data = doPost("http://192.168.31.153:8080/pair/run", SHandleId.GetServerState, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_GetServerState response = SResponse_GetServerState.load(data);
//			LOG.v("--- success");
//		}
//		else
//		{
//			LOG.e("--- fail");
//		}
//	}
//
//	public static void testProtocol()
//	{
//		for (int i = 0; i < 1; i++)
//		{
//			new Thread(new Runnable()
//			{
//				@Override
//				public void run()
//				{
//					SRequest_GetConfig request = new SRequest_GetConfig();
//					String data = doPost(API, SHandleId.GetConfig, request.saveToStr());
//					if (!TextUtils.isEmpty(data))
//					{
//						SResponse_GetConfig response = SResponse_GetConfig.load(data);
//						LOG.v("--- success");
//					}
//					else
//					{
//						LOG.e("--- fail");
//					}
//				}
//			}).start();
//		}
//
//	}
//
//	public static void testSvn()
//	{
////		String svnUrl = String.format("http://120.24.162.51:81/svn/board");
////		String svnAccount = "any";
////		String svnPsw = "269543";
//
//		String svnUrl = String.format("https://192.168.43.46:8443/svn/lys");
//		String svnAccount = "wangzhiting";
//		String svnPsw = "wangzhiting";
//
//		SVNManager.init(svnUrl, svnAccount, svnPsw);
//
//		try
//		{
////			SVNManager.getRemoteVision(String.format("/lys.tasks/%s/%s", "FARNU19719100497", "366ec743-b64c-4af4-a8cb-ea6d67130346"));
////			SVNManager.getLocalVision(new File("C:/Users/xnktyu/Desktop/366ec743-b64c-4af4-a8cb-ea6d67130346"));
//			SVNManager.update(new File("C:\\Users\\xnktyu\\Desktop\\2f855952-513b-41f0-9040-b2dbbf64b8b2"));
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	public static void writeImageToBytes(File imageFile, File textFile)
//	{
//		BufferedImage image = ImageUtil.readImage(imageFile.toString());
//		StringBuilder sb = new StringBuilder();
//		for (int x = 0; x < image.getWidth(); x++)
//		{
//			for (int y = 0; y < image.getHeight(); y++)
//			{
//				int px = image.getRGB(x, y);
////				sb.append(String.format(" %08x", px));
//				sb.append(String.format("%04d %04d %08x\r\n", x, y, px));
//			}
////			sb.append(String.format("\r\n"));
//		}
//		FsUtils.writeText(textFile, sb.toString());
//	}
//
//	public static final int Type_Photo = 0;
//	public static final int Type_Video = 1;
//	public static final int Type_Topic = 2;
//	public static final int Type_SelectionGroup = 3;
//
//	public static final String VideoLocal = "local:";
//	public static final String VideoNet = "net:";
//
//	public static void checkBoardData(File rootDir)
//	{
//		int taskIndex = 0;
//		File[] userDirs = rootDir.listFiles();
//		for (File userDir : userDirs)
//		{
//			if (userDir.isDirectory() && !userDir.getName().equals(".svn"))
//			{
//				File[] taskDirs = userDir.listFiles();
//				for (File taskDir : taskDirs)
//				{
//					if (taskDir.isDirectory())
//					{
////						LOG.v(taskIndex + " check : " + taskDir);
//						taskIndex++;
//
//						File pagesetFile = new File(String.format("%s/pageset.json", taskDir));
//						if (pagesetFile.exists())
//						{
//							SNotePageSet pageset = SNotePageSet.load(FsUtils.readText(pagesetFile));
//							for (SNotePage page : pageset.pages)
//							{
//								File dir = new File(String.format("%s/%s", taskDir, page.pageDir));
//								File boardFile = new File(String.format("%s/board.json", dir));
//								if (boardFile.exists())
//								{
//									SBoardConfig board = SBoardConfig.load(FsUtils.readText(boardFile));
//									for (SBoardPhoto photo : board.photos)
//									{
//										File photoFile = new File(String.format("%s/%s.png", dir, photo.name));
//										File videoFile = new File(String.format("%s/%s.mp4", dir, photo.name));
//										File parseFile = new File(String.format("%s/%s.parse.png", dir, photo.name));
//
//										if (videoFile.exists())
//										{
//											LOG.e("find : " + videoFile);
//										}
//
//										if (photo.type == Type_Photo)
//										{
//										}
//										else if (photo.type == Type_Video)
//										{
//											if (photo.url.startsWith(VideoNet))
//											{
//												LOG.v("find net video : " + photo.url);
//											}
//											else if (photo.url.startsWith(VideoLocal))
//											{
//												LOG.e("find local video : " + videoFile);
//											}
//										}
//										else if (photo.type == Type_Topic)
//										{
//										}
//										else if (photo.type == Type_SelectionGroup)
//										{
//										}
//									}
//								}
//								else
//								{
//									LOG.e("miss : " + boardFile);
//								}
//							}
//						}
//						else
//						{
//							LOG.e("miss : " + pagesetFile);
//						}
//					}
//				}
//			}
//		}
//	}
//
//	public static void genNetImgSub(File rootDir, String rootUrl, int smallWidth)
//	{
//		List<SNetPicInfo> netPics = new ArrayList<>();
//
//		File smallDir = new File(rootDir, "small");
//		File coverDir = new File(rootDir, "cover");
//
//		FsUtils.delete(smallDir);
//		FsUtils.delete(coverDir);
//
//		FsUtils.createDir(smallDir);
//		FsUtils.createDir(coverDir);
//
//		File[] files = rootDir.listFiles();
//		for (File file : files)
//		{
//			if (file.isFile())
//			{
//				if (file.getName().toLowerCase().endsWith(".jpg") || //
//						file.getName().toLowerCase().endsWith(".jpeg") || //
//						file.getName().toLowerCase().endsWith(".png"))
//				{
//					int pos = file.getName().lastIndexOf('.');
//					String type = file.getName().substring(pos);
//					String name = file.getName().substring(0, pos);
//
//					File smallFile = new File(smallDir, name + ".png");
//
//					BufferedImage image = ImageUtil.readImage(file.toString());
//					BufferedImage smallImage = ImageUtil.scaleImageMax(image, smallWidth);
//					ImageUtil.writeImage(smallImage, smallFile.toString());
//
//					SNetPicInfo netPic = new SNetPicInfo();
//					netPic.isMovie = false;
//					netPic.type = type;
//					netPic.name = name;
//					netPic.smallUrl = String.format("%s/small/%s", rootUrl, smallFile.getName());
//					netPic.smallWidth = smallImage.getWidth();
//					netPic.smallHeight = smallImage.getHeight();
//					netPic.bigUrl = String.format("%s/%s", rootUrl, file.getName());
//					netPic.bigWidth = image.getWidth();
//					netPic.bigHeight = image.getHeight();
//					netPics.add(netPic);
//				}
//				else if (file.getName().toLowerCase().endsWith(".mp4"))
//				{
//					VideoMetaInfo info = MediaUtil.getVideoMetaInfo(file);
//					if (info != null)
//					{
//						int pos = file.getName().lastIndexOf('.');
//						String type = file.getName().substring(pos);
//						String name = file.getName().substring(0, pos);
//
//						File coverFile = new File(coverDir, name + ".jpg");
//						File smallFile = new File(smallDir, name + ".jpg");
//
//						MediaUtil.cutVideoFrame(file, coverFile, 0);
////						MediaUtil.cutVideoFrame(file, coverFile, Integer.MAX_VALUE);
//
//						BufferedImage image = ImageUtil.readImage(coverFile.toString());
//						BufferedImage smallImage = ImageUtil.scaleImageMax(image, smallWidth);
//						ImageUtil.writeImage(smallImage, smallFile.toString());
//
//						SNetPicInfo netPic = new SNetPicInfo();
//						netPic.isMovie = true;
//						netPic.type = type;
//						netPic.name = name;
//						netPic.smallUrl = String.format("%s/small/%s", rootUrl, smallFile.getName());
//						netPic.smallWidth = smallImage.getWidth();
//						netPic.smallHeight = smallImage.getHeight();
//						netPic.bigUrl = String.format("%s/cover/%s", rootUrl, coverFile.getName());
//						netPic.bigWidth = image.getWidth();
//						netPic.bigHeight = image.getHeight();
//						netPic.videoUrl = String.format("%s/%s", rootUrl, file.getName());
//						netPic.duration = info.getDuration();
//						netPics.add(netPic);
//					}
//					else
//					{
//						LOG.e("video parse fail : " + file);
//					}
//				}
//			}
//		}
//
//		File jsonFile = new File(rootDir, "list.json");
//		File jsonFileRaw = new File(rootDir, "list.json.raw");
//
//		FsUtils.writeText(jsonFile, LOGJson.getStr(SNetPicInfo.saveList(netPics).toString()));
//		FsUtils.writeText(jsonFileRaw, SNetPicInfo.saveList(netPics).toString());
//	}
//
//	public static void genNetImg(File rootDir, String rootUrl, int smallWidth)
//	{
//		List<String> groups = new ArrayList<String>();
//
//		File[] files = rootDir.listFiles();
//		for (File file : files)
//		{
//			if (file.isDirectory())
//			{
//				genNetImgSub(file, rootUrl + "/" + file.getName(), smallWidth);
//				groups.add(file.getName());
//			}
//		}
//
//		File jsonFile = new File(rootDir, "group.json");
//		File jsonFileRaw = new File(rootDir, "group.json.raw");
//
//		FsUtils.writeText(jsonFile, LOGJson.getStr(AppDataTool.saveStringList(groups).toString()));
//		FsUtils.writeText(jsonFileRaw, AppDataTool.saveStringList(groups).toString());
//	}
//
//	public static void AddUser(String userId, String name, String psw, Integer userType, Integer sex)
//	{
//		SRequest_AddUser request = new SRequest_AddUser();
//		request.userId = userId;
//		request.name = name;
//		request.psw = psw;
//		request.head = "";
//		request.userType = userType;
//		request.sex = sex;
//		String data = doPost(API, SHandleId.AddUser, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			LOG.v("添加成功：" + userId);
//		else
//			LOG.e("添加失败：" + userId + "！！！");
//	}
//
//	public static void aaaaaaa()
//	{
//		ArrayList<Integer> aa = new ArrayList<Integer>();
//
////		aa.add((int) (Math.random() * 9 + 1));
////		aa.add((int) (Math.random() * 9 + 1));
//
//		aa.add(4);
//		aa.add(8);
//
//		LOG.v("" + aa.get(aa.size() - 2));
//		LOG.v("" + aa.get(aa.size() - 1));
//
//		for (int i = 0; i < 2000; i++)
//		{
//			int a1 = aa.get(aa.size() - 2);
//			int a2 = aa.get(aa.size() - 1);
//
//			int a3 = Math.abs(a1 - a2);
//
//			LOG.v("\t\t" + i + "\t\t" + a3);
//			aa.add(a3);
//
////			int a4 = Math.abs(a3 - a2);
////			LOG.v(String.format("%d\t%10d %10d %10d %10d", i, a1, a2, a3, a4));
//		}
//	}
//
//	public static void checkUrls(File taskDir)
//	{
////		LOG.v(taskIndex + " check : " + taskDir);
//		File pagesetFile = new File(String.format("%s/pageset.json", taskDir));
//		if (pagesetFile.exists())
//		{
//			SNotePageSet pageset = SNotePageSet.load(FsUtils.readText(pagesetFile));
//			for (SNotePage page : pageset.pages)
//			{
//				File dir = new File(String.format("%s/%s", taskDir, page.pageDir));
//
//				File boardFile = new File(String.format("%s/board.json", dir));
//				if (boardFile.exists())
//				{
//					SBoardConfig board = SBoardConfig.load(FsUtils.readText(boardFile));
//					for (SBoardPhoto photo : board.photos)
//					{
//						if (photo.type == Type_Video)
//						{
//							if (photo.url.startsWith(VideoNet))
//							{
//								String url = photo.url.substring(VideoNet.length());
//								System.out.println("kkk:" + url);
//							}
//						}
//					}
//				}
//
//				File videoUrlFile = new File(String.format("%s/%s.txt", dir.getAbsolutePath(), "big_video"));
//				if (videoUrlFile.exists())
//				{
//					String url = FsUtils.readText(videoUrlFile);
//					System.out.println(url);
//
//					String path = "D:/360极速浏览器下载/" + taskDir.getName() + url.substring(url.lastIndexOf("/"));
//
//					File file = new File(path);
//					System.out.println(file);
//
//					FsUtils.createDir(file.getParentFile());
//
//					HttpUtils.doDownload(url, file);
//				}
//
//			}
//		}
//		else
//		{
//			LOG.e("miss : " + pagesetFile);
//		}
//	}
//
//	public static void uploadoss(StringBuilder sb, File file)
//	{
//		OssUtils.doUploadMd5FileWithProgress(OssUtils.ZjykFile, file, OssUtils.DirVideo, new OssUtils.OnProgressListener()
//		{
//			@Override
//			public void onProgress(long written, long total, int percent)
//			{
//			}
//
//			@Override
//			public void onSuccess(String url)
//			{
//				System.out.println(file.getName() + "--" + url);
//				sb.append(file.getName() + "--" + url + "\r\n");
//			}
//
//			@Override
//			public void onFail()
//			{
//			}
//		});
//	}
//
//	public static void uploadoss(File dir)
//	{
//		StringBuilder sb = new StringBuilder();
//
//		File[] files = dir.listFiles();
//		for (File file : files)
//		{
//			if (file.isFile() && file.getName().endsWith(".mp4"))
//			{
//				LOG.v(file.toString());
//				uploadoss(sb, file);
//			}
//		}
//
//		FsUtils.writeText(new File(dir, "path.txt"), sb.toString());
//	}
//
//	public static void changeUrls(File rootDir, File pathTxt)
//	{
//		HashMap<String, String> map = new HashMap<String, String>();
//
//		String text = FsUtils.readText(pathTxt);
//		String[] lines = text.split("\r\n");
//		for (String line : lines)
//		{
//			String[] parts = line.split("--");
////			LOG.v(parts[0] + "--" + parts[1]);
//			map.put(parts[0], parts[1]);
//		}
//
//		int taskIndex = 0;
//		File[] userDirs = rootDir.listFiles();
//		for (File userDir : userDirs)
//		{
//			if (userDir.isDirectory() && !userDir.getName().equals(".svn"))
//			{
//				LOG.v(taskIndex + " check : " + userDir);
//				taskIndex++;
//
//				File[] taskDirs = userDir.listFiles();
//				for (File taskDir : taskDirs)
//				{
//					if (taskDir.isDirectory())
//					{
//
//						File pagesetFile = new File(String.format("%s/pageset.json", taskDir));
//						if (pagesetFile.exists())
//						{
//							SNotePageSet pageset = SNotePageSet.load(FsUtils.readText(pagesetFile));
//							for (SNotePage page : pageset.pages)
//							{
//								File dir = new File(String.format("%s/%s", taskDir, page.pageDir));
//
////								File boardFile = new File(String.format("%s/board.json", dir));
////								if (boardFile.exists())
////								{
////									SBoardConfig board = SBoardConfig.load(FsUtils.readText(boardFile));
////									for (SBoardPhoto photo : board.photos)
////									{
////										if (photo.type == Type_Video)
////										{
////											if (photo.url.startsWith(VideoNet))
////											{
////												String url = photo.url.substring(VideoNet.length());
//////												System.out.println("kkk:" + url);
////											}
////										}
////									}
////								}
//
//								File videoUrlFile = new File(String.format("%s/%s.txt", dir.getAbsolutePath(), "big_video"));
//								if (videoUrlFile.exists())
//								{
//									String url = FsUtils.readText(videoUrlFile);
////									System.out.println(url);
//
//									String path = "D:/360极速浏览器下载/" + taskDir.getName() + url.substring(url.lastIndexOf("/"));
//
//									File file = new File(path);
////									System.out.println(file);
//
//									if (map.containsKey(file.getName()))
//									{
//										System.out.println(file);
//										FsUtils.writeText(videoUrlFile, map.get(file.getName()));
//									}
//
////									FsUtils.createDir(file.getParentFile());
//
////									HttpUtils.doDownload(url, file);
//								}
//
//							}
//						}
//						else
//						{
//							LOG.e("miss : " + pagesetFile);
//						}
//					}
//				}
//			}
//		}
//	}
//
//	public static void playMidi(File file)
//	{
//		try
//		{
//			AudioClip aau = Applet.newAudioClip(file.toURL());
//			aau.play();
//		}
//		catch (MalformedURLException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	public static void playMp3(File file)
//	{
//		String commandPath = "C:/wangzhiting/work/code/client/tools/command.jar";
//		CommandHelper.executeCommand(String.format("java -jar \"%s\" playMusic \"%s\"", commandPath, file));
//	}
//
//	public static void tickServer()
//	{
//		new Thread(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				while (true)
//				{
//					SRequest_GetConfig request = new SRequest_GetConfig();
//					String data = doPost(API, SHandleId.GetConfig, request.saveToStr());
//					if (!TextUtils.isEmpty(data))
//					{
//						SResponse_GetConfig response = SResponse_GetConfig.load(data);
//						LOG.v("--- success");
//					}
//					else
//					{
//						LOG.e("--- fail");
////						playMidi(new File("C:/wangzhiting/work/code/client/java/pair/baga01.mid"));
//						playMp3(new File("C:/wangzhiting/work/code/client/java/pair/ding.mp3"));
//					}
//					try
//					{
//						Thread.sleep(10 * 1000);
//					}
//					catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
//	}
//
//	public static void syncOss(File rootDir)
//	{
//		new Thread(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				while (true)
//				{
//					List<String> allFiles = OssUtils.listAllFiles(OssUtils.ZjykFile, "video/");
//					for (String path : allFiles)
//					{
//						String url = OssUtils.getHost(OssUtils.ZjykFile) + path;
//						File file = new File(rootDir, path);
//						if (!file.exists())
//						{
//							LOG.v("sync : " + file);
//							FsUtils.createDir(file.getParentFile());
//							HttpUtils.doDownload(url, file);
//						}
//					}
//					try
//					{
//						Thread.sleep(10 * 60 * 1000);
//					}
//					catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
//	}
//
//	public static void DeleteTask(String taskId)
//	{
//		SRequest_DeleteTask request = new SRequest_DeleteTask();
//		request.taskId = taskId;
//		String data = doPost(API, SHandleId.DeleteTask, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			LOG.v("删除任务成功：" + taskId);
//		else
//			LOG.e("删除任务失败：" + taskId + "！！！");
//	}

	public static SPTask FindTask(String group, String name)
	{
		SRequest_FindTask request = new SRequest_FindTask();
		request.group = group;
		request.name = name;
		String data = doPost(API, SHandleId.FindTask, request.saveToStr());
		if (!TextUtils.isEmpty(data))
		{
			SResponse_FindTask response = SResponse_FindTask.load(data);
			return response.task;
		}
		else
			return null;
	}

//	public static SResponse_GetTaskList GetTaskList(String userId)
//	{
//		SRequest_GetTaskList request = new SRequest_GetTaskList();
//		request.userId = userId;
//		String data = doPost(API, SHandleId.GetTaskList, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			return SResponse_GetTaskList.load(data);
//		else
//			return null;
//	}
//
//	public static void SendTask()
//	{
//		if (false)
//		{
//			SRequest_SendTask request = new SRequest_SendTask();
//			request.userIds.add("xxx");
//			request.taskIds.add("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//			request.sendUserId = "uuuuuuu";
//			request.text = "学完课程后，请完成配套的作业。请务必在明天（2.1）中午12点之前提交。选择题可直接在选项上点击作答，填空和解答题可以把作答过程拍照上传。";
//			String data = doPost(API, SHandleId.SendTask, request.saveToStr());
//			if (!TextUtils.isEmpty(data))
//				LOG.v("发送任务成功：");
//			else
//				LOG.e("发送任务失败：" + "！！！");
//		}
//	}
//
//	public static SResponse_GetFriendList GetFriendList(String userId)
//	{
//		SRequest_GetFriendList request = new SRequest_GetFriendList();
//		request.userId = userId;
//		String data = doPost(API, SHandleId.GetFriendList, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			return SResponse_GetFriendList.load(data);
//		else
//			return null;
//	}
//
//	public static void AddFriend(String userId, String friendId)
//	{
//		SRequest_AddFriend request = new SRequest_AddFriend();
//		request.userId = userId;
//		request.friendId = friendId;
//		String data = doPost(API, SHandleId.AddFriend, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			LOG.v("添加好友成功：" + userId + " and " + friendId);
//		else
//			LOG.e("添加好友失败：" + userId + " and " + friendId + "！！！");
//	}
//
//	public static void DeleteFriend(String userId, String friendId)
//	{
//		SRequest_DeleteFriend request = new SRequest_DeleteFriend();
//		request.userId = userId;
//		request.friendId = friendId;
//		String data = doPost(API, SHandleId.DeleteFriend, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			LOG.v("删除好友成功：" + userId + " and " + friendId);
//		else
//			LOG.e("删除好友失败：" + userId + " and " + friendId + "！！！");
//	}
//
//	public static void ModifyFriendGroup(String userId, String friendId, String group)
//	{
//		SRequest_ModifyFriendGroup request = new SRequest_ModifyFriendGroup();
//		request.userId = userId;
//		request.friendId = friendId;
//		request.group = group;
//		String data = doPost(API, SHandleId.ModifyFriendGroup, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			LOG.v("修改好友组成功：" + userId + " and " + friendId);
//		else
//			LOG.e("修改好友组失败：" + userId + " and " + friendId + "！！！");
//	}
//
//	public static void TopicRecordDelete(String userId, String topicId)
//	{
//		SRequest_TopicRecordDelete request = new SRequest_TopicRecordDelete();
//		request.userId = userId;
//		request.topicId = topicId;
//		String data = doPost(API, SHandleId.TopicRecordDelete, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			LOG.v("删除刷题记录成功：" + userId + " and " + topicId);
//		else
//			LOG.e("删除刷题记录失败：" + userId + " and " + topicId + "！！！");
//	}
//
//	public static SResponse_TopicRecordGetList TopicRecordGetList(String userId)
//	{
//		SRequest_TopicRecordGetList request = new SRequest_TopicRecordGetList();
//		request.userId = userId;
//		String data = doPost(API, SHandleId.TopicRecordGetList, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			return SResponse_TopicRecordGetList.load(data);
//		else
//			return null;
//	}
//
//	public static void addFriends() throws Exception
//	{
//		List<String> lines = new ArrayList<>();
//
//		lines.add("李平" + "-->" + "王宇翔");
//		lines.add("李平" + "-->" + "屈周洲");
//		lines.add("李平" + "-->" + "黄欣格");
//		lines.add("李平" + "-->" + "柏舟航");
//		lines.add("李平" + "-->" + "彭心语");
//		lines.add("李平" + "-->" + "曾少萱");
//
//		lines.add("陈大军" + "-->" + "刘润楠");
//		lines.add("陈大军" + "-->" + "张含玥");
//		lines.add("陈大军" + "-->" + "刘川源");
//		lines.add("陈大军" + "-->" + "屈正睿");
//		lines.add("陈大军" + "-->" + "罗一博");
//
//		lines.add("张芒" + "-->" + "熊宇翔");
//		lines.add("张芒" + "-->" + "陶正悦");
//		lines.add("张芒" + "-->" + "樊智欣");
//		lines.add("张芒" + "-->" + "魏雨婷");
//		lines.add("张芒" + "-->" + "吴亦真");
//		lines.add("张芒" + "-->" + "李健坤");
//
//		lines.add("高兴兵" + "-->" + "芮蕊");
//		lines.add("高兴兵" + "-->" + "石行敏");
//		lines.add("高兴兵" + "-->" + "李智信");
//
//		lines.add("杨亚丽" + "-->" + "熊羽欧");
//
//		lines.add("徐永铭" + "-->" + "王宇翔");
//		lines.add("徐永铭" + "-->" + "屈周洲");
//		lines.add("徐永铭" + "-->" + "胡凤颖");
//		lines.add("徐永铭" + "-->" + "熊宇翔");
//		lines.add("徐永铭" + "-->" + "吴泽文");
//		lines.add("徐永铭" + "-->" + "魏雨婷");
//		lines.add("徐永铭" + "-->" + "孙婧怡");
//
//		lines.add("朱海鲲" + "-->" + "邓骏杰");
//		lines.add("朱海鲲" + "-->" + "樊智欣");
//		lines.add("朱海鲲" + "-->" + "石行敏");
//		lines.add("朱海鲲" + "-->" + "屈正睿");
//		lines.add("朱海鲲" + "-->" + "孙孚俊");
//
//		lines.add("谢老师" + "-->" + "王冠阳");
//		lines.add("谢老师" + "-->" + "陈仁杰");
//		lines.add("谢老师" + "-->" + "文艳青");
//
//		lines.add("秦单凤" + "-->" + "王冠阳");
//		lines.add("秦单凤" + "-->" + "屈周洲");
//		lines.add("秦单凤" + "-->" + "魏雨婷");
//		lines.add("秦单凤" + "-->" + "孙孚俊");
//
//		lines.add("赵大勇" + "-->" + "熊羽欧");
//		lines.add("赵大勇" + "-->" + "樊智欣");
//		lines.add("赵大勇" + "-->" + "孙孚俊");
//
//		LOG.v("lines:" + lines.size());
//
//		for (String line : lines)
//		{
//			String[] strs = line.split("-->");
//			String name1 = strs[0];
//			String name2 = strs[1];
////			LOG.v(name1 + "\t" + name2);
//			SUser user1 = DBClient.selectUser(name1);
//			SUser user2 = DBClient.selectUser(name2);
//			if (user1 != null && user2 != null)
//			{
//				ProcessFriend.addFriendImpl(user1.id, user2.id);
//				ProcessFriend.addFriendImpl(user2.id, user1.id);
//			}
//		}
//	}
//
//	public static void showFriends(String userId) throws Exception
//	{
//		LOG.v("showFriends : " + userId);
//		SResponse_GetFriendList list = GetFriendList(userId);
//		for (SUser friend : list.friends)
//		{
//			if (!friend.id.equals("mst1"))
//				LOG.v(String.format("\t%s@%s", friend.id, friend.name));
//		}
//	}
//
//	public static final String EventAction_InTask = "InTask";
//	public static final String EventAction_OutTask = "OutTask";
//	public static final String EventAction_CommitJob = "CommitJob";
//
//	public static boolean isCommitJob(String userId, String taskId)
//	{
//		SRequest_GetEventList request = new SRequest_GetEventList();
//		request.userId = userId;
//		request.actions.add(EventAction_CommitJob);
//		request.targets.add(taskId);
//		String data = doPost(API, SHandleId.GetEventList, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_GetEventList response = SResponse_GetEventList.load(data);
//			return response.events.size() > 0;
//		}
//		return false;
//	}
//
//	public static boolean isOpenTask(String userId, String taskId)
//	{
//		SRequest_GetEventList request = new SRequest_GetEventList();
//		request.userId = userId;
//		request.actions.add(EventAction_InTask);
//		request.targets.add(taskId);
//		String data = doPost(API, SHandleId.GetEventList, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_GetEventList response = SResponse_GetEventList.load(data);
//			return response.events.size() > 0;
//		}
//		return false;
//	}
//
//	public static long getOpenTime(String userId, String taskId)
//	{
//		SRequest_GetEventList request = new SRequest_GetEventList();
//		request.userId = userId;
//		request.actions.add(EventAction_InTask);
//		request.actions.add(EventAction_OutTask);
//		request.targets.add(taskId);
//		String data = doPost(API, SHandleId.GetEventList, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_GetEventList response = SResponse_GetEventList.load(data);
//			long allTime = 0;
//			long lastInTime = -1;
//			for (int i = 0; i < response.events.size(); i++)
//			{
//				SEvent event = response.events.get(i);
//				if (event.action.equals(EventAction_InTask))
//				{
//					lastInTime = event.time;
//				}
//				else if (event.action.equals(EventAction_OutTask))
//				{
//					if (lastInTime != -1)
//					{
//						long dtTime = event.time - lastInTime;
//						allTime += dtTime;
//					}
//					lastInTime = -1;
//				}
//			}
//			return allTime;
//		}
//		return 0;
//	}
//
//	public static void processFriendGroup(String userId)
//	{
//		LOG.v("processFriendGroup : " + userId);
//		SResponse_GetFriendList list = GetFriendList(userId);
//		int index = 0;
//
//		int classCount = 0;
//		int openCount = 0;
//
//		long openAllTime = 0;
//
//		int jobCount = 0;
//		int commitCount = 0;
//
//		for (SUser friend : list.friends)
//		{
//			if (!TextUtils.isEmpty(friend.group))
//			{
////				if (friend.userType == 4 && friend.id.startsWith("U") && friend.id.length() == 9 && friend.grade == 10)
//				if (friend.group.equals("致远高一B") || friend.group.equals("致远高一C") || friend.group.equals("致远高一D"))
//				{
//					index++;
//					LOG.v(String.format("\t%s\t%s\t%s\t%s\t%s\t%s", index, friend.userType, friend.id, friend.grade, friend.name, friend.group));
////					ModifyFriendGroup(userId, friend.id, "致远高一D");
//
////					SResponse_GetTaskList kkkk = GetTaskList(friend.id);
////
////					if (kkkk.tasks.size() != 2)
////					{
////						for (SPTask task : kkkk.tasks)
////						{
////							LOG.v(String.format("\t\t%s\t%s", task.name, task.id));
////						}
////					}
//
//					// ------------------- 高一 -------------------
//
//					if (false)
//					{
//						SRequest_SendTask request = new SRequest_SendTask();
//						request.userIds.add(friend.id);
//						request.taskIds.add("80d26e1a-0727-41d8-92dc-e74870e116d0");
//						request.sendUserId = userId;
//						request.text = "本次课程我们学习函数的恒成立5种方法，请认真听课，做好笔记";
//						String data = doPost(API, SHandleId.SendTask, request.saveToStr());
//						if (!TextUtils.isEmpty(data))
//							LOG.v("发送任务成功：");
//						else
//							LOG.e("发送任务失败：" + "！！！");
//					}
//
//					if (false)
//					{
//						SRequest_SendTask request = new SRequest_SendTask();
//						request.userIds.add(friend.id);
//						request.taskIds.add("ba54c314-2895-4b60-aac2-d45be3ebcd04");
//						request.sendUserId = userId;
//						request.text = "今天晚上6点前请完成恒成立专题配套作业。直接点击选项作答即可，全部完成后点右上角的提交作业。千万不要写一题提交一题，只有一次提交的机会，提交后即可显示答案和解析。";
//						String data = doPost(API, SHandleId.SendTask, request.saveToStr());
//						if (!TextUtils.isEmpty(data))
//							LOG.v("发送任务成功：");
//						else
//							LOG.e("发送任务失败：" + "！！！");
//					}
//
//					// ------------------- 高二 -------------------
//
//					if (false)
//					{
//						SRequest_SendTask request = new SRequest_SendTask();
//						request.userIds.add(friend.id);
//						request.taskIds.add("bad2557c-d8c5-44f8-b342-95dd7b141675");
//						request.sendUserId = userId;
//						request.text = "今日课程：《导数的运算》，请认真做好笔记。";
//						String data = doPost(API, SHandleId.SendTask, request.saveToStr());
//						if (!TextUtils.isEmpty(data))
//							LOG.v("发送任务成功：");
//						else
//							LOG.e("发送任务失败：" + "！！！");
//					}
//
//					if (false)
//					{
//						SRequest_SendTask request = new SRequest_SendTask();
//						request.userIds.add(friend.id);
//						request.taskIds.add("96c29969-f77c-42da-81ce-b7ccda910676");
//						request.sendUserId = userId;
//						request.text = "今日配套作业，直接点击选项作答，全部完成后点击右上角提交作业提交。";
//						String data = doPost(API, SHandleId.SendTask, request.saveToStr());
//						if (!TextUtils.isEmpty(data))
//							LOG.v("发送任务成功：");
//						else
//							LOG.e("发送任务失败：" + "！！！");
//					}
//
//					// ------------------- 统计 -------------------
//
//					if (false)
//					{
//						SResponse_GetTaskList kkkk = GetTaskList(friend.id);
//						for (SPTask task : kkkk.tasks)
//						{
//							if (task.name.equals("导数的运算"))
//							{
//								LOG.v(String.format("\t\t%s\t%s", task.name, task.id));
//								classCount++;
//								if (isOpenTask(friend.id, task.id))
//									openCount++;
//
//								openAllTime += getOpenTime(friend.id, task.id);
//							}
//							if (task.name.equals("作业-导数的运算"))
//							{
//								LOG.v(String.format("\t\t%s\t%s", task.name, task.id));
//								jobCount++;
//								if (isCommitJob(friend.id, task.id))
//									commitCount++;
//							}
//						}
//					}
//
//				}
//
//			}
//		}
//
//		if (false)
//		{
//			LOG.v(String.format("课程数：%s, 课程打开数：%s, 课程打开率：%s%%", classCount, openCount, openCount * 100 / classCount));
//			LOG.v(String.format("课程打开总时间：%s, 课程打平均时间：%s", CommonUtils.formatTime(openAllTime), CommonUtils.formatTime(openAllTime / openCount)));
//			LOG.v(String.format("作业总数：%s, 作业提交数：%s, 作业提交率：%s%%", jobCount, commitCount, commitCount * 100 / jobCount));
//		}
//	}
//
//	private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//	public static void GetServerState()
//	{
//		System.out.println("------------------------- GetServerState ----------------------------");
//		SRequest_GetServerState request = new SRequest_GetServerState();
//		String data = doPost(RUN, SHandleId.GetServerState, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_GetServerState result = SResponse_GetServerState.load(data);
//
//			long readyTimeAll = 0;
//			long waitTimeAll = 0;
//			long processTimeAll = 0;
//			long useTimeAll = 0;
//
//			int index = 0;
//			for (int i = 0; i < result.serverState.requestRecords.size(); i++)
//			{
//				SRequestRecord requestRecord = result.serverState.requestRecords.get(i);
//
//				long readyTime = requestRecord.readyTime - requestRecord.entryTime;
//				long waitTime = requestRecord.startProcessTime - requestRecord.readyTime;
//				long processTime = requestRecord.overProcessTime - requestRecord.startProcessTime;
//				long useTime = requestRecord.overProcessTime - requestRecord.entryTime;
//
//				readyTimeAll += readyTime;
//				waitTimeAll += waitTime;
//				processTimeAll += processTime;
//				useTimeAll += useTime;
//
//				if (processTime > 10 * 1000)
//				{
//					index++;
//
//					StringBuilder sb = new StringBuilder();
//					sb.append(String.format("\t%s", index));
//					sb.append(String.format("\t%s", i + 1));
//					sb.append(String.format("\t%s", formatDate.format(new Date(requestRecord.entryTime))));
//					sb.append(String.format("\t%20s", requestRecord.handleName));
//					sb.append(String.format("\t准备时长：%s", CommonUtils.formatTime(readyTime)));
//					sb.append(String.format("\t等待时长：%s", CommonUtils.formatTime(waitTime)));
//					sb.append(String.format("\t处理时长：%s", CommonUtils.formatTime(processTime)));
//					sb.append(String.format("\t使用时长：%s", CommonUtils.formatTime(useTime)));
//					System.out.println(sb.toString());
//				}
//			}
//
//			StringBuilder sb = new StringBuilder();
//			sb.append(String.format("\t%s", ""));
//			sb.append(String.format("\t%s", ""));
//			sb.append(String.format("\t%s", ""));
//			sb.append(String.format("\t%20s", "总时长"));
//			sb.append(String.format("\t准备时长：%s", CommonUtils.formatTime(readyTimeAll)));
//			sb.append(String.format("\t等待时长：%s", CommonUtils.formatTime(waitTimeAll)));
//			sb.append(String.format("\t处理时长：%s", CommonUtils.formatTime(processTimeAll)));
//			sb.append(String.format("\t使用时长：%s", CommonUtils.formatTime(useTimeAll)));
//			System.out.println(sb.toString());
//
//			System.out.println(String.format("记录数：%s", result.serverState.requestRecords.size()));
//			System.out.println(String.format("启动时间：%s", formatDate.format(new Date(result.serverState.startTime))));
//			System.out.println(String.format("启动时长：%s", CommonUtils.formatTime(System.currentTimeMillis() - result.serverState.startTime)));
//		}
//	}
//
//	public static void GetTimeRecord()
//	{
//		System.out.println("------------------------- GetTimeRecord ----------------------------");
//		SRequest_GetTimeRecord request = new SRequest_GetTimeRecord();
//		String data = doPost(RUN, SHandleId.GetTimeRecord, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_GetTimeRecord result = SResponse_GetTimeRecord.load(data);
//
//			long waitTimeAll = 0;
//			long processTimeAll = 0;
//			long useTimeAll = 0;
//
//			int index = 0;
//			for (int i = 0; i < result.timeRecords.size(); i++)
//			{
//				STimeRecord timeRecord = result.timeRecords.get(i);
//
//				long waitTime = timeRecord.startProcessTime - timeRecord.startTime;
//				long processTime = timeRecord.overProcessTime - timeRecord.startProcessTime;
//				long useTime = timeRecord.overProcessTime - timeRecord.startTime;
//
//				waitTimeAll += waitTime;
//				processTimeAll += processTime;
//				useTimeAll += useTime;
//
//				if (useTime > 1000)
//				{
//					index++;
//
//					StringBuilder sb = new StringBuilder();
//					sb.append(String.format("\t%s", index));
//					sb.append(String.format("\t%s", i + 1));
//					sb.append(String.format("\t%s", formatDate.format(new Date(timeRecord.startTime))));
//					sb.append(String.format("\t等待时长：%s", CommonUtils.formatTime(waitTime)));
//					sb.append(String.format("\t处理时长：%s", CommonUtils.formatTime(processTime)));
//					sb.append(String.format("\t使用时长：%s", CommonUtils.formatTime(useTime)));
//					sb.append(String.format("\t%s", timeRecord.des));
//					System.out.println(sb.toString());
//				}
//			}
//
//			StringBuilder sb = new StringBuilder();
//			sb.append(String.format("\t%s", ""));
//			sb.append(String.format("\t%s", ""));
//			sb.append(String.format("\t%s", "总时长"));
//			sb.append(String.format("\t等待时长：%s", CommonUtils.formatTime(waitTimeAll)));
//			sb.append(String.format("\t处理时长：%s", CommonUtils.formatTime(processTimeAll)));
//			sb.append(String.format("\t使用时长：%s", CommonUtils.formatTime(useTimeAll)));
//			System.out.println(sb.toString());
//
//			System.out.println(String.format("记录数：%s", result.timeRecords.size()));
//		}
//	}
//
//	public static void catchBook(String name, String cover)
//	{
//		File allDir = new File("D:/catch_book");
//		File jsonFile = new File(allDir, name + ".json.raw");
//		File bookDir = new File(allDir, name);
//		if (jsonFile.exists())
//		{
//			FsUtils.createDir(bookDir);
//			LOG.v("process : " + name);
//			suxuewang.download(cover, new File(bookDir, "cover.jpg"));
//			JSONArray arr = JsonHelper.getJSONArray(FsUtils.readText(jsonFile));
//			for (int i = 0; i < arr.size(); i++)
//			{
//				JSONObject obj = arr.getJSONObject(i);
//				String page = obj.getString("page");
//				String url = obj.getString("url");
//				suxuewang.download(url, new File(bookDir, page + ".jpg"));
//			}
//		}
//	}
//
//	public static void getBookTask_AddPhoto(String name, SBoardConfig board, File bookDir, String imgName, int conWidth, int conHeight, int xOffset, Graphics g)
//	{
//		SBoardPhoto photo = new SBoardPhoto();
//		photo.type = 0;
//		photo.name = imgName;
//		photo.rotation = 0;
//		photo.cover = String.format("http://file.k12-eco.com/课本/%s/%s.jpg", name, imgName);
//		photo.duration = 0L;
//
//		BufferedImage image = ImageUtil.readImage(bookDir + "/" + imgName + ".jpg");
//
//		float scaleX = 1.0f * conWidth / image.getWidth();
//		float scaleY = 1.0f * conHeight / image.getHeight();
//
//		float scale = Math.min(scaleX, scaleY);
//
//		photo.width = (int) (image.getWidth() * scale) + 28;
//		photo.height = (int) (image.getHeight() * scale) + 28;
//
//		photo.x = (conWidth - photo.width) / 2 + xOffset;
//		photo.y = (conHeight - photo.height) / 2;
//
////		ImageUtil.drawImage(g, image, SPTCard_Rect.create(photo.x + 14, photo.y + 14, photo.x + photo.width - 14, photo.y + photo.height - 14), SPTCard_Rect.create(0, 0, image.getWidth(), image.getHeight()));
//
//		board.photos.add(photo);
//	}
//
//	public static void getBookTask(String name, String taskId)
//	{
//		File allDir = new File("D:/catch_book");
//		File jsonFile = new File(allDir, name + ".json.raw");
//		File bookDir = new File(allDir, name);
//		File taskDir = new File("D:/wangzhiting/work/lys.tasks/root/" + taskId);
//		if (jsonFile.exists() && bookDir.exists())
//		{
//			FsUtils.createDir(taskDir);
//			LOG.v("gen book task : " + name);
//
//			File filePageset = new File(String.format("%s/pageset.json", taskDir));
//			SNotePageSet pageset = new SNotePageSet();
//
//			{
//				String pageName = "cover";
//
////				BufferedImage image = ImageUtil.newImage(1920, 1200);
////				Graphics g = image.getGraphics();
////				ImageUtil.fillRect(g, Color.WHITE, SPTCard_Rect.create(0, 0, image.getWidth(), image.getHeight()));
//
//				SNotePage pageData = new SNotePage();
//				pageData.pageDir = pageName;
//				pageset.pages.add(pageData);
//
//				File dirPage = new File(String.format("%s/%s", taskDir, pageName));
//				FsUtils.createDir(dirPage);
//
//				SBoardConfig board = new SBoardConfig();
//				board.bg = 0;
//				board.height = 1200;
//
//				getBookTask_AddPhoto(name, board, bookDir, "cover", 1920, 1200, 0, null);
//
//				File fileBoardJson = new File(String.format("%s/board.json", dirPage));
//				FsUtils.writeText(fileBoardJson, LOGJson.getStr(board.saveToStr()));
//
////				ImageUtil.writeImage(ImageUtil.scaleImage(image, 600), String.format("%s/small.jpg", dirPage));
//			}
//
//			JSONArray arr = JsonHelper.getJSONArray(FsUtils.readText(jsonFile));
//			for (int i = 0; i < arr.size(); i += 2)
//			{
//				String pageName = "page" + (i + 1);
//
////				BufferedImage image = ImageUtil.newImage(1920, 1200);
////				Graphics g = image.getGraphics();
////				ImageUtil.fillRect(g, Color.WHITE, SPTCard_Rect.create(0, 0, image.getWidth(), image.getHeight()));
//
//				SNotePage pageData = new SNotePage();
//				pageData.pageDir = pageName;
//				pageset.pages.add(pageData);
//
//				File dirPage = new File(String.format("%s/%s", taskDir, pageName));
//				FsUtils.createDir(dirPage);
//
//				SBoardConfig board = new SBoardConfig();
//				board.bg = 0;
//				board.height = 1200;
//
//				if (i < arr.size())
//				{
//					JSONObject obj = arr.getJSONObject(i);
//					String page = obj.getString("page");
//					String url = obj.getString("url");
//
//					getBookTask_AddPhoto(name, board, bookDir, page, 1920 / 2, 1200, 0, null);
//				}
//
//				if (i + 1 < arr.size())
//				{
//					JSONObject obj = arr.getJSONObject(i + 1);
//					String page = obj.getString("page");
//					String url = obj.getString("url");
//
//					getBookTask_AddPhoto(name, board, bookDir, page, 1920 / 2, 1200, 1920 / 2, null);
//				}
//
//				File fileBoardJson = new File(String.format("%s/board.json", dirPage));
//				FsUtils.writeText(fileBoardJson, LOGJson.getStr(board.saveToStr()));
//
////				ImageUtil.writeImage(ImageUtil.scaleImage(image, 600), String.format("%s/small.jpg", dirPage));
//			}
//
//			FsUtils.writeText(filePageset, LOGJson.getStr(pageset.saveToStr()));
//		}
//	}
//
//	public static void getBookTask(String name)
//	{
//		SRequest_CreateTask request = new SRequest_CreateTask();
//		request.userId = "root";
//		request.name = name;
//		request.group = "高中课本";
//		String data = doPost(API, SHandleId.CreateTask, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_CreateTask response = SResponse_CreateTask.load(data);
//			getBookTask(name, response.task.id);
//		}
//	}
//
//	public static void catchBooks()
//	{
//		List<String> lines = new ArrayList<>();
//
////		lines.add("高中语文必修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37534&shelfId=2528&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_895918F8CA23A6B48FB29E7981F0CCBB.jpeg");
////		lines.add("高中语文必修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37535&shelfId=2528&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_D496044D55BC4E6C8B51366E9B1EF89A.jpeg");
////		lines.add("高中语文必修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37536&shelfId=2528&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_E693669592B31CFA68B500A7B63E4B00.jpeg");
////		lines.add("高中语文必修四" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37537&shelfId=2528&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_193BA85B717B224592AB356F97A7C3F5.jpeg");
////		lines.add("高中语文必修五" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37538&shelfId=2528&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_EA675C7D2791331F8137D0489721C279.jpeg");
//
////		lines.add("高中数学必修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37539&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_EA989EB3714A13E85ABB7E0B3ED80DD8.jpeg");
////		lines.add("高中数学必修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37540&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_D16A1DB0385ABB5948B79C12E23C37E5.jpeg");
////		lines.add("高中数学必修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37541&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_FE4ECE13D4A87FCB612A8E3720FB4D91.jpeg");
////		lines.add("高中数学必修四" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37542&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_70E375842E693D18B0EC0EEC48F542A4.jpeg");
////		lines.add("高中数学必修五" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37543&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_8A2EEA23E4E57F2F214D361E4B18CE1E.jpeg");
////		lines.add("高中数学选修1-1" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37544&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_AAB3706E9706EAD5157B7345319FDC70.jpeg");
////		lines.add("高中数学选修1-2" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37545&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_5A53329AB41CEA16B32F41D0E2F7DA6F.jpeg");
////		lines.add("高中数学选修2-1" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37546&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_47411E703FDC4AB2A2943D8D2FEB01BC.jpeg");
////		lines.add("高中数学选修2-2" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37547&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_BF3CF9D5BA8D0E9816FC0F8F18127AA9.jpeg");
////		lines.add("高中数学选修2-3" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37548&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_BCA7525B40616B874B383C7DD07695EC.jpeg");
////		lines.add("高中数学选修3-1" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37549&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_8D108D649D013346959E7A5BCC6D08C8.jpeg");
////		lines.add("高中数学选修3-3" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37550&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_5036A4CACA45D0273CB208937FDF6C90.jpeg");
////		lines.add("高中数学先修3-4" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37551&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_80785E0645AAB4AEEE6CBDBB14CF8782.jpeg");
////		lines.add("高中数学选修4-1" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37552&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_82E978FCDB70DDBFEC600ACA287AFC5D.jpeg");
////		lines.add("高中数学选修4-2" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37553&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_2F159C832DB069E71E82F174BF056CE5.jpeg");
////		lines.add("高中数学选修4-4" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37555&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_03867149F602AAAA16E97230BEF19097.jpeg");
////		lines.add("高中数学选修4-5" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37556&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_F991A9D871C4FB31368BA3F5CC37D443.jpeg");
////		lines.add("高中数学选修4-6" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37558&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_2FC9B9058968B1396A1F3701820983ED.jpeg");
////		lines.add("高中数学选修4-7" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37560&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_AEF1AB400D2E80C8378F5B58076343AD.jpeg");
////		lines.add("高中数学选修4-9" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37561&shelfId=2529&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_70155A423AF1A8E26EF4CC647E58825A.jpeg");
//
////		lines.add("高中英语必修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37563&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_6A7C8D379ABD876FABBB0D2CC224B234.jpeg");
////		lines.add("高中英语必修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37565&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_8B89B22068BAB774AB84485985273395.jpeg");
////		lines.add("高中英语必修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37566&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_7A96006976961E027D5FA103E9213301.jpeg");
////		lines.add("高中英语必修四" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37568&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_DC97EB37B61BA39CB926EC11B83E64C2.jpeg");
////		lines.add("高中英语必修五" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37569&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_4C0568F769F61172C553ADBD5C027D02.jpeg");
////		lines.add("高中英语选修六" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37570&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_0C6B7C0E678450F226A3371E3E5DC15B.jpeg");
////		lines.add("高中英语选修八" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37572&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_EC2E46B62B395190514E17DC669164EA.jpeg");
////		lines.add("高中英语选修九" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37573&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_33ACC087CB7752CAA8682BB2635788DF.jpeg");
////		lines.add("高中英语选修十" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37574&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_B81CE329E02AE1CAA472E217390A9B7A.jpeg");
////		lines.add("高中英语选修十一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37575&shelfId=2530&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_27E27452A04E9D0397C2C6B5CA1A1C9D.jpeg");
////
////		lines.add("高中物理必修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37606&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_BDC512A6AC823F3B58C596889516340D.jpeg");
////		lines.add("高中物理必修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37607&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_3AD1A23AF448093D95E70D371AD49387.jpeg");
////		lines.add("高中物理选修1-1" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37608&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_22EA1F50FBF037D59B607BF80ECB0755.jpeg");
////		lines.add("高中物理选修1-2" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37609&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_1AB5DD9E55485999F3C791194446FCDE.jpeg");
////		lines.add("高中物理选修2-1" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37610&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_233FF505AD3E9BC94F0348FCCB88EAA3.jpeg");
////		lines.add("高中物理选修2-2" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37611&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_E078A266FC2D42DF48034761A160B56C.jpeg");
////		lines.add("高中物理选修2-3" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37612&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_78281C9ACA5B5F27DBF5A301E9F0155C.jpeg");
////		lines.add("高中物理选修3-1" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37613&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_68E1C75625E81399422053CC68ACA0D7.jpeg");
////		lines.add("高中物理选修3-3" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37615&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_63E2B921FC86CAF2D7F4CF1E4A4125D8.jpeg");
////		lines.add("高中物理选修3-2" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37618&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_3E5F3A05E25F1C9140607FC8BCB23B55.jpeg");
////		lines.add("高中物理选修3-4" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37622&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_ECE648C0056C5F98C9106F11E0244690.jpeg");
////		lines.add("高中物理选修3-5" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37624&shelfId=2531&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_E49558ECEAD93BC8CB0EBEBF8F4CC890.jpeg");
////
////		lines.add("高中化学必修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37775&shelfId=2558&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_CBA7B106D12021DE5A2F49B011D51EBA.jpeg");
////		lines.add("高中化学必修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37776&shelfId=2558&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_FB1E0B4367444B9CD61602D2A5F240DC.jpeg");
////		lines.add("高中化学选修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37777&shelfId=2558&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_79DD1125817C0F0635810B7E4DCDE7B6.jpeg");
////		lines.add("高中化学选修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37778&shelfId=2558&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_6FAB603960421A474532F3E0154E9B77.jpeg");
////		lines.add("高中化学选修四" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37779&shelfId=2558&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_788EC4D4885C27D5F34AD37AB2C7827F.jpeg");
////		lines.add("高中化学选修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37780&shelfId=2558&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_31CB426BC7AAEC81AA950679574F832E.jpeg");
////		lines.add("高中化学选修五" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37781&shelfId=2558&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_BA3D836C7890EAB57607ED5C22B9BFE0.jpeg");
////		lines.add("高中化学选修六" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37782&shelfId=2558&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_3C67EF04629B0AAD47A9EBD026617A9A.jpeg");
////
////		lines.add("高中生物必修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37786&shelfId=2559&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_3043E49CCB638BB8581F224CBDB3042C.jpeg");
////		lines.add("高中生物必修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37789&shelfId=2559&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_94F05182F4F7669A24EC2A7578CAB88E.jpeg");
////		lines.add("高中生物必修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37791&shelfId=2559&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_ADE3F10B2C2086985B5EBFFFD2D65355.jpeg");
////		lines.add("高中生物选修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37793&shelfId=2559&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_D23DD925687720F80A02B0080F11D9C0.jpeg");
////		lines.add("高中生物选修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37794&shelfId=2559&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_C6318DA3B013F76D0E066F9F517221BB.jpeg");
////		lines.add("高中生物选修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37795&shelfId=2559&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_44B7F445DDB2C9147C72E237DBDDD2DD.jpeg");
////
////		lines.add("高中政治必修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37802&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_729A1046FB2AFDA952A08357125BC07D.jpeg");
////		lines.add("高中政治必修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37809&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_D007E1AA414E3E52D12193A578D09D4F.jpeg");
////		lines.add("高中政治必修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37810&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_0CCD83188BA3A3B3A2106BF13A5B2C82.jpeg");
////		lines.add("高中政治必修四" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37811&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_68F5D7153F9D7A908EF3DBBE57160DEE.jpeg");
////		lines.add("高中政治选修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37812&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_277E3559AD091A776B076F8392E243E0.jpeg");
////		lines.add("高中政治选修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37813&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_6C4FD17F6029A0EFD031DDB8D4C808CD.jpeg");
////		lines.add("高中政治选修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37814&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_CA97919417B3059752F8D271AD0F53A5.jpeg");
////		lines.add("高中政治选修四" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37815&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_2D8FCADD91CDAF1AF4AB7D3968F1BFD2.jpeg");
////		lines.add("高中政治选修五" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37816&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_A940334BC8198F8C095FA154AE3441EE.jpeg");
////		lines.add("高中政治选修六" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37817&shelfId=2571&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_8A45F5E3E3870387FC43EBC5D9A7CB41.jpeg");
////
////		lines.add("高中历史必修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37844&shelfId=2572&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_D0F182B07B9291B57963AE6093FF2566.jpeg");
////		lines.add("高中历史必修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37845&shelfId=2572&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_932BA8451B3066C37E28AC9F5A118185.jpeg");
////		lines.add("高中历史必修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37846&shelfId=2572&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_92FB7885EA32F378A55151B872204340.jpeg");
////		lines.add("高中历史选修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37847&shelfId=2572&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_6A4766513B25504AB2F06850B6533B12.jpeg");
////		lines.add("高中历史选修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37848&shelfId=2572&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_759955EDF002F5C5116BEFF550BFA7D4.jpeg");
////		lines.add("高中历史选修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37849&shelfId=2572&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_98AFFC2BD1DE83771F02743070292E32.jpeg");
////		lines.add("高中历史选修四" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37850&shelfId=2572&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_6156F8101614E7C4BEBEC52873CDBD8B.jpeg");
////		lines.add("高中历史选修六" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37851&shelfId=2572&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_4E4122FD2E0896DB24BCF79196591578.jpeg");
////		lines.add("高中历史选修五" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37852&shelfId=2572&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_E5D395AF1EB5CD3C8C7B6B378AA2F050.jpeg");
////
////		lines.add("高中地理必修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37853&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_958A5F6E2EF052DADDB5A60E1A0D3E52.jpeg");
////		lines.add("高中地理必修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37854&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_5702E3F8D3DE6E105ED6FA2F55A2C8DC.jpeg");
////		lines.add("高中地理必修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37855&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_4D520BE93D267DCD8743EC7EBFF2F61D.jpeg");
////		lines.add("高中地理选修一" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37858&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_324B76E25728C637A30E374BD9FD43B6.jpeg");
////		lines.add("高中地理选修二" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37859&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_859CCF4028E998FCBEF98BA6A24CC03A.jpeg");
////		lines.add("高中地理选修三" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37860&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_299253F17BA51B09945AC8BE505150B6.jpeg");
////		lines.add("高中地理选修四" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37861&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_39B3552DF00F3DCA113F48809E034FC8.jpeg");
////		lines.add("高中地理选修五" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37862&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_279F9E90C7CB196AE98690CCB3F24024.jpeg");
////		lines.add("高中地理选修六" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37863&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_FCA43C8132C2F8A03547A317EEA5E747.jpeg");
////		lines.add("高中地理选修七" + "-->" + "https://mp.codeup.cn/book/sample.htm?id=37864&shelfId=2573&mallId=263" + "-->" + "http://cdn11.bookln.cn/51046811_3554BE02193E5E7ABA93371737E62178.jpeg");
//
//		LOG.v("lines:" + lines.size());
//
//		HashMap<String, String> nameMap = new HashMap<String, String>();
//		HashMap<String, String> urlMap = new HashMap<String, String>();
//
//		for (String line : lines)
//		{
//			String[] strs = line.split("-->");
//			String name = strs[0];
//			String url = strs[1];
//			String cover = strs[2];
////			LOG.v(name + "\t" + url + "\t" + cover);
//			nameMap.put(name, "");
//			urlMap.put(url, "");
////			catchBook(name, cover);
////			getBookTask(name);
////			break;
//		}
//
//		LOG.v("nameMap:" + nameMap.size());
//		LOG.v("urlMap:" + urlMap.size());
//	}

	public static boolean genJuanTaskImpl(File dir, String phase, String subject, String taskId, int topicShowWidth, boolean hasSelectTopic)
	{
		LOG.v(String.format("genJuanTask : %s - %s", dir.getName(), taskId));
		File jsonFile = new File(dir, "main" + ".json");
		File topicImageDir = new File(dir, "main");
		if (jsonFile.exists() && topicImageDir.exists())
		{
			List<SZXTopic> topics = zhixue.loadTopics(jsonFile);

			File taskDir = new File(dir, taskId);
			FsUtils.delete(taskDir);
			FsUtils.createDir(taskDir);

			File filePageset = new File(String.format("%s/pageset.json", taskDir));
			SNotePageSet pageset = new SNotePageSet();

			for (int i = 0; i < topics.size(); i++)
			{
				SZXTopic topic = topics.get(i);

				String pageName = "topic" + (i + 1);

				File contentImageFile = new File(topicImageDir, String.format("%02d.content.png", i + 1));
				File parseImageFile = new File(topicImageDir, String.format("%02d.parse.png", i + 1));

				SNotePage pageData = new SNotePage();
				pageData.pageDir = pageName;
				pageset.pages.add(pageData);

				File dirPage = new File(String.format("%s/%s", taskDir, pageName));
				FsUtils.createDir(dirPage);

				SBoardConfig board = new SBoardConfig();
				board.bg = 0;
				board.height = 1200;

				int selectionY = 0;

				{
					String contentPath = String.format("juan/%s%s/%s/%02d.content.png", phase, subject, taskId, i + 1);
					String parsePath = String.format("juan/%s%s/%s/%02d.parse.png", phase, subject, taskId, i + 1);

					String contentUrl = OssUtils.doUpload(OssUtils.ZjykFile, contentImageFile, contentPath);
					if (TextUtils.isEmpty(contentUrl))
					{
						LOG.e("upload content fail at : " + contentImageFile);
						return false;
					}

					String parseUrl = OssUtils.doUpload(OssUtils.ZjykFile, parseImageFile, parsePath);
					if (TextUtils.isEmpty(parseUrl))
					{
						LOG.e("upload content fail at : " + parseImageFile);
						return false;
					}

					SBoardPhoto photo = new SBoardPhoto();
					photo.type = 2;
					photo.name = "topic";
					photo.rotation = 0;
					photo.cover = contentUrl;
					photo.url = parseUrl;
					photo.duration = 0L;

					BufferedImage image = ImageUtil.readImage(contentImageFile.toString());

					float scaleX = 1.0f * topicShowWidth / image.getWidth();

					float scale = scaleX;

					photo.width = (int) (image.getWidth() * scale) + 28;
					photo.height = (int) (image.getHeight() * scale) + 28;

					photo.x = 10;
					photo.y = 10;

					selectionY = photo.y + photo.height;

					board.photos.add(photo);
				}

				boolean isSelectTopic = false;
				if (topic.answer.equals("A") || topic.answer.equals("AA"))
				{
					topic.answer = "A";
					isSelectTopic = true;
				}
				else if (topic.answer.equals("B") || topic.answer.equals("BB"))
				{
					topic.answer = "B";
					isSelectTopic = true;
				}
				else if (topic.answer.equals("C") || topic.answer.equals("CC"))
				{
					topic.answer = "C";
					isSelectTopic = true;
				}
				else if (topic.answer.equals("D") || topic.answer.equals("DD"))
				{
					topic.answer = "D";
					isSelectTopic = true;
				}

				if (hasSelectTopic && isSelectTopic)
				{
					SBoardPhoto photo = new SBoardPhoto();
					photo.type = 3;
					photo.name = "selection";
					photo.rotation = 0;
					photo.duration = 0L;

					photo.width = 1244;
					photo.height = 220;

					photo.x = 0;
					photo.y = selectionY;

					if (topic.answer.equals("A") || topic.answer.equals("B") || topic.answer.equals("C") || topic.answer.equals("D"))
					{
						photo.selectionGroup = new SSelectionGroup();
						photo.selectionGroup.problemType = 1;
						for (char c = 'A'; c <= 'D'; c++)
						{
							photo.selectionGroup.selections.add(String.valueOf(c));
						}
						photo.selectionGroup.rightAnswer.add(topic.answer);
					}
					else
					{
						LOG.v("错误的选择题答案：" + topic.answer);
						return false;
					}

					board.photos.add(photo);
				}

				File fileBoardJson = new File(String.format("%s/board.json", dirPage));
				FsUtils.writeText(fileBoardJson, LOGJson.getStr(board.saveToStr()));
			}

			FsUtils.writeText(filePageset, LOGJson.getStr(pageset.saveToStr()));
			return true;
		}
		return false;
	}

	public static SPTask genJuanTask(File dir, String phase, String subject, String material, int topicShowWidth, boolean hasSelectTopic)
	{
		if (dir.exists())
		{
			String group = String.format("%s%s", phase, subject);
			String name = material;
			SPTask task = FindTask(group, name);
			if (task == null)
			{
				SRequest_CreateTask request = new SRequest_CreateTask();
				request.userId = "root";
				request.name = name;
				request.group = group;
				request.type = SPTaskType.Job;
				request.jobType = SPJobType.MultTopic;
				String data = doPost(API, SHandleId.CreateTask, request.saveToStr());
				if (!TextUtils.isEmpty(data))
				{
					SResponse_CreateTask response = SResponse_CreateTask.load(data);
					task = response.task;
				}
			}
			if (task != null)
			{
				if (genJuanTaskImpl(dir, phase, subject, task.id, topicShowWidth, hasSelectTopic))
				{
					return task;
				}
			}
		}
		return null;
	}

//	public static SResponse_GetUserList GetUserList(int userType)
//	{
//		SRequest_GetUserList request = new SRequest_GetUserList();
//		request.userType = userType;
//		String data = doPost(API, SHandleId.GetUserList, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//			return SResponse_GetUserList.load(data);
//		else
//			return null;
//	}
//
//	public static void checkTaskGroup()
//	{
//		HashMap<String, Integer> map = new HashMap<String, Integer>();
//		for (int userType = SUserType.SupterMaster; userType <= SUserType.Student; userType++)
//		{
//			SResponse_GetUserList responseUserList = GetUserList(userType);
//			for (int i = 0; i < responseUserList.users.size(); i++)
//			{
//				SUser user = responseUserList.users.get(i);
//				if (i % 100 == 0)
//					System.out.println(String.format("%s - %s/%s", userType, i, responseUserList.users.size()));
//				SResponse_GetTaskList responseTaskList = GetTaskList(user.id);
//				for (SPTask task : responseTaskList.tasks)
//				{
//					if (map.containsKey(task.group))
//						map.put(task.group, map.get(task.group) + 1);
//					else
//						map.put(task.group, 1);
//				}
//			}
//		}
//
//		ArrayList<String> keySet = new ArrayList<String>(map.keySet());
//		Collections.sort(keySet, new Comparator<String>()
//		{
//			@Override
//			public int compare(String key1, String key2)
//			{
//				return map.get(key1).compareTo(map.get(key2));
//			}
//		});
//		for (String key : keySet)
//		{
//			System.out.println(String.format("%s\t%s", map.get(key), key));
//		}
//	}
//
//	public static final String EventAction_TeachCall = "TeachCall";
//	public static final String EventAction_TeachRefuse = "TeachRefuse";
//	public static final String EventAction_TeachAgree = "TeachAgree";
//	public static final String EventAction_TeachReady = "TeachReady";
//	public static final String EventAction_TeachStart = "TeachStart";
//	public static final String EventAction_TeachOver = "TeachOver";
//	public static final String EventAction_TeachQuit = "TeachQuit";
//
//	public static long teachRecordParseSecond(String des)
//	{
//		long second = 0;
//		des = des.trim();
//		int pos = des.indexOf("耗时：");
//		if (pos > 0)
//		{
//			des = des.substring(pos + "耗时：".length());
//			String[] parts = des.split(":");
//			int danwei = 1;
//			for (int i = parts.length - 1; i >= 0; i--)
//			{
//				String part = parts[i];
////				LOG.v("" + part);
//				second += Integer.parseInt(part) * danwei;
//				danwei *= 60;
//			}
//		}
////		LOG.v("" + second);
//		if (second > 5 * 3600)
//		{
//			LOG.e("error : " + second);
//			second = 0;
//		}
//		return second;
//	}
//
//	public static long teachRecord(String userId)
//	{
//		long allTime = 0;
//		SRequest_GetEventList request = new SRequest_GetEventList();
//		request.userId = userId;
//		request.actions.add(EventAction_TeachCall);
//		request.actions.add(EventAction_TeachRefuse);
//		request.actions.add(EventAction_TeachAgree);
//		request.actions.add(EventAction_TeachReady);
//		request.actions.add(EventAction_TeachStart);
//		request.actions.add(EventAction_TeachOver);
//		request.actions.add(EventAction_TeachQuit);
//		String data = doPost(API, SHandleId.GetEventList, request.saveToStr());
//		if (!TextUtils.isEmpty(data))
//		{
//			SResponse_GetEventList response = SResponse_GetEventList.load(data);
//			String lastTargetId = null;
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < response.events.size(); i++)
//			{
//				SEvent event = response.events.get(i);
//				String str = event.des;
//				if (str.endsWith("\r\n"))
//					str = str.substring(0, str.length() - "\r\n".length());
//				str = str.replace("\r\n", "\r\n\t\t\t\t\t");
////				if (!event.target.equals(lastTargetId))
////					sb.append(String.format("%s--------【%s】\r\n", TextUtils.isEmpty(lastTargetId) ? "" : "\r\n", event.target));
////				if (event.action.equals(EventAction_TeachOver) || event.action.equals(EventAction_TeachQuit))
//				if (str.indexOf("耗时：") > 0)
//				{
//					allTime += teachRecordParseSecond(str);
//					sb.append(String.format("%s\t%s\t%s\r\n", formatDate.format(new Date(event.time)), event.action, str));
//				}
//				lastTargetId = event.target;
//			}
//			System.out.print(sb.toString());
//		}
//		long time = allTime;
//		long hour = time / 3600;
//		time = time % 3600;
//		long minues = time / 60;
//		long second = time % 60;
//		System.out.println(String.format("allTime : %s\t%s:%s:%s", allTime, hour, minues, second));
//		return allTime;
//	}
//
//	public static void teachRecord()
//	{
//		long allTime = 0;
////		HashMap<String, Integer> map = new HashMap<String, Integer>();
//		for (int userType = SUserType.Teacher; userType <= SUserType.Teacher; userType++)
//		{
//			SResponse_GetUserList responseUserList = GetUserList(userType);
//			for (int i = 0; i < responseUserList.users.size(); i++)
//			{
//				SUser user = responseUserList.users.get(i);
//				System.out.println(String.format("----------------%s:%s(%s) - %s/%s", userType, user.name, user.id, i, responseUserList.users.size()));
////				SResponse_GetTaskList responseTaskList = GetTaskList(user.id);
////				for (SPTask task : responseTaskList.tasks)
////				{
////					if (map.containsKey(task.group))
////						map.put(task.group, map.get(task.group) + 1);
////					else
////						map.put(task.group, 1);
////				}
//				allTime += teachRecord(user.id);
//				System.out.println("");
//			}
//		}
//		long time = allTime;
//		long hour = time / 3600;
//		time = time % 3600;
//		long minues = time / 60;
//		long second = time % 60;
//		System.out.println(String.format("all teacher time : %s\t%s:%s:%s", allTime, hour, minues, second));
//	}
//
//	public static void processTeachRecord()
//	{
//		try
//		{
//			DBClient.processTeachRecord();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	public static void printFields(Class cls, Object obj)
//	{
//		try
//		{
//			Field[] fields = cls.getFields();
//			if (fields != null && fields.length > 0)
//			{
//				StringBuilder sb_createTable = new StringBuilder();
//				StringBuilder sb_as = new StringBuilder();
//				StringBuilder sb_t_name = new StringBuilder();
//				StringBuilder sb_d_name = new StringBuilder();
//				StringBuilder sb_update = new StringBuilder();
//				for (Field field : fields)
//				{
//					field.setAccessible(true);
//					try
//					{
//						if (field.getName().equals("ts"))
//							continue;
//						sb_createTable.append(String.format("            %s xxxxx,", field.get(obj)) + "\r\n");
////						sb_as.append(String.format("            %s as %s,", field.get(obj), field.getName()) + "\r\n");
//						sb_as.append(String.format("		<result property=\"%s\" column=\"%s\"/>", field.getName(), field.get(obj)) + "\r\n");
//						sb_t_name.append(String.format("            %s,", field.get(obj)) + "\r\n");
//						sb_d_name.append(String.format("            #{%s},", field.getName()) + "\r\n");
//						sb_update.append(String.format("			%s=#{%s},", field.get(obj), field.getName()) + "\r\n");
//					}
//					catch (IllegalAccessException e)
//					{
//						System.out.println("eeeeeeeeee");
//					}
//				}
//				System.out.println(sb_createTable.toString());
//				System.out.println(sb_as.toString());
//				System.out.println(sb_t_name.toString());
//				System.out.println(sb_d_name.toString());
//				System.out.println(sb_update.toString());
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args)
//	{
//		StaticConfig.isTomcat = false;
//		DBHelper.fromClient = true;
//
//		MediaUtil.setFFmpegPath("C:/wangzhiting/SDK/ffmpeg-4.2.3-win64-static/bin/ffmpeg.exe");
////		MediaUtil.isExecutable();
//
////		teachRecord();
//
////		processTeachRecord();
//
////		printFields(Table.Task.class, Table.task);
////		printFields(Table.Friend.class, Table.friend);
////		printFields(Table.Topic_Record.class, Table.topicRecord);
////		printFields(Table.User.class, Table.user);
//
////		genJuanTask(new File("D:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/pair/juan/高一数学/2019-2020学年湖北省武汉十五中、十七中、常青一中高一（上）期末数学试卷"), "高一", "数学", "2019-2020学年湖北省武汉十五中、十七中、常青一中高一（上）期末数学试卷");
//
////		MediaUtil.convertVideo(new File("C:/Users/wzt/Desktop/kkk/5a37f6a62f78ef8547351e312d916f57.mp4"), //
////				new File("C:/Users/wzt/Desktop/kkk/kkkkkkkkk.mp4"), //
////				true, CrfValueEnum.HIGH_QUALITY.getCode(), PresetVauleEnum.MEDIUM_ZIP_SPEED.getPresetValue(), 0, 0);
//
////		processFriendGroup("mst2");
//
////		catchBooks();
//
////		checkTaskGroup();
//
////		GetServerState();
////		GetTimeRecord();
//
////		try
////		{
////			addFriends();
////
////			showFriends("lysxym");
////			showFriends("lysqdf");
////			showFriends("lyscdj");
////			showFriends("lysx00");
////			showFriends("lysyyl");
////			showFriends("lysgxb");
////			showFriends("lyslp");
////			showFriends("lyszdy");
////			showFriends("lyszhk");
////			showFriends("lyszm");
////		}
////		catch (Exception e)
////		{
////			e.printStackTrace();
////		}
//
////		DeleteTask("e2850761-acae-465a-aaab-8f930b35451e");
//
////		for (int i = 11; i <= 50; i++)
////		{
////			if (i == 27)
////				continue;
////			String userId = "lysxsty" + i;
////			LOG.v("" + userId);
////			SResponse_GetFriendList list = GetFriendList(userId);
////			for (SUser friend : list.friends)
////			{
////				if (!friend.id.equals("mst1"))
////					LOG.v(String.format("\t%s@%s", friend.id, friend.name));
////			}
////		}
//
////		AddFriend(userId, "lysgzy2");
////		DeleteFriend("lysxsty38", "lysgzy2");
//
////		for (int i = 11; i <= 50; i++)
////		{
////			if (i == 27)
////				continue;
////			String userId = "lysxsty" + i;
////			LOG.v("" + userId);
////			SResponse_TopicRecordGetList list = TopicRecordGetList(userId);
////			for (STopicRecord topicRecord : list.topicRecords)
////			{
////				LOG.v(String.format("\t%s@%s", topicRecord.userId, topicRecord.topicId));
////			}
////		}
//
////		TopicRecordDelete(topicRecord.userId, topicRecord.topicId);
//
////		tickServer();
////		syncOss(new File("D:/oss/zjyk-file"));
//
////		for (int i = 8; i <= 8; i++)
////		{
////			AddUser("lysxsty" + i, "学生体验" + i, "123", SUserType.Student, SSex.Girl);
////		}
//
////		checkBoardData(new File("E:\\lys.tasks"));
//
////		checkUrls(new File("D:\\wangzhiting\\work\\lys.tasks\\mst1\\28e54e8a-cf4a-46d4-bab4-a983327f87f8"));//work
////		checkUrls(new File("D:\\wangzhiting\\work\\lys.tasks\\mst1\\c9780c7f-109d-4fb2-b20a-e2f5b359a798"));
////		checkUrls(new File("D:\\wangzhiting\\work\\lys.tasks\\lysgzy2\\ed12d72c-dc80-4849-aca0-f6d49e3b9117"));
//
////		uploadoss(new File("D:\\360极速浏览器下载\\28e54e8a-cf4a-46d4-bab4-a983327f87f8\\new"));
////		uploadoss(new File("D:\\360极速浏览器下载\\c9780c7f-109d-4fb2-b20a-e2f5b359a798\\new"));
////		uploadoss(new File("D:\\360极速浏览器下载\\ed12d72c-dc80-4849-aca0-f6d49e3b9117\\new"));
//
////		changeUrls(new File("D:\\wangzhiting\\work\\lys.tasks"), new File("D:\\360极速浏览器下载\\28e54e8a-cf4a-46d4-bab4-a983327f87f8\\new\\path.txt"));
////		changeUrls(new File("D:\\wangzhiting\\work\\lys.tasks"), new File("D:\\360极速浏览器下载\\c9780c7f-109d-4fb2-b20a-e2f5b359a798\\new\\path.txt"));
////		changeUrls(new File("D:\\wangzhiting\\work\\lys.tasks"), new File("D:\\360极速浏览器下载\\ed12d72c-dc80-4849-aca0-f6d49e3b9117\\new\\path.txt"));
//
////		genNetImg(new File("D:/wangzhiting/work/pair_ui/学生板/插图"), "http://file.k12-eco.com/插图", 200);
//
////		testSvn();
//
////		writeImageToBytes(new File("C:\\Users\\xnktyu\\Desktop\\222.png"), new File("C:\\Users\\xnktyu\\Desktop\\222.txt"));
////		writeImageToBytes(new File("C:\\Users\\xnktyu\\Desktop\\board.png"), new File("C:\\Users\\xnktyu\\Desktop\\board.txt"));
//
////		uploadAppInfo_main();
////		uploadAppInfo_market();
////		uploadAppInfo_zhixue();
//
////		genKnowledgeTree(new File("C:/wangzhiting/work/code/client/java/pair/share/catch_zhixue_index"), SPhase.Chu, SSubject.Li);
////		genChapterTree(new File("C:/wangzhiting/work/code/client/java/pair/share/catch_zhixue_index"), SPhase.Chu, SSubject.Li);
////		genKnowledgeTree(new File("C:/wangzhiting/work/code/client/java/pair/share/catch_zhixue_index"), SPhase.Chu, SSubject.Shu);
////		genChapterTree(new File("C:/wangzhiting/work/code/client/java/pair/share/catch_zhixue_index"), SPhase.Chu, SSubject.Shu);
////		genKnowledgeTree(new File("C:/wangzhiting/work/code/client/java/pair/share/catch_zhixue_index"), SPhase.Gao, SSubject.Shu);
////		genChapterTree(new File("C:/wangzhiting/work/code/client/java/pair/share/catch_zhixue_index"), SPhase.Gao, SSubject.Shu);
////		genKnowledgeTree(new File("C:/wangzhiting/work/code/client/java/pair/share/catch_zhixue_index"), SPhase.Gao, SSubject.Li);
////		genChapterTree(new File("C:/wangzhiting/work/code/client/java/pair/share/catch_zhixue_index"), SPhase.Gao, SSubject.Li);
//
////		addTopic(new File("E:/catch_zhixue"), new File("D:/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/pair/topic/imgs")); // 已弃用
////		genTopicStyle(new File("C:/wangzhiting/work/code/client/java/pair/WebContent/fixed"));
//
////		printPx(1920);
////		printPx(1280);
////		printSp(1);
//
////		searchTopic();
////		searchResource();
////		requestLog();
////		testProtocol();
//
////		String url = doUpload(new File("C:/wangzhiting/work/code/client/as3/apk/app_pair/release/app-release.apk"), "/apk/app-release.apk");
////		LOG.v(url);
//
//		LOG.v("-------------------------------- process over --------------------------------");
//	}
}