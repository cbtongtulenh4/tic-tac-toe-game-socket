package javakit.minhphuc.dao.impl;

import javakit.minhphuc.config.DataConfig;
import javakit.minhphuc.dao.ICURDRepository;
import javakit.minhphuc.mapper.RowMapper;

import java.util.List;

public class CURDRepository<T> extends DataConfig<T> implements ICURDRepository<T> {

    @Override
    public void delete(Long id, String nameEntity) {

    }

    @Override
    public T findOne(Long id, String nameEntity) {
        String sql = "SELECT * FROM " + nameEntity + "WHERE id = ?";
        return null;
    }

    @Override
    public List<T> findAll(RowMapper<T> rowMapper, String nameEntity) {
        String sql = "SELECT * FROM " + nameEntity;
        List<T> result = query(sql, rowMapper);
        return result;
    }

    @Override
    public Long save(T object, String nameEntity, Boolean check) {
        String sql = "";
        if(check){
            sql = "UPDATE " + nameEntity +
                  "SET " + object.toString();
        }
        return null;
    }
}
