package id.seruput.core.database;

import id.seruput.api.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseImpl implements Database {

    private final String username;
    private final String database;
    private final String host;
    private final short port;
    private final Connection connection;

    DatabaseImpl(String username, String database, String host, short port, String password) throws SQLException {
        this.username = username;
        this.database = database;
        this.host = host;
        this.port = port;
        this.connection = setup(password);
    }

    private Connection setup(String password) throws SQLException {
        password = password == null ? "" : "&password=" + password;
        return DriverManager.getConnection(String
                .format("jdbc:mysql://%s:%d/%s?user=%s%s", host, port, database, username, password));
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

    public Connection connection() {
        return connection;
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
         * @return A new Database object, or null if fail.
         */
        public Database build(String password) {
            if (database != null && host != null && port != null) {
                try {
                    return new DatabaseImpl(username, database, host, port, password);
                } catch (SQLException e) {
                    return null;
                }
            }
            return null;
        }

        public Database build() {
            return build(null);
        }

    }

}
