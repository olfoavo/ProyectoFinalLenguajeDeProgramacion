package com.gustavo.sakila.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class DB {
    private static final HikariDataSource DS;

    static {
        String url  = System.getenv().getOrDefault("SAKILA_DB_URL",
                "jdbc:mysql://127.0.0.1:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        String user = System.getenv().getOrDefault("SAKILA_DB_USER", "root");
        String pass = System.getenv().getOrDefault("SAKILA_DB_PASS", "");

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(url);
        cfg.setUsername(user);
        cfg.setPassword(pass);
        cfg.setMaximumPoolSize(5);
        cfg.setPoolName("sakila-pool");

        DS = new HikariDataSource(cfg);
    }

    /** Úsalo en los repositorios. */
    public static DataSource ds() {
        return DS;
    }

    /** Por si necesitas un Connection directo. */
    public static Connection get() throws SQLException {
        return DS.getConnection();
    }
}