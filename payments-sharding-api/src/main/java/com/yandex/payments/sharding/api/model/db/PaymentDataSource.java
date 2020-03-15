package com.yandex.payments.sharding.api.model.db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentDataSource extends BasicDataSource {
    private final BasicDataSource dataSource;

    public PaymentDataSource(String url, String username, String password, String driverClassName,
                             int minIdle, int maxIdle, int maxOpenPreparedStatements) {
        this.dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
    }

    public PaymentDataSource(String url, String username, String password, String driverClassName) {
        this(url, username, password, driverClassName, 5, 10, 100);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
