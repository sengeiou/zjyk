package com.lys.app.service;

import com.lys.protobuf.SRequest_SearchTopics;
import com.lys.protobuf.SResponse_SearchTopics;

public interface TopicService
{
	SResponse_SearchTopics SearchTopics(SRequest_SearchTopics request);
}