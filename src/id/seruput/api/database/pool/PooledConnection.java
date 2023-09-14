package id.seruput.api.database.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PooledConnection extends AutoCloseable {

    PreparedStatement prepareStatement(String query) throws SQLException;

    Connection connection();

    boolean isClosed() throws SQLException;

    void close() throws SQLException;

}
