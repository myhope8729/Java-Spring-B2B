package com.kpc.eos.core.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.web.context.ApplicationSetting;

public class LoginDataSource implements DataSource {
	private String dbType;
	
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		DriverManager.setLoginTimeout(seconds);
	}
	
	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		DriverManager.setLogWriter(out);
	}
	
	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return DriverManager.getLoginTimeout();
	}
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return DriverManager.getLogWriter();
	}
	
	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		String url = ApplicationSetting.getConfig("database.url");
		if (StringUtils.isNotBlank(dbType)) {
			url += "_" + dbType;
		}
//		System.out.println("++++++++++ db url="+url);
		String id = ApplicationSetting.getConfig("database.username");
		String pass = ApplicationSetting.getConfig("database.password");
		Connection conn = DriverManager.getConnection(url,id,pass);
		return conn;
	}
	
	/*
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	*/
}
