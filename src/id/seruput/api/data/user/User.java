package id.seruput.api.data.user;

import id.seruput.api.database.Entity;
import id.seruput.api.util.FakeOption;

public interface User extends Entity<UserId> {

    UserId id();

    User id(UserId id);

    FakeOption<String> email();

    User email(String email);

    String username();

    User username(String username);

    String password();

    User password(String password);

    String phone();

    User phone(String phone);

    String address();

    User address(String address);

    UserGender gender();

    User gender(UserGender gender);

    UserRole role();

    User role(UserRole role);

    boolean isMale();

    boolean isFemale();


}
