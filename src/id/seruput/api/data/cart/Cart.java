package id.seruput.api.data.cart;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Entity;

public interface Cart extends Entity<CompositeKey<UserId, ProductId>> {

    CompositeKey<UserId, ProductId> id();

    int quantity();

    default UserId userId() {
        return id().first();
    }

    default ProductId productId() {
        return id().second();
    }

}
