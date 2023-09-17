package id.seruput.api.data.product;

import id.seruput.api.exception.DataValidationException;

import java.util.List;
import java.util.Optional;

public interface ProductManager {

    Optional<Product> fetchProduct(ProductId id);

    void addProduct(ProductId id, String name, long price, String description) throws DataValidationException;

    List<Product> products();

}
