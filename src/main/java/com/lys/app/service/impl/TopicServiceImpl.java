package com.lys.app.service.impl;

import com.lys.app.manager.OssUtils;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.service.TopicService;
import com.lys.protobuf.*;
import com.lys.utils.HttpUtils;
import com.lys.utils.LOG;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService
{
	@Value("${app.config.topic.api}")
	private String topicApi = null;

	@Override
	public SResponse_SearchTopics SearchTopics(SRequest_SearchTopics request)
	{
		String jsonStr = doPostSolr(SHandleId.SearchTopics, request.saveToStr());
		if (TextUtils.isEmpty(jsonStr))
			throw new ApiRuntimeException("参数错误");
		SResponse_SearchTopics response = SResponse_SearchTopics.load(jsonStr);
		for (STopic topic : response.topics)
		{
			topic.contentUrl = String.format("%s%d_%d/%s/%s/content.png", OssUtils.getHost(OssUtils.ZjykTopic), topic.phase, topic.subject, topic.id.substring(topic.id.length() - 2), topic.id);
			topic.analyUrl = String.format("%s%d_%d/%s/%s/parse.png", OssUtils.getHost(OssUtils.ZjykTopic), topic.phase, topic.subject, topic.id.substring(topic.id.length() - 2), topic.id);
		}
		return response;
	}

	public String doPostSolr(int handleId, String data)
	{
		SProtocol transSend = new SProtocol();
		transSend.handleId = handleId;
		transSend.data = data;
		LOG.v("上行Solr--------" + SHandleId.name(handleId) + "---------" + handleId + "---------");
//		LOGJson.log(transSend.saveToStr());
		String jsonStr = HttpUtils.doHttpPost(topicApi, transSend.saveToStr());
		if (!TextUtils.isEmpty(jsonStr))
		{
			SProtocol transRcv = null;
			try
			{
				LOG.v("下行Solr--------" + SHandleId.name(handleId) + "---------" + handleId + "---------");
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
}