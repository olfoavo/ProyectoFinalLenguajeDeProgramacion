package com.gustavo.sakila.dao;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    T post(T entity);
    Optional<T> getById(ID id);
    List<T> getAll();
    T put(T entity);
    boolean delete(ID id);
}
