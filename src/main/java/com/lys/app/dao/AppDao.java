package com.lys.app.dao;

import com.lys.protobuf.SApp;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppDao
{
	void createTable();

	SApp get(String pkgName, String channel);

	List<SApp> getList();

	int insert(SApp app);

	int update(SApp app);

	int delete(String pkgName, String channel);
}