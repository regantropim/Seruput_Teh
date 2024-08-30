package id.seruput.api.database;

import id.seruput.api.database.pool.ConnectionPool;
import id.seruput.api.database.pool.PooledConnection;
import id.seruput.api.exception.EmptyConnectionPoolException;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {

    /**
     * Create a new connection to the database.
     *
     * @return Database connection.
     */
    Connection connection() throws SQLException;

    ConnectionPool connectionPool();

    default PooledConnection fromPool() throws SQLException, InterruptedException, EmptyConnectionPoolException {
        return connectionPool().connection();
    }

}
