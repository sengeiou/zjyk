package com.lys.app.dao;

import com.lys.protobuf.STeach;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachDao
{
	void createTable();

	STeach get(STeach teach);

	List<STeach> getList(String teacherId, Integer year, Integer month, Integer day);

	int insert(STeach teach);

	int update(STeach teach);

	int delete(STeach teach);
}