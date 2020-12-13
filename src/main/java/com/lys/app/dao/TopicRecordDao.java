package com.lys.app.dao;

import com.lys.protobuf.STopicRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRecordDao
{
	void createTable();

	STopicRecord get(String userId, String topicId);

	List<STopicRecord> getList(String userId, Integer type, Long time, Boolean prev, Integer pageSize);

	int insert(STopicRecord topicRecord);

	int update(STopicRecord topicRecord);

	int delete(String userId, String topicId);
}