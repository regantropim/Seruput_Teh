package id.seruput.core;

import id.seruput.api.SeruputTeh;
import id.seruput.api.database.Database;
import id.seruput.api.user.UserManager;
import id.seruput.core.database.DatabaseImpl;
import id.seruput.core.data.user.UserManagerImpl;

public class SeruputTehCore implements SeruputTeh {

    private final UserManager userManager;
    private final Database database;

    SeruputTehCore() {
        this.database = DatabaseImpl.builder()
                .database("seruput_teh")
                .host("127.0.0.1")
                .port((short) 3306)
                .username("root")
                .build("1234");

        this.userManager = UserManagerImpl.build(database);
    }

    public static SeruputTeh create() {
        return new SeruputTehCore();
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
