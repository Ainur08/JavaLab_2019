package dao;

public interface CrudDao<T> {
    void save(T model);
    void delete(T model);
}
