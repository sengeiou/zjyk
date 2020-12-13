package com.lys.app.dao;

import com.lys.protobuf.STeachPage;
import com.lys.protobuf.STeachRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachRecordDao
{
	void createTable();

	STeachRecord get(String teachId, String userId);

	List<STeachRecord> find(String teachId);

	List<STeachRecord> getList(String userId, Long fromTime, Long toTime);

	int insert(STeachRecord teachRecord);

	int update(STeachRecord teachRecord);

	int updateOverTime(String teachId, String userId, Long overTime);

	int updateConfirmMsg(String teachId, String userId, String confirmMsg);

	int updateQuestionStudent(String teachId, String userId, String questionMatch, String questionDiff, String questionGot, String questionQuality, String questionLike);

	int updateQuestionTeacher(String teachId, String questionHot, String questionMind, String questionLogic, String questionOther);

	int updateTeachPages(String teachId, List<STeachPage> teachPages);

	int delete(String teachId, String userId);
}