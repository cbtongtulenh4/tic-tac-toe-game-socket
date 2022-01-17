package javakit.minhphuc.config;

import javakit.minhphuc.mapper.RowMapper;

import java.sql.Timestamp;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DataConfig<T> implements RepositoryDataConfig<T>{
    //
    private final ResourceBundle bundle = ResourceBundle.getBundle("resources/db");

    private final String DRIVER_NAME = bundle.getString("driverName");
    private final String URL = bundle.getString("url");
    private final String USER = bundle.getString("user");
    private final String PASSWORD = bundle.getString("password");

    public Connection getConnection(){
        try {
            Class.forName(DRIVER_NAME);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
        List<T> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setParameters(statement, parameters);
            result = statement.executeQuery();
            while (result.next()){
                results.add(rowMapper.mapRow(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            closeAll(connection, statement, result);
        }
        return results;
    }

    @Override
    public void update(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            // Set Funny
            connection.setAutoCommit(false);
            setParameters(statement, params);
            statement.executeUpdate();
            connection.commit();
        }catch (SQLException e){
            rollBack(connection);
            e.printStackTrace();
        }finally {
            closeAll(connection, statement);
        }
    }

    @Override
    public Long insert(String sql, Object... params) {
        Long id = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try{
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameters(statement, params);
            statement.executeUpdate();
            result = statement.getGeneratedKeys();
            while (result.next()){
                id = result.getLong(1);
            }
            connection.commit();
            return id;
        }catch (SQLException e){
            rollBack(connection);
            e.printStackTrace();
        }finally {
            closeAll(connection, statement, result);
        }
        return null;
    }

    @Override
    public void delete(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setParameters(statement, params);
            statement.executeQuery();
        }catch(SQLException e){
            rollBack(connection);
            e.printStackTrace();
        }finally {
            closeAll(connection, statement);
        }

    }

    protected void setParameters(PreparedStatement statement, Object... parameters){
        try {
            int index = 0;
            for (Object param: parameters) {
                index++;
                if(param instanceof Long){
                    statement.setLong(index, (Long) param);
                }else if(param instanceof Integer){
                    statement.setLong(index, (Integer) param);
                }else if(param instanceof String){
                    statement.setString(index, (String) param);
                }else if(param instanceof Timestamp){
                    statement.setTimestamp(index, (Timestamp) param);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeAll(Object... parameters){
        try {
            for (Object param : parameters) {
                if (param instanceof PreparedStatement) {
                    ((PreparedStatement) param).close();
                }else if(param instanceof Connection){
                    ((Connection) param).close();
                }else if(param instanceof ResultSet){
                    ((ResultSet) param).close();
                }
            }
        }
        catch (SQLException e) {
             e.printStackTrace();
        }
    }

    protected void rollBack(Connection connection){
        if (connection != null){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }



}
