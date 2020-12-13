package com.lys.app.typehandler;

import com.lys.base.utils.JsonHelper;
import com.lys.protobuf.SExamPaperTopic;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MappedJdbcTypes(JdbcType.CLOB)
@MappedTypes(value = List.class)
public class ListSExamPaperTopicTypeHandler extends BaseTypeHandler<List<SExamPaperTopic>>
{
	@Override
	public void setNonNullParameter(PreparedStatement ps, int idx, List<SExamPaperTopic> data, JdbcType jdbcType) throws SQLException
	{
		ps.setString(idx, SExamPaperTopic.saveList(data).toString());
	}

	@Override
	public List<SExamPaperTopic> getNullableResult(ResultSet rs, String col) throws SQLException
	{
		String data = rs.getString(col);
		if (StringUtils.isEmpty(data))
			return null;
		return SExamPaperTopic.loadList(JsonHelper.getJSONArray(data));
	}

	@Override
	public List<SExamPaperTopic> getNullableResult(ResultSet rs, int idx) throws SQLException
	{
		String data = rs.getString(idx);
		if (StringUtils.isEmpty(data))
			return null;
		return SExamPaperTopic.loadList(JsonHelper.getJSONArray(data));
	}

	@Override
	public List<SExamPaperTopic> getNullableResult(CallableStatement cs, int idx) throws SQLException
	{
		String data = cs.getString(idx);
		if (StringUtils.isEmpty(data))
			return null;
		return SExamPaperTopic.loadList(JsonHelper.getJSONArray(data));
	}
}