package id.seruput.core.data.cart;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.cart.Cart;
import id.seruput.api.data.product.Product;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.DataValidator;
import id.seruput.api.exception.BuilderIncompleteException;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.util.builder.EntityBuilder;

import java.util.Optional;

import static id.seruput.core.util.Language.FIELDS_EMPTY;

public class CartImpl implements Cart {

    private final CompositeKey<UserId, ProductId> id;
    private int quantity;

    public CartImpl(CompositeKey<UserId, ProductId> id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    @Override
    public CompositeKey<UserId, ProductId> id() {
        return id;
    }

    @Override
    public int quantity() {
        return quantity;
    }

    @Override
    public Cart quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public Cart userId(UserId userId) {
        id().first(userId);
        return this;
    }

    @Override
    public CompositeKey<UserId, ProductId> primaryKey() {
        return id;
    }

    @Override
    public Optional<Product> product(ProductManager productManager) {
        return productManager.fetchProduct(id.second());
    }

    @Override
    public String toString() {
        return "CartImpl{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }

    public static CartBuilder builder() {
        return new CartBuilder();
    }

    public static class CartBuilder implements EntityBuilder<Cart> {

        private CompositeKey<UserId, ProductId> id;
        private int quantity;

        CartBuilder() {

        }

        public CartBuilder id(UserId userId, ProductId productId) {
            this.id = CompositeKey.of(userId, productId);
            return this;
        }

        public CartBuilder id(CompositeKey<UserId, ProductId> id) {
            this.id = id;
            return this;
        }

        public CartBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        public Cart build() {
            if (id != null && quantity != 0) {
                return new CartImpl(id, quantity);
            }
            throw new BuilderIncompleteException("CartImpl can't be built, data is not complete!");
        }

        @Override
        public Cart build(DataValidator<Cart> validator) throws DataValidationException {
            try {
                Cart cart = build();
                validator.validate(cart);
                return cart;
            } catch (BuilderIncompleteException e) {
                throw new DataValidationException(FIELDS_EMPTY);
            }
        }
    }

}
