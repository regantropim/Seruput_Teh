package id.seruput.core.data.cart;

import id.seruput.api.data.cart.Cart;
import id.seruput.api.database.DataValidator;
import id.seruput.api.exception.DataValidationException;

public class CartDataValidator implements DataValidator<Cart> {

    @Override
    public void validate(Cart data) throws DataValidationException {
        if (data.quantity() < 0) {
            throw new DataValidationException("Quantity must be greater than 0");
        }
    }

}
