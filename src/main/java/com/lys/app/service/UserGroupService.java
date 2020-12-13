package com.lys.app.service;

import com.lys.protobuf.*;

public interface UserGroupService
{
	SResponse_UserGroupGetAll UserGroupGetAll(SRequest_UserGroupGetAll request);

	SResponse_UserGroupAddModify UserGroupAddModify(SRequest_UserGroupAddModify request);

	SResponse_UserGroupDelete UserGroupDelete(SRequest_UserGroupDelete request);
}