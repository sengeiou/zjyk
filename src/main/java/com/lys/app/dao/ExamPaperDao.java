package com.lys.app.dao;

import com.lys.protobuf.SExamPaper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamPaperDao
{
	void createTable();

	SExamPaper get(String id);

	List<SExamPaper> getList();

	List<SExamPaper> search(List<String> keywords, Integer offset, Integer rows);

	int searchCount(@Param("keywords") List<String> keywords);

	int insert(SExamPaper paper);

	int update(SExamPaper paper);

	int delete(String id);
}