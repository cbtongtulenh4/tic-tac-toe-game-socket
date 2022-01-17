package javakit.minhphuc.config;

import javakit.minhphuc.mapper.RowMapper;
import java.util.List;

public interface RepositoryDataConfig<T> {
//    list - danh sách all object
    public List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);

//    Cập nhật - update data
    public void update(String sql, Object... params);
//    chèn - inset data
    public Long insert(String sql, Object... params);
//     Xóa - delete data
    public void delete(String sql, Object... params);
}
