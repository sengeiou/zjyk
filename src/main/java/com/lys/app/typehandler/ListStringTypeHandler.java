package com.lys.app.typehandler;

import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.JsonHelper;
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
public class ListStringTypeHandler extends BaseTypeHandler<List<String>>
{
	@Override
	public void setNonNullParameter(PreparedStatement ps, int idx, List<String> data, JdbcType jdbcType) throws SQLException
	{
		ps.setString(idx, AppDataTool.saveStringList(data).toString());
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, String col) throws SQLException
	{
		String data = rs.getString(col);
		if (StringUtils.isEmpty(data))
			return null;
		return AppDataTool.loadStringList(JsonHelper.getJSONArray(data));
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, int idx) throws SQLException
	{
		String data = rs.getString(idx);
		if (StringUtils.isEmpty(data))
			return null;
		return AppDataTool.loadStringList(JsonHelper.getJSONArray(data));
	}

	@Override
	public List<String> getNullableResult(CallableStatement cs, int idx) throws SQLException
	{
		String data = cs.getString(idx);
		if (StringUtils.isEmpty(data))
			return null;
		return AppDataTool.loadStringList(JsonHelper.getJSONArray(data));
	}
}