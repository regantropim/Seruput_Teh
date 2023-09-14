package id.seruput.api.database;

import id.seruput.api.exception.DataValidationException;

public interface DataValidator<T extends Entity<?>> {

    void validate(T data) throws DataValidationException;

}
