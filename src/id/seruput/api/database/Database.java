package id.seruput.api.database;

import java.sql.Connection;

public interface Database {

    /**
     * Get database connection.
     *
     * @return Database connection.
     */
    Connection connection();

}
