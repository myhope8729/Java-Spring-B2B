package com.kpc.eos.core.dao.mapping;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class DayTypeHandler implements TypeHandler {
	private String format(Date fromDate) {
		if (fromDate == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy.MM.dd").format(fromDate);
	}

	private Date convert(String dateString) {
		if (dateString == null) {
			return null;
		}
		try {
			return new SimpleDateFormat("yyyy.MM.dd").parse(dateString);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public boolean equals(Object arg0, String arg1) {
		return false;
	}

	@Override
	public Object getResult(ResultSet rs, String col) throws SQLException {
		return convert(rs.getString(col));
	}

	@Override
	public Object getResult(ResultSet rs, int index) throws SQLException {
		return convert(rs.getString(index));
	}

	@Override
	public Object getResult(CallableStatement cs, int index) throws SQLException {
		return convert(cs.getString(index));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, Object arg2, String arg3) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object valueOf(String arg0) {
		return convert(arg0);
	}

}
