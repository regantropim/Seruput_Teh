package id.seruput.core.data.product;

import id.seruput.api.data.product.Product;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.database.DataValidator;
import id.seruput.api.database.Database;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.util.logger.Logger;

import java.util.*;

import static id.seruput.core.util.Language.PRODUCT_NAME_UNIQUE;

public class ProductManagerImpl implements ProductManager {

    private final Database database;
    private final DataValidator<Product> validator;
    private final ProductRepository repository;
    private int highestId;
    private final Logger logger = Logger.getLogger(ProductManagerImpl.class);

    ProductManagerImpl(Database database) {
        this.database = database;
        this.validator = new ProductValidator();
        this.repository = new ProductRepository(database, new ProductOperationHelper());
        this.highestId = repository.findHighestId().map(ProductId::identity).orElse(0);
    }

    public static ProductManager build(Database database) {
        return new ProductManagerImpl(database);
    }

    @Override
    public Optional<Product> fetchProduct(ProductId id) {
        return repository.findById(id);
    }

    @Override
    public Product addProduct(String name, long price, String description) throws DataValidationException {
        ProductId id = ProductId.of(highestId + 1);
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
        highestId++;
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        try {
            repository.update(product);
        } catch (RuntimeException e) {
            logger.error("Failed to update product: " + product);
            throw new RuntimeException("Failed to update product", e);
        }
    }

    @Override
    public void removeProduct(Product product) {
        try {
            repository.delete(product);
        } catch (RuntimeException e) {
            logger.error("Failed to remove product: " + product);
            throw new RuntimeException("Failed to remove product", e);
        }
    }

    @Override
    public List<Product> products() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public String toString() {
        return "ProductManagerImpl{" +
                "database=" + database +
                ", validator=" + validator +
                ", repository=" + repository +
                ", logger=" + logger +
                '}';
    }

}
