package com.lys.app.dao;

import com.lys.protobuf.STaskGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskGroupDao
{
	void createTable();

	STaskGroup get(String id);

	List<STaskGroup> getList();

	int insert(STaskGroup taskGroup);

	int update(STaskGroup taskGroup);

	int delete(String id);
}