package id.seruput.api.database.pool;

import id.seruput.api.exception.EmptyConnectionPoolException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    PooledConnectionAbstract connection() throws EmptyConnectionPoolException, SQLException, InterruptedException;

    PooledConnectionAbstract connection(long timeout) throws SQLException, InterruptedException, EmptyConnectionPoolException;

    void returnToPool(Connection connection) throws SQLException;

    boolean isFree();

    int size();

    int available();

}
