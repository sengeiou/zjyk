package com.lys.app.dao;

import com.lys.protobuf.SEvent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDao
{
	void createTable();

	List<SEvent> getList(String userId, List<String> actions, List<String> targets);

	List<SEvent> search(String userId, String action, String target, Long beginTime, Long endTime, List<String> keywords, Integer offset, Integer rows);

	int searchCount(String userId, String action, String target, Long beginTime, Long endTime, @Param("keywords") List<String> keywords);

	int insert(SEvent event);
}