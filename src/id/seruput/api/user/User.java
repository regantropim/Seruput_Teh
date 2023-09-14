package id.seruput.api.user;

import id.seruput.api.database.Entity;
import id.seruput.api.util.FakeOption;

public interface User extends Entity<UserId> {

    UserId id();

    FakeOption<String> email();

    String username();

    String password();

    String phone();

    String address();

    UserGender gender();
    UserRole role();

    boolean isMale();

    boolean isFemale();


}
