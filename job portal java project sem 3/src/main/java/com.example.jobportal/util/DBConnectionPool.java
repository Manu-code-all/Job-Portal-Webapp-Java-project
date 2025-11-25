package com.example.jobportal.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionPool {
    private static HikariDataSource ds;

    static {
        try {
            Properties props = new Properties();
            InputStream in = DBConnectionPool.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(in);
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("jdbc.url"));
            config.setUsername(props.getProperty("jdbc.user"));
            config.setPassword(props.getProperty("jdbc.password"));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("jdbc.maxPoolSize", "10")));
            ds = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error setting up DB pool", e);
        }
    }

    private DBConnectionPool() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
