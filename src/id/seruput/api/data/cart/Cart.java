package id.seruput.api.data.cart;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Entity;

public interface Cart extends Entity<CompositeKey<UserId, ProductId>> {

    CompositeKey<UserId, ProductId> id();

    int quantity();

    Cart quantity(int quantity);

    default UserId userId() {
        return id().first();
    }

    default Cart userId(UserId userId) {
        id().first(userId);
        return this;
    }

    default ProductId productId() {
        return id().second();
    }

    default Cart productId(ProductId productId) {
        id().second(productId);
        return this;
    }

}
