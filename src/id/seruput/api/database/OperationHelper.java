package id.seruput.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface OperationHelper<T extends Entity<K>, K> {

    T update(ResultSet rs, T t) throws SQLException;

    T deserialize(ResultSet resultSet) throws SQLException;

    PreparedStatement selectAll(Connection connection) throws SQLException;

    PreparedStatement selectStatement(Connection connection) throws SQLException;

    PreparedStatement insertStatement(Connection connection) throws SQLException;

    PreparedStatement deleteStatement(Connection connection) throws SQLException;

    PreparedStatement updateStatement(Connection connection) throws SQLException;

    PreparedStatement updateOrInsertStatement(Connection connection) throws SQLException;

    void setSelectPreparedStatement(PreparedStatement preparedStatement, T t) throws SQLException;
    
    void setSelectPreparedStatement(PreparedStatement preparedStatement, K id) throws SQLException;

    void setInsertPreparedStatement(PreparedStatement preparedStatement, T t) throws SQLException;

    void setDeletePreparedStatement(PreparedStatement preparedStatement, T t) throws SQLException;

    void setDeletePreparedStatement(PreparedStatement preparedStatement, K id) throws SQLException;

    void setUpdatePreparedStatement(PreparedStatement preparedStatement, T t) throws SQLException;

    void setUpdateOrInsertPreparedStatement(PreparedStatement preparedStatement, T t) throws SQLException;

}