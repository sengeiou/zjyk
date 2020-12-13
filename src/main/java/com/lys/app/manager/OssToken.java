package com.lys.app.manager;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.lys.protobuf.SResponse_GetOssToken;
import com.lys.utils.LOG;

public class OssToken
{
	private static final String accessKeyId = "LTAI4FcVX8N8rJ1AdVxMJDqd";
	private static final String accessKeySecret = "dq3jvO1l49EpBxXYhUKGkRjVjdZy3p";

	private static final String REGION_CN_HANGZHOU = "cn-hangzhou";
	private static final String STS_API_VERSION = "2015-04-01";

	private static final String policy = "{\"Statement\":[{\"Action\":[\"oss:GetObject\",\"oss:PutObject\",\"oss:DeleteObject\",\"oss:ListParts\",\"oss:AbortMultipartUpload\",\"oss:ListObjects\"],\"Effect\":\"Allow\",\"Resource\":[\"acs:oss:*:*:zjyk-file/*\",\"acs:oss:*:*:zjyk-file\"]}],\"Version\":\"1\"}";

	public static SResponse_GetOssToken requestToken(String userId)
	{
		try
		{
			IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion(STS_API_VERSION);
			request.setMethod(MethodType.POST);
			request.setProtocol(ProtocolType.HTTPS);

			request.setRoleArn("acs:ram::1749820499760098:role/aliyunosstokengeneratorrole");
			request.setRoleSessionName(userId);
			request.setPolicy(policy);
			request.setDurationSeconds(3600L);

			AssumeRoleResponse response = client.getAcsResponse(request);

			LOG.v("AccessKeyId : " + response.getCredentials().getAccessKeyId());
			LOG.v("AccessKeySecret" + response.getCredentials().getAccessKeySecret());
			LOG.v("SecurityToken : " + response.getCredentials().getSecurityToken());
			LOG.v("Expiration : " + response.getCredentials().getExpiration());

			SResponse_GetOssToken responseData = new SResponse_GetOssToken();
			responseData.accessKeyId = response.getCredentials().getAccessKeyId();
			responseData.accessKeySecret = response.getCredentials().getAccessKeySecret();
			responseData.securityToken = response.getCredentials().getSecurityToken();
			responseData.expiration = response.getCredentials().getExpiration();
			return responseData;
		}
		catch (ClientException e)
		{
			e.printStackTrace();
			LOG.v("ErrorCode : " + e.getErrCode());
			LOG.v("ErrorMessage : " + e.getErrMsg());
		}
		return null;
	}

}
