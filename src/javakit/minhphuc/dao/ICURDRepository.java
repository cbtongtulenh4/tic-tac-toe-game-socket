package javakit.minhphuc.dao;

import javakit.minhphuc.mapper.RowMapper;

import java.util.List;

public interface ICURDRepository<T> {
    public void delete(Long id, String nameEntity);
    public T findOne(Long id, String nameEntity);
    public List<T> findAll(RowMapper<T> rowMapper, String nameEntity);
    public Long save(T object, String nameEntity, Boolean check);
}
