package com.lys.app.manager;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.lys.base.utils.JsonHelper;
import com.lys.utils.LOG;

public class SendSms
{
	// 全局KEY
	private static final String accessKeyId = "LTAI4FfzrNam3xMjGPY63JvN";
	private static final String accessKeySecret = "0XUdaR78AHogI5ELahc0viws3FNkV3";

	public static final String AppMath = "振宇数学";

	public static final String TemplateLogin = "SMS_187210862";
	public static final String TemplateModifyPsw = "SMS_187210859";
	public static final String TemplateReg = "SMS_187210860";

	public static boolean send(String phone, String app, String template, String code)
	{
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysDomain("dysmsapi.aliyuncs.com");
		request.setSysVersion("2017-05-25");
		request.setSysAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");
		request.putQueryParameter("PhoneNumbers", phone);
		request.putQueryParameter("SignName", app);
		request.putQueryParameter("TemplateCode", template);
		request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
		try
		{
			CommonResponse response = client.getCommonResponse(request);
			LOG.v(response.getData());
			JSONObject obj = JsonHelper.getJSONObject(response.getData());
			if (obj.getString("Code").equals("OK"))
			{
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
