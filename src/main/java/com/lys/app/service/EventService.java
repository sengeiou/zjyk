package com.lys.app.service;

import com.lys.protobuf.SRequest_AddEvent;
import com.lys.protobuf.SRequest_GetEventList;
import com.lys.protobuf.SResponse_AddEvent;
import com.lys.protobuf.SResponse_GetEventList;

public interface EventService
{
	SResponse_AddEvent AddEvent(SRequest_AddEvent request);

	SResponse_GetEventList GetEventList(SRequest_GetEventList request);
}