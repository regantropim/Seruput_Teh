package id.seruput.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface OperationHelper<T extends Entity<K>, K> {

    T deserialize(ResultSet resultSet) throws SQLException;

    PreparedStatement selectAll(Connection connection) throws SQLException;

    PreparedStatement selectStatement(Connection connection) throws SQLException;

    PreparedStatement insertStatement(Connection connection) throws SQLException;

    PreparedStatement deleteStatement(Connection connection) throws SQLException;

    PreparedStatement updateStatement(Connection connection) throws SQLException;

    void setSelectPreparedStatement(PreparedStatement preparedStatement, T t) throws SQLException;
    
    void setSelectPreparedStatement(PreparedStatement preparedStatement, K id) throws SQLException;

    void setInsertPreparedStatement(PreparedStatement preparedStatement, T user) throws SQLException;

    void setDeletePreparedStatement(PreparedStatement preparedStatement, T user) throws SQLException;

    void setDeletePreparedStatement(PreparedStatement preparedStatement, K id) throws SQLException;

    void setUpdatePreparedStatement(PreparedStatement preparedStatement, T user) throws SQLException;

}