package com.lys.app.service.impl;

import com.lys.app.dao.FriendDao;
import com.lys.app.dao.UserDao;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.service.FriendService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FriendServiceImpl implements FriendService
{
	@Autowired
	private UserDao userDao = null;

	@Autowired
	private FriendDao friendDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		friendDao.createTable();
	}

	@Override
	public SResponse_GetFriendList GetFriendList(SRequest_GetFriendList request)
	{
		SResponse_GetFriendList response = new SResponse_GetFriendList();
		response.friends = friendDao.getList(request.userId);
		return response;
	}

	@Override
	public void addFriendImpl(String userId, String friendId)
	{
		if (userId.equals(friendId))
			return;
		if (friendDao.get(userId, friendId) == null)
		{
			SFriend friend = new SFriend();
			friend.userId = userId;
			friend.friendId = friendId;
			friendDao.insert(friend);
		}
	}

	@Override
	public SResponse_AddFriend AddFriend(SRequest_AddFriend request)
	{
		SResponse_AddFriend response = new SResponse_AddFriend();

		if (userDao.getById(request.userId) == null)
			throw new ApiRuntimeException("用户" + request.userId + "不存在");

		if (userDao.getById(request.friendId) == null)
			throw new ApiRuntimeException("用户" + request.friendId + "不存在");

		addFriendImpl(request.userId, request.friendId);
		addFriendImpl(request.friendId, request.userId);

		return response;
	}

	@Override
	public SResponse_DeleteFriend DeleteFriend(SRequest_DeleteFriend request)
	{
		SResponse_DeleteFriend response = new SResponse_DeleteFriend();
		friendDao.delete(request.userId, request.friendId);
		friendDao.delete(request.friendId, request.userId);
		return response;
	}

	@Override
	public SResponse_ModifyFriendGroup ModifyFriendGroup(SRequest_ModifyFriendGroup request)
	{
		SResponse_ModifyFriendGroup response = new SResponse_ModifyFriendGroup();
		SFriend friend = friendDao.get(request.userId, request.friendId);
		if (friend == null)
			throw new ApiRuntimeException("好友关系不存在");
		friend.group = request.group;
		friendDao.update(friend);
		return response;
	}
}