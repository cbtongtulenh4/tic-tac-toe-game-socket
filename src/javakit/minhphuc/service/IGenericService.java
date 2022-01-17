package javakit.minhphuc.service;

import javakit.minhphuc.mapper.RowMapper;

import java.util.*;

public interface IGenericService<T> {
    public void delete(Long[] ids, String nameEntity);
    public T findOne(Long id, String nameEntity);
    public List<T> findAll(RowMapper<T> rowMapper, String nameEntity);
    public Long save(T object, String nameEntity);


}
