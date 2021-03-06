package org.rapidoid.sql;

/*
 * #%L
 * rapidoid-sql
 * %%
 * Copyright (C) 2014 - 2015 Nikolche Mihajlovski and contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.u.U;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Authors("Nikolche Mihajlovski")
@Since("4.1.0")
public class C3P0ConnectionPool implements ConnectionPool {

	private final ComboPooledDataSource pool = new ComboPooledDataSource();

	public C3P0ConnectionPool(String jdbcUrl, String driverClass, String username, String password) {
		init(jdbcUrl, driverClass, username, password);
	}

	public C3P0ConnectionPool(String jdbcUrl, String driverClass) {
		this(jdbcUrl, driverClass, null, null);
	}

	public C3P0ConnectionPool(SQLAPI sqlapi) {
		this(sqlapi.url(), sqlapi.driver(), sqlapi.user(), sqlapi.password());
		sqlapi.connectionPool(this);
	}

	private void init(String jdbcUrl, String driverClass, String username, String password) {
		try {
			pool.setDriverClass(driverClass);
		} catch (PropertyVetoException e) {
			throw U.rte("Cannot load JDBC driver!", e);
		}

		pool.setJdbcUrl(jdbcUrl);
		pool.setUser(username);
		pool.setPassword(password);

		pool.setMinPoolSize(5);
		pool.setAcquireIncrement(5);
		pool.setMaxPoolSize(20);
	}

	@Override
	public Connection getConnection(String jdbcUrl) throws SQLException {
		U.must(U.eq(jdbcUrl, pool.getJdbcUrl()), "The JDBC URLs don't match: '%s' and '%s'!", jdbcUrl,
				pool.getJdbcUrl());
		return pool.getConnection();
	}

	@Override
	public Connection getConnection(String jdbcUrl, String username, String password) throws SQLException {
		return pool.getConnection(username, password);
	}

	@Override
	public void releaseConnection(Connection connection) throws SQLException {
		connection.close();
	}

}
