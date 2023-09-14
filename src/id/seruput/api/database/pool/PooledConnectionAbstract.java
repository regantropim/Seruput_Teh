package id.seruput.api.database.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class PooledConnectionAbstract implements PooledConnection {

    protected final ConnectionPool connectionPool;
    protected final Connection connection;

    public PooledConnectionAbstract(ConnectionPool connectionPool, Connection connection) {
        this.connectionPool = connectionPool;
        this.connection = connection;
    }

    @Override
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    @Override
    public boolean isClosed() throws SQLException {
        return connection.isClosed();
    }

    @Override
    public void close() throws SQLException {
        connectionPool.returnToPool(connection);
    }

    @Override
    public Connection connection() {
        return connection;
    }

}
