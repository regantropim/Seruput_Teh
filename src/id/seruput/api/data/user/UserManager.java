package id.seruput.api.data.user;

import id.seruput.api.database.repository.Repository;
import id.seruput.api.exception.CredentialErrorException;
import id.seruput.api.exception.DataValidationException;

public interface UserManager {

    User login(String username, String password) throws CredentialErrorException;

    User register(String email, String username, String password, String passwordConfirmation, String phone, String address, UserGender gender, UserRole role) throws DataValidationException;

    Repository<User, UserId> repository();

}
