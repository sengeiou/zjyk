package com.lys.app.dao;

import com.lys.protobuf.SExamRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRecordDao
{
	void createTable();

	SExamRecord get(String id);

	List<SExamRecord> getList();

	List<SExamRecord> getListByName(String name);

	List<SExamRecord> getListByPaperId(String paperId);

	List<SExamRecord> search(List<String> keywords, Integer offset, Integer rows);

	int searchCount(@Param("keywords") List<String> keywords);

	int insert(SExamRecord record);

	int update(SExamRecord record);

	int delete(String id);
}