package com.lys.app.dao;

import com.lys.protobuf.SFriend;
import com.lys.protobuf.SUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendDao
{
	void createTable();

	SFriend get(String userId, String friendId);

	List<SUser> getList(String userId);

	int insert(SFriend friend);

	int update(SFriend friend);

	int delete(String userId, String friendId);

	int deleteByUserId(String userId);

	int deleteByFriendId(String friendId);
}