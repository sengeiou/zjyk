package com.lys.app.dao;

import com.lys.protobuf.SExamPoint;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamPointDao
{
	void createTable();

	SExamPoint get(String id);

	List<SExamPoint> getList();

	List<SExamPoint> search(List<String> keywords, Integer offset, Integer rows);

	int searchCount(@Param("keywords") List<String> keywords);

	int insert(SExamPoint point);

	int update(SExamPoint point);

	int delete(String id);
}