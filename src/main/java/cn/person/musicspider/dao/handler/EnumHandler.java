package cn.person.musicspider.dao.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import cn.person.musicspider.core.EnumType;

@MappedJdbcTypes(value=JdbcType.SMALLINT)
public class EnumHandler<T extends EnumType> extends BaseTypeHandler<T>{

	private Class<T> type;
	private final T[] enums;
	
	public EnumHandler(Class<T> type) {
		if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
		this.type = type;
		this.enums = type.getEnumConstants();
		if (this.enums == null) throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
	}
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int code = rs.getInt(columnName);
		if(rs.wasNull()){
			return null;
		}
		return getEnum(code);
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int code = rs.getInt(columnIndex);
		if(rs.wasNull()){
			return null;
		}
		return getEnum(code);
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int code = cs.getInt(columnIndex);
		if(cs.wasNull()){
			return null;
		}
		return getEnum(code);
	}

	private T getEnum(int code){
		for (T t : enums) {
			if(t.getCode()==code){
				return t;
			}
		}
		throw new IllegalArgumentException("Cannot convert " + code + " to "+type.getSimpleName());
	}
}
