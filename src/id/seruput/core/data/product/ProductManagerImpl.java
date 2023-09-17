package id.seruput.core.data.product;

import id.seruput.api.data.product.Product;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.database.DataValidator;
import id.seruput.api.database.Database;
import id.seruput.api.exception.DataValidationException;

import java.util.*;

import static id.seruput.core.util.Language.PRODUCT_NAME_UNIQUE;

public class ProductManagerImpl implements ProductManager {

    private final Database database;
    private final DataValidator<Product> validator;
    private final ProductRepository repository;

    private final Map<ProductId, Product> products = new HashMap<>();

    ProductManagerImpl(Database database) {
        this.database = database;
        this.validator = new ProductValidator();
        this.repository = new ProductRepository(database, new ProductOperationHelper());
        loadProducts();
    }

    public static ProductManager build(Database database) {
        return new ProductManagerImpl(database);
    }

    private void loadProducts() {
        repository.findAll().forEach(product -> products.put(product.productId(), product));
    }

    @Override
    public Optional<Product> fetchProduct(ProductId id) {
        if (products.containsKey(id)) {
            return Optional.of(products.get(id));
        } else {
            return repository.findById(id);
        }
    }

    @Override
    public void addProduct(ProductId id, String name, long price, String description) throws DataValidationException {
        Product product = ProductImpl.builder()
                .productId(id)
                .productName(name)
                .productPrice(price)
                .productDescription(description)
                .build(validator);

        if (fetchProduct(id).isPresent()) {
            throw new DataValidationException("Product with id " + id + " already exists");
        }

        if (repository.findByName(name).isPresent()) {
            throw new DataValidationException(PRODUCT_NAME_UNIQUE);
        }

        repository.save(product);
    }

    @Override
    public List<Product> products() {
        return new ArrayList<>(products.values());
    }

}
