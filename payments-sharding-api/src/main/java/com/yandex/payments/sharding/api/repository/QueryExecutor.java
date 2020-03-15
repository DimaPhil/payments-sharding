package com.yandex.payments.sharding.api.repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

public class QueryExecutor {
    public static <T> int executeUpdate(DataSource dataSource,
                                        String query) throws SQLException {
        Connection connection = dataSource.getConnection();
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }

    public static <T> T executeQuery(DataSource dataSource,
                                     String query,
                                     Function<ResultSet, T> parseAction) throws SQLException {
        Connection connection = dataSource.getConnection();
        try (Statement stmt = connection.createStatement()) {
            return parseAction.apply(stmt.executeQuery(query));
        }
    }
}
