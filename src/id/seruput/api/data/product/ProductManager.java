package id.seruput.api.data.product;

import id.seruput.api.exception.DataValidationException;

import java.util.List;
import java.util.Optional;

public interface ProductManager {

    Optional<Product> fetchProduct(ProductId id);

    Product addProduct(String name, long price, String description) throws DataValidationException;

    void updateProduct(Product product);

    void removeProduct(Product product);

    List<Product> products();

}
