package id.seruput.api.data.transaction;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.database.Entity;

public interface TransactionDetail extends Entity<CompositeKey<TransactionId, ProductId>> {

    CompositeKey<TransactionId, ProductId> id();

    int quantity();

    TransactionDetail quantity(int quantity);

    default TransactionId transactionId() {
        return id().first();
    }

    default ProductId productId() {
        return id().second();
    }

}
