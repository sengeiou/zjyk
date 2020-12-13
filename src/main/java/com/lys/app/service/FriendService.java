package com.lys.app.service;

import com.lys.protobuf.*;

public interface FriendService
{
	SResponse_GetFriendList GetFriendList(SRequest_GetFriendList request);

	void addFriendImpl(String userId, String friendId);

	SResponse_AddFriend AddFriend(SRequest_AddFriend request);

	SResponse_DeleteFriend DeleteFriend(SRequest_DeleteFriend request);

	SResponse_ModifyFriendGroup ModifyFriendGroup(SRequest_ModifyFriendGroup request);
}