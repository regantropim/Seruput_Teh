package id.seruput.core.database;

import id.seruput.api.database.pool.ConnectionPool;
import id.seruput.api.database.Database;
import id.seruput.api.database.pool.PooledConnectionAbstract;
import id.seruput.api.exception.EmptyConnectionPoolException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPoolCore implements ConnectionPool {

    private final Vector<Connection> connections = new Vector<>();
    private final Database database;

    private ConnectionPoolCore(Database database, int size) throws SQLException {
        this.database = database;
        for (int i = 0; i < size; i++) {
            connections.add(database.connection());
        }
    }

    static ConnectionPool create(Database database, int size) throws SQLException {
        return new ConnectionPoolCore(database, size);
    }

    @Override
    public PooledConnectionAbstract connection() throws EmptyConnectionPoolException, SQLException, InterruptedException {
        return connection(1000);
    }

    @Override
    public synchronized PooledConnectionAbstract connection(long timeout) throws SQLException, InterruptedException, EmptyConnectionPoolException {
        long start = System.currentTimeMillis();

        while (connections.isEmpty()) {
            long remaining = timeout - (System.currentTimeMillis() - start);
            if (remaining > 0) {
                wait(remaining);
            } else {
                throw new EmptyConnectionPoolException("Connection pool is empty.");
            }

        }

        Connection connection = connections.remove(connections.size() - 1);

        return PooledConnectionAbstractCore.create(this, connection);
    }

    @Override
    public synchronized void returnToPool(Connection connection) throws SQLException {
        if (connection.isClosed()) {
            connections.add(database.connection());
        } else {
            connections.add(connection);
            notifyAll();
        }
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int available() {
        return 0;
    }

}
