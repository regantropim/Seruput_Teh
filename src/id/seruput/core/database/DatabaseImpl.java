package id.seruput.core.database;

import id.seruput.api.database.Database;
import id.seruput.api.database.pool.ConnectionPool;
import id.seruput.api.util.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseImpl implements Database {

    private static final Logger LOGGER = Logger.getLogger(DatabaseImpl.class);

    private final String username;
    private final String password;
    private final String database;
    private final String host;
    private final short port;
    private final ConnectionPool connectionPool;

    DatabaseImpl(String username, String database, String host, short port, String password) throws SQLException {
        this.username = username;
        this.password = password;
        this.database = database;
        this.host = host;
        this.port = port;
        this.connectionPool = ConnectionPoolCore.create(this, 3);
    }

    public static DatabaseBuilder builder() {
        return new DatabaseBuilder();
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getHost() {
        return host;
    }

    public short getPort() {
        return port;
    }

    public Connection connection() throws SQLException {
        Connection connection =  DriverManager.getConnection(String
                .format("jdbc:mysql://%s:%d/%s", host, port, database), username, password);

        LOGGER.info("Connection to database established " + connection.toString());
        return connection;
    }

    @Override
    public ConnectionPool connectionPool() {
        return connectionPool;
    }

    public static class DatabaseBuilder {

        private String database;
        private String username;
        private String host;
        private Short port;

        DatabaseBuilder() {

        }

        public DatabaseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public DatabaseBuilder database(String database) {
            this.database = database;
            return this;
        }

        public DatabaseBuilder host(String host) {
            this.host = host;
            return this;
        }

        public DatabaseBuilder port(Short port) {
            this.port = port;
            return this;
        }

        /**
         * Build a new Database object.
         *
         * @param password The password to connect to the database, or null if no
         * @return A new Database object, or null if failed.
         */
        public Database build(String password) {
            if (database != null && host != null && port != null) {
                try {
                    return new DatabaseImpl(username, database, host, port, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
           throw new RuntimeException("Database cannot be built, data is not complete!");
        }

        public Database build() {
            return build(null);
        }

    }

}
