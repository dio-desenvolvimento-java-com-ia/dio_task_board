package org.renigoms.persistence.config;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://172.17.0.2:3306/board", "root", "rngazrcb");
        connection.setAutoCommit(false);
        return connection;
    }
}
