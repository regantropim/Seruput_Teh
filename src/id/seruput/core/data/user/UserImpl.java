package id.seruput.core.data.user;

import id.seruput.api.util.builder.EntityBuilder;
import id.seruput.api.database.DataValidator;
import id.seruput.api.exception.BuilderIncompleteException;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.data.user.User;
import id.seruput.api.data.user.UserGender;
import id.seruput.api.data.user.UserId;
import id.seruput.api.data.user.UserRole;
import id.seruput.api.util.FakeOption;

import static id.seruput.core.util.Language.FIELDS_EMPTY;

public final class UserImpl implements User {

    private UserId id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private UserGender gender;
    private UserRole role;

    public UserImpl(UserId id, String email, String name, String password, String phone, String address,
                    UserGender gender, UserRole role) {
        this.id = id;
        this.email = email;
        this.username = name;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.role = role;
    }

    UserImpl() {

    }

    @Override
    public UserId id() {
        return id;
    }

    @Override
    public User id(UserId id) {
        this.id = id;
        return this;
    }

    @Override
    public FakeOption<String> email() {
        return FakeOption.of(email);
    }

    @Override
    public User email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public User username(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public User password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String phone() {
        return phone;
    }

    @Override
    public User phone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public User address(String address) {
        this.address = address;
        return this;
    }

    @Override
    public UserGender gender() {
        return gender;
    }

    @Override
    public User gender(UserGender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public UserRole role() {
        return role;
    }

    @Override
    public User role(UserRole role) {
        this.role = role;
        return this;
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

    @Override
    public String toString() {
        return "UserImpl{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                '}';
    }

    public static class UserBuilder implements EntityBuilder<User> {

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

        @Override
        public User build() {
            if (id != null && username != null && password != null && phone != null && address != null && gender != null && role != null) {
                return new UserImpl(id, email, username, password, phone, address, gender, role);
            }
            throw new BuilderIncompleteException("UserImpl cannot be built, data is not complete!");
        }

        @Override
        public User build(DataValidator<User> validator) throws DataValidationException {
            try {
                User user = build();
                validator.validate(user);
                return user;
            } catch (BuilderIncompleteException e) {
                throw new DataValidationException(FIELDS_EMPTY);
            }
        }

    }

}
