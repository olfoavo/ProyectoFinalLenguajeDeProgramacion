package com.gustavo.sakila.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public final class DB {
    private static final HikariDataSource DS;
    static {
        HikariConfig cfg = new HikariConfig();
        // Ajusta si cambiaste host/puerto/DB
        cfg.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true");
        cfg.setUsername("sakila_user");
        cfg.setPassword("Sakila2025"); // la que creaste
        cfg.setMaximumPoolSize(10);
        cfg.setMinimumIdle(2);
        cfg.setIdleTimeout(30_000);
        cfg.setConnectionTimeout(15_000);
        cfg.setMaxLifetime(30 * 60_000);
        DS = new HikariDataSource(cfg);
    }
    private DB() {}
    public static DataSource get() { return DS; }
}
