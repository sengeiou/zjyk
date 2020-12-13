package com.lys.app.dao;

import com.lys.protobuf.SPTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao
{
	void createTable();

	SPTask get(String id);

	List<SPTask> find(String name, String group);

	List<SPTask> getList(String userId, String group, Integer overType, Integer sortType, Long createTime, Long overTime, Boolean prev, Integer pageSize);

	List<SPTask> search(Integer type, Integer jobType, Integer timesForWeb, Long beginTime, Long endTime, List<String> keywords, Integer offset, Integer rows);

	int searchCount(Integer type, Integer jobType, Integer timesForWeb, Long beginTime, Long endTime, @Param("keywords") List<String> keywords);

	int insert(SPTask task);

	int update(SPTask task);

	int delete(String id);

	int deleteByUserId(String userId);
}