package id.seruput.api.util.builder;

import id.seruput.api.database.DataValidator;
import id.seruput.api.database.Entity;
import id.seruput.api.exception.DataValidationException;

public interface EntityBuilder<T extends Entity<?>> extends Builder<T> {

    T build(DataValidator<T> validator) throws DataValidationException;

}