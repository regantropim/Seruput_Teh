package id.seruput.core.data.user;

import id.seruput.api.database.DataValidator;
import id.seruput.api.database.Database;
import id.seruput.api.database.repository.Repository;
import id.seruput.api.exception.CredentialErrorException;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.data.user.*;

import static id.seruput.core.util.Language.*;

public class UserManagerImpl implements UserManager {

    private final Database database;
    private final UserRepository userRepository;
    private final DataValidator<User> validator;
    private final int highestId;

    private UserManagerImpl(Database database) {
        this.database = database;
        this.userRepository = new UserRepository(database);
        this.validator = new UserDataValidator();
        this.highestId = userRepository.fetchHighestId(UserRole.USER).identity();
    }

    public static UserManager build(Database database) {
        return new UserManagerImpl(database);
    }

    @Override
    public User login(String username, String password) throws CredentialErrorException {
        if (username.isEmpty() || password.isEmpty()) {
            throw new CredentialErrorException(FIELDS_EMPTY);
        }
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new CredentialErrorException(USER_INVALID_CREDENTIALS));

        if (!user.password().equals(password)) {
            throw new CredentialErrorException(USER_INVALID_CREDENTIALS);
        }

        return user;
    }

    @Override
    public User register(String username, String password, String passwordConfirmation, String phone, String address,
                         UserGender gender, UserRole role) throws DataValidationException {
        if (passwordConfirmation.isEmpty()) {
            throw new DataValidationException(FIELDS_EMPTY);
        }

        if (!password.equals(passwordConfirmation)) {
            throw new DataValidationException(USER_PASSWORD_CONFIRMATION_NOT_MATCH);
        }

        if (userRepository.findUserByUsername(username).isPresent()) {
            throw new DataValidationException(USER_USERNAME_ALREADY_EXISTS);
        }

        UserId userId = generateUserId(role);
        User user = UserImpl.builder()
                .id(userId)
                .username(username)
                .password(password)
                .phone(phone)
                .gender(gender)
                .role(role)
                .build(validator);

        return userRepository.insert(user);
    }

    @Override
    public Repository<User, UserId> repository() {
        return userRepository;
    }

    private UserId generateUserId(UserRole role) {
        return UserId.of(highestId + 1, role);
    }

}
