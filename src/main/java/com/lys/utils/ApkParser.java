package com.lys.utils;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ApkParser
{
	private static void printElement(Element element) throws Exception
	{
		LOG.v(element.getName());
		for (Attribute attribute : element.attributes())
		{
			LOG.v(attribute.getName() + " : " + attribute.getValue());
		}
	}

	private static void parserManifest(Map<String, String> result, String manifestXml) throws Exception
	{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new ByteArrayInputStream(manifestXml.getBytes()));
		Element root = document.getRootElement();

//		for (Element permission : root.elements("uses-permission"))
//		{
//			String permissionName = permission.attribute("name").getValue();
//			LOG.v("permission" + " : " + permissionName);
//		}

		Element application = root.element("application");
		for (Element metadata : application.elements("meta-data"))
		{
			String metadataName = metadata.attribute("name").getValue();
			String metadataValue = metadata.attribute("value").getValue();
//			LOG.v(metadataName + " : " + metadataValue);
			result.put(metadataName, metadataValue);
		}
	}

	public static Map<String, String> parser(File file)
	{
		Map<String, String> result = new HashMap<>();
		try
		{
			ApkFile apkFile = new ApkFile(file);

			String manifestXml = apkFile.getManifestXml();
//			LOG.v(manifestXml);
//			FsUtils.writeText(new File("C:/Users/wzt/Desktop/tmp.xml"), manifestXml);
			parserManifest(result, manifestXml);

			ApkMeta apkMeta = apkFile.getApkMeta();
//			LOG.v(apkMeta);

			result.put("packageName", apkMeta.getPackageName());
			result.put("label", apkMeta.getLabel());
//			result.put("icon", apkMeta.getIcon());
			result.put("versionName", apkMeta.getVersionName());
			result.put("versionCode", String.valueOf(apkMeta.getVersionCode()));
			result.put("minSdkVersion", apkMeta.getMinSdkVersion());
			result.put("targetSdkVersion", apkMeta.getTargetSdkVersion());

			apkFile.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static void test(File file)
	{
		try
		{
			ApkFile apkFile = new ApkFile(file);

			String manifestXml = apkFile.getManifestXml();
			LOG.v(manifestXml);

//			String xml = apkFile.transBinaryXml("res/menu/main.xml");
//			LOG.v(xml);

			ApkMeta apkMeta = apkFile.getApkMeta();
			LOG.v(apkMeta);
//			for (UseFeature feature : apkMeta.getUsesFeatures())
//			{
//				LOG.v(feature.getName());
//			}

//			DexClass[] classes = apkFile.getDexClasses();
//			for (DexClass dexClass : classes)
//			{
//				LOG.v(dexClass);
//			}

//			List<ApkSigner> signers = apkFile.getApkSingers(); // apk v1 signers
//			for (ApkSigner signer : signers)
//			{
//				LOG.v("signer : " + signer);
//			}
//			List<ApkV2Signer> v2signers = apkFile.getApkV2Singers(); // apk v2 signers
//			for (ApkV2Signer v2signer : v2signers)
//			{
//				LOG.v("v2signer : " + v2signer);
//			}

			apkFile.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
//		test(new File("D:/TMP/upload/40c7e1811e81325179cb309117102aa2.apk"));
		Map<String, String> result = parser(new File("D:/TMP/upload/40c7e1811e81325179cb309117102aa2.apk"));
		for (Map.Entry<String, String> entry : result.entrySet())
		{
			LOG.v(entry.getKey() + " : " + entry.getValue());
		}
	}

}
