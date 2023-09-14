package id.seruput.core.data.user;

import id.seruput.api.database.DataValidator;
import id.seruput.api.exception.BuilderIncompleteException;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.user.User;
import id.seruput.api.user.UserGender;
import id.seruput.api.user.UserId;
import id.seruput.api.user.UserRole;
import id.seruput.api.util.FakeOption;

import static id.seruput.core.util.Language.FIELDS_EMPTY;

public final class UserImpl implements User {

    private final UserId id;
    private final String username;
    private final String email;
    private final String password;
    private final String phone;
    private final String address;
    private final UserGender gender;
    private final UserRole role;

    public UserImpl(UserId id, String username, String password, String phone, String address, UserGender gender, UserRole role) {
        this.id = id;
        this.email = null;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.role = role;
    }

    public UserImpl(UserId id, String email, String username, String password, String phone, String address, UserGender gender, UserRole role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.role = role;
    }

    @Override
    public UserId id() {
        return id;
    }

    @Override
    public FakeOption<String> email() {
        return FakeOption.of(email);
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String phone() {
        return phone;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public UserGender gender() {
        return gender;
    }

    @Override
    public UserRole role() {
        return role;
    }

    @Override
    public boolean isMale() {
        return gender == UserGender.MALE;
    }

    @Override
    public boolean isFemale() {
        return gender == UserGender.FEMALE;
    }

    @Override
    public UserId primaryKey() {
        return id;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        private UserId id;
        private String email = null;
        private String username;
        private String password;
        private String phone;
        private String address;
        private UserGender gender;
        private UserRole role;

        private UserBuilder() {

        }

        public UserBuilder id(UserId id) {
            this.id = id;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder gender(UserGender gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder role(UserRole role) {
            this.role = role;
            return this;
        }

        public UserImpl build() {
            if (id != null && username != null && password != null && phone != null && address != null && gender != null && role != null) {
                return new UserImpl(id, username, email, password, phone, address, gender, role);
            }
            throw new BuilderIncompleteException("UserImpl cannot be built, data is not complete!");
        }

        public UserImpl build(DataValidator<User> validator) throws DataValidationException {
            try {
                UserImpl user = build();
                validator.validate(user);
                return user;
            } catch (BuilderIncompleteException e) {
                throw new DataValidationException(FIELDS_EMPTY);
            }
        }

    }

}
