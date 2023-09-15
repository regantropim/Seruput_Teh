package id.seruput.core;

import id.seruput.api.SeruputTeh;
import id.seruput.api.database.Database;
import id.seruput.api.data.user.UserManager;
import id.seruput.core.database.DatabaseImpl;
import id.seruput.core.data.user.UserManagerImpl;

public class SeruputTehCore implements SeruputTeh {

    private final UserManager userManager;
    private final Database database;

    SeruputTehCore(String databasePassword) {
        this.database = DatabaseImpl.builder()
                .database("seruput")
                .host("127.0.0.1")
                .port((short) 3306)
                .username("root")
                .build(databasePassword);

        if (this.database == null) {
            throw new RuntimeException("Database connection failed");
        }

        this.userManager = UserManagerImpl.build(database);
    }

    public static SeruputTeh create(String databasePassword) {
        return new SeruputTehCore(databasePassword);
    }

    @Override
    public UserManager userManager() {
        return userManager;
    }

    @Override
    public Database database() {
        return database;
    }

}
