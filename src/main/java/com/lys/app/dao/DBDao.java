package com.lys.app.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface DBDao
{
	void createDB();

	void useDB();
}