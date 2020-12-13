package com.lys.app.dao;

import com.lys.protobuf.SUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao
{
	void createTable();

	SUser getById(String id);

	SUser getByPhone(String phone);

	SUser get(String idOrPhone);

	List<SUser> getList(Integer userType);

	List<SUser> search(Integer userType, Boolean hasPhone, Long beginTime, Long endTime, List<String> keywords, Integer offset, Integer rows);

	int searchCount(Integer userType, Boolean hasPhone, Long beginTime, Long endTime, @Param("keywords") List<String> keywords);

	int insert(SUser user);

	int update(SUser user);

	int delete(String id);
}