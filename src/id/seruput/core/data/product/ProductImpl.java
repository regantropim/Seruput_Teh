package id.seruput.core.data.product;

import id.seruput.api.data.product.Product;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.database.DataValidator;
import id.seruput.api.exception.BuilderIncompleteException;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.util.builder.EntityBuilder;

import static id.seruput.core.util.Language.FIELDS_EMPTY;

public class ProductImpl implements Product {

    private final ProductId productId;
    private String productName;
    private long productPrice;
    private String productDescription;

    public ProductImpl(ProductId productId, String productName, long productPrice, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }

    @Override
    public ProductId productId() {
        return productId;
    }

    @Override
    public String name() {
        return productName;
    }

    @Override
    public Product name(String name) {
        this.productName = name;
        return this;
    }

    @Override
    public long price() {
        return productPrice;
    }

    @Override
    public Product price(long price) {
        this.productPrice = price;
        return this;
    }

    @Override
    public String description() {
        return productDescription;
    }

    @Override
    public Product description(String description) {
        this.productDescription = description;
        return this;
    }

    @Override
    public ProductId primaryKey() {
        return productId;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder implements EntityBuilder<Product> {

        private ProductId productId;
        private String productName;
        private long productPrice;
        private String productDescription;

        ProductBuilder() {

        }

        public ProductBuilder productId(ProductId productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductBuilder productPrice(long productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public ProductBuilder productDescription(String productDescription) {
            this.productDescription = productDescription;
            return this;
        }

        @Override
        public Product build() {
            if (productId != null && productName != null && productPrice != 0 && productDescription != null) {
                return new ProductImpl(productId, productName, productPrice, productDescription);
            }
            throw new BuilderIncompleteException("ProductBuilder is incomplete");
        }

        @Override
        public Product build(DataValidator<Product> validator) throws DataValidationException {
            try {
                Product product = build();
                validator.validate(product);
                return product;
            } catch (BuilderIncompleteException e) {
                throw new DataValidationException(FIELDS_EMPTY);
            }

        }

    }
}
