package id.seruput.core.data.product;

import id.seruput.api.data.product.Product;
import id.seruput.api.database.DataValidator;
import id.seruput.api.exception.DataValidationException;

import static id.seruput.core.util.Language.PRODUCT_NAME_PRICE;

public class ProductValidator implements DataValidator<Product> {

    ProductValidator() {

    }

    @Override
    public void validate(Product data) throws DataValidationException {
        priceCheck(data.price());
    }

    private void priceCheck(long price) throws DataValidationException {
        if (price < 0) {
            throw new DataValidationException(PRODUCT_NAME_PRICE);
        }
    }

}
