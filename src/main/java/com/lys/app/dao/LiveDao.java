package com.lys.app.dao;

import com.lys.protobuf.SLiveTask;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveDao
{
	void createTable();

	SLiveTask get(String id);

	List<SLiveTask> find(String userId);

	List<SLiveTask> getList();

	int insert(SLiveTask live);

	int update(SLiveTask live);

	int delete(String id);
}