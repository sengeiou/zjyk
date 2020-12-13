package com.lys.app.manager;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.lys.utils.LOG;
import com.lys.utils.MD5;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OssUtils
{
	private static final String endpoint = "http://oss-cn-huhehaote.aliyuncs.com";

	// 子KEY
	private static final String accessKeyId = "LTAI4FqPqU7JmnqMZ2QALVBt";
	private static final String accessKeySecret = "1ZATEbLMunyzUyh7LZUm36mMpDVxKu";

	public static final String ERROR = "error";

	public static final String ZjykFile = "zjyk-file";
	public static final String ZjykTopic = "zjyk-topic";

	public static final String DirHead = "head/";
	public static final String DirVideo = "video/";

	public static String getHost(String bucketName)
	{
//		if (bucketName.equals(ZjykFile))
//			return "http://file.k12-eco.com/";
//		else if (bucketName.equals(ZjykTopic))
//			return "http://topic.k12-eco.com/";
//		else
		return "http://" + bucketName + ".oss-cn-huhehaote.aliyuncs.com/";
	}

	private static OSS ossClient = null;

	private static OSS getOssClient()
	{
		if (ossClient == null)
		{
			ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		}
		return ossClient;
	}

	private static void shutdown()
	{
		if (ossClient != null)
		{
			ossClient.shutdown();
			ossClient = null;
		}
	}

	public interface OnProgressListener
	{
		void onProgress(long written, long total, int percent);

		void onSuccess(String url);

		void onFail();
	}

	public static void doUploadWithProgress(String bucketName, File file, String path, OnProgressListener listener)
	{
		try
		{
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, file);
			putObjectRequest.withProgressListener(new ProgressListener()
			{
				private long bytesWritten = 0;
				private long totalBytes = -1;
				private boolean succeed = false;

				@Override
				public void progressChanged(ProgressEvent progressEvent)
				{
					long bytes = progressEvent.getBytes();
					ProgressEventType eventType = progressEvent.getEventType();
					switch (eventType)
					{
					case TRANSFER_STARTED_EVENT:
//						LOG.v("Start to upload......");
						break;
					case REQUEST_CONTENT_LENGTH_EVENT:
						this.totalBytes = bytes;
//						LOG.v(this.totalBytes + " bytes in total will be uploaded to OSS");
						break;
					case REQUEST_BYTE_TRANSFER_EVENT:
						this.bytesWritten += bytes;
						if (this.totalBytes != -1)
						{
							int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
//							LOG.v(bytes + " bytes have been written at this time, upload progress: " + percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
							if (listener != null)
								listener.onProgress(this.bytesWritten, this.totalBytes, percent);
						}
						else
						{
//							LOG.v(bytes + " bytes have been written at this time, upload ratio: unknown" + "(" + this.bytesWritten + "/...)");
							if (listener != null)
								listener.onProgress(this.bytesWritten, this.totalBytes, -1);
						}
						break;
					case TRANSFER_COMPLETED_EVENT:
						this.succeed = true;
//						LOG.v("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
						if (listener != null)
							listener.onSuccess(getHost(bucketName) + path);
						break;
					case TRANSFER_FAILED_EVENT:
//						LOG.v("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
						if (listener != null)
							listener.onFail();
						break;
					default:
						break;
					}
				}
			});
			getOssClient().putObject(putObjectRequest);
		}
		catch (Exception e)
		{
		}
	}

	public static void doUploadMd5FileWithProgress(String bucketName, File file, String dir, OnProgressListener listener)
	{
		int pos = file.getName().lastIndexOf('.');
		String suffix = "";
		if (pos >= 0)
			suffix = file.getName().substring(pos);
		String md5 = MD5.calcMD5(file);
		String path = String.format("%s%s/%s%s", dir, md5.substring(md5.length() - 2), md5, suffix);
		String existResult = fileExist(bucketName, path);
		if (ERROR.equals(existResult))
		{
			if (listener != null)
				listener.onFail();
		}
		else if (StringUtils.isEmpty(existResult))
		{
			doUploadWithProgress(bucketName, file, path, listener);
		}
		else
		{
			if (listener != null)
				listener.onSuccess(existResult);
		}
	}

	public static String doUpload(String bucketName, File file, String path)
	{
		try
		{
			getOssClient().putObject(bucketName, path, file);
			return getHost(bucketName) + path;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static String doUploadUntil(String bucketName, File file, String path)
	{
		while (true)
		{
			String url = doUpload(bucketName, file, path);
			if (!StringUtils.isEmpty(url))
				return url;
		}
	}

	public static String fileExist(String bucketName, String path)
	{
		try
		{
			boolean exist = getOssClient().doesObjectExist(bucketName, path);
			if (exist)
				return getHost(bucketName) + path;
			else
				return null;
		}
		catch (Exception e)
		{
			return ERROR;
		}
	}

	public static boolean listImpl(String bucketName, String path, List<String> dirs, List<String> files)
	{
		try
		{
			String nextMarker = null;
			ObjectListing listing;
			do
			{
				ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
				listObjectsRequest.setDelimiter("/");
				listObjectsRequest.setPrefix(path);
				listObjectsRequest.setMarker(nextMarker);
				listing = getOssClient().listObjects(listObjectsRequest);
//				LOG.v("Dirs:");
				for (String commonPrefix : listing.getCommonPrefixes())
				{
//					LOG.v("d : " + commonPrefix);
					dirs.add(commonPrefix);
				}
//				LOG.v("Files:");
				for (OSSObjectSummary objectSummary : listing.getObjectSummaries())
				{
					if (!objectSummary.getKey().equals(path))
					{
//						LOG.v("f : " + objectSummary.getKey());
						files.add(objectSummary.getKey());
					}
				}
				nextMarker = listing.getNextMarker();
			} while (listing.isTruncated());
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public static void list(String bucketName, String path, List<String> allFiles, int indent)
	{
		List<String> dirs = new ArrayList<String>();
		List<String> files = new ArrayList<String>();
		if (listImpl(bucketName, path, dirs, files))
		{
			int index = 0;
			allFiles.addAll(files);
			for (int i = 0; i < dirs.size(); i++)
			{
//				LOG.v(String.format("%s%3s : [%s]", CommonUtils.getIndentStr(indent), ++index, dirs.get(i).substring(path.length())));
				list(bucketName, dirs.get(i), allFiles, indent + 1);
			}
			for (int i = 0; i < files.size(); i++)
			{
//				LOG.v(String.format("%s%3s : %s", CommonUtils.getIndentStr(indent), ++index, files.get(i).substring(path.length())));
			}
		}
	}

	public static List<String> listAllFiles(String bucketName, String path)
	{
		List<String> allFiles = new ArrayList<String>();
		list(bucketName, path, allFiles, 0);
//		int index = 0;
//		for (String file : allFiles)
//		{
//			LOG.v(String.format("%3s : %s", ++index, file));
//		}
		return allFiles;
	}

	// ----------------------------------------------------

	private static boolean exist(File file, String path)
	{
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		PutObjectRequest putObjectRequest = new PutObjectRequest("zjyk-file", path, file);

		boolean found = ossClient.doesObjectExist("zjyk-file", path);

		ossClient.shutdown();

		return found;
	}

	private static void test(File file, String path)
	{
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		PutObjectRequest putObjectRequest = new PutObjectRequest("zjyk-file", path, file);
		putObjectRequest.withProgressListener(new ProgressListener()
		{
			private long bytesWritten = 0;
			private long totalBytes = -1;
			private boolean succeed = false;

			@Override
			public void progressChanged(ProgressEvent progressEvent)
			{
				long bytes = progressEvent.getBytes();
				ProgressEventType eventType = progressEvent.getEventType();
				switch (eventType)
				{
				case TRANSFER_STARTED_EVENT:
					LOG.v("Start to upload......");
					break;
				case REQUEST_CONTENT_LENGTH_EVENT:
					this.totalBytes = bytes;
					LOG.v(this.totalBytes + " bytes in total will be uploaded to OSS");
					break;
				case REQUEST_BYTE_TRANSFER_EVENT:
					this.bytesWritten += bytes;
					if (this.totalBytes != -1)
					{
						int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
						LOG.v(bytes + " bytes have been written at this time, upload progress: " + percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
					}
					else
					{
						LOG.v(bytes + " bytes have been written at this time, upload ratio: unknown" + "(" + this.bytesWritten + "/...)");
					}
					break;
				case TRANSFER_COMPLETED_EVENT:
					this.succeed = true;
					LOG.v("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
					break;
				case TRANSFER_FAILED_EVENT:
					LOG.v("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
					break;
				default:
					break;
				}
			}
		});
		ossClient.putObject(putObjectRequest);
		ossClient.shutdown();
	}


}
