package ru.multiclientchat.dao;

public interface CrudDao<T> {
    void save(T model);
}
