package id.seruput.core.data.user;

import id.seruput.api.database.DataValidator;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.data.user.User;
import id.seruput.api.util.FakeOption;

import static id.seruput.core.util.Language.*;

public class UserDataValidator implements DataValidator<User> {

    private static final String PHONE_NUMBER_PREFIX = "+62";
    private static final String EMAIL_DOMAIN = "@gmail.com";

    public void validate(User user) throws DataValidationException {
        validateEmail(user.email());
        validateUsername(user.username());
        validatePassword(user.password());
        validatePhoneNumber(user.phone());
        validateAddress(user.address());
    }

    /**
     * Zero-regex email validation
     *
     * <p>Valid email format:
     * <ul>
     *     <li>Only contains alphanumeric, dot, and underscore</li>
     *     <li>Must be ended with @gmail.com</li>
     *     <li>Must be at least 11 characters long (including @gmail.com suffix)</li>
     * </ul>
     *
     * @param emailOption {@link FakeOption} of {@link String} email to validate
     * @throws DataValidationException if email is invalid
     */
    private void validateEmail(FakeOption<String> emailOption) throws DataValidationException {
        String email = emailOption.orElseThrow(() -> new DataValidationException(FIELDS_EMPTY));
        char[] cEmail = email.toCharArray();
        if (cEmail.length <= EMAIL_DOMAIN.length()) {
            throw new DataValidationException("Email must have name and end with @gmail.com");
        }

        char[] cEmailName = new char[cEmail.length - EMAIL_DOMAIN.length()];
        System.arraycopy(cEmail, 0, cEmailName, 0, cEmailName.length);

        for (char c : cEmailName) {
            if (!Character.isLetterOrDigit(c) && c != '.' && c != '_') {
                throw new DataValidationException("Email must only contains alphanumeric, dot, and underscore");
            }
        }

        if (email.endsWith(EMAIL_DOMAIN)) {
            throw new DataValidationException(USER_EMAIL_MUST_END_WITH_GMAIL);
        }
    }

    private void validateUsername(String username) throws DataValidationException {
        if (!(username.length() >= 5 && username.length() <= 20)) {
            throw new DataValidationException(USER_USERNAME_LENGTH);
        }
    }

    private void validatePassword(String password) throws DataValidationException {
        if (!password.chars().allMatch(Character::isLetterOrDigit)) {
            throw new DataValidationException(USER_PASSWORD_ALPHANUMERIC);
        }
        if (password.length() < 5) {
            throw new DataValidationException(USER_PASSWORD_LENGTH);
        }
    }

    private void validatePhoneNumber(String phone) throws DataValidationException {
        if (phone.startsWith(PHONE_NUMBER_PREFIX)) {
            throw new DataValidationException(USER_PHONE_NUMBER_PREFIX);
        }

        if (phone.length() <= PHONE_NUMBER_PREFIX.length()) {
            throw new DataValidationException("Phone number must contain the number");
        }

        if (!phone.chars().skip(PHONE_NUMBER_PREFIX.length()).allMatch(Character::isDigit)) {
            throw new DataValidationException(USER_PHONE_NUMBER_NUMERIC);
        }

    }

    private void validateAddress(String address) throws DataValidationException {
        if (address.isEmpty()) {
            throw new DataValidationException(FIELDS_EMPTY);
        }
    }

}
