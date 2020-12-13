package com.lys.app.service;

import com.lys.protobuf.*;

public interface TopicRecordService
{
	SResponse_TopicRecordGetList TopicRecordGetList(SRequest_TopicRecordGetList request);

	SResponse_TopicRecordGet TopicRecordGet(SRequest_TopicRecordGet request);

	SResponse_TopicRecordSetFav TopicRecordSetFav(SRequest_TopicRecordSetFav request);

	SResponse_TopicRecordSetResult TopicRecordSetResult(SRequest_TopicRecordSetResult request);

	SResponse_TopicRecordDelete TopicRecordDelete(SRequest_TopicRecordDelete request);
}