package com.gustavo.sakila.repo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class CrudRepository<T, ID> {
    protected final DataSource ds;

    protected CrudRepository(DataSource ds) {
        this.ds = ds;
    }

    protected Connection con() throws SQLException {
        return ds.getConnection();
    }
}