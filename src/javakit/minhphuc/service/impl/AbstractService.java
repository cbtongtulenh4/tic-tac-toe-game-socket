package javakit.minhphuc.service.impl;

import javakit.minhphuc.dao.ICURDRepository;
import javakit.minhphuc.mapper.RowMapper;
import javakit.minhphuc.service.IGenericService;

import java.util.List;

public class AbstractService<T> implements IGenericService<T> {

    private ICURDRepository<T> curdRepository;

    @Override
    public void delete(Long[] ids, String nameEntity) {
        for (Long id : ids){
            curdRepository.delete(id, nameEntity);
        }
    }

    @Override
    public T findOne(Long id, String nameEntity) {
        return null;
    }

    @Override
    public List<T> findAll(RowMapper<T> rowMapper, String nameEntity) {
        return null;
    }

    @Override
    public Long save(T object, String nameEntity) {
        return null;
    }
}
