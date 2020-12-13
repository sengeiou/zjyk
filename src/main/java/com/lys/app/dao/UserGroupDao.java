package com.lys.app.dao;

import com.lys.protobuf.SUserGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupDao
{
	void createTable();

	SUserGroup get(String id);

	List<SUserGroup> getList();

	int insert(SUserGroup live);

	int update(SUserGroup live);

	int delete(String id);
}