package id.seruput.core.database;

import id.seruput.api.database.pool.ConnectionPool;
import id.seruput.api.database.pool.PooledConnectionAbstract;

import java.sql.Connection;

public class PooledConnectionCore extends PooledConnectionAbstract {

    public PooledConnectionCore(ConnectionPool connectionPool, Connection connection) {
        super(connectionPool, connection);
    }

    static PooledConnectionAbstract create(ConnectionPool connectionPool, Connection connection) {
        return new PooledConnectionCore(connectionPool, connection);
    }

}
