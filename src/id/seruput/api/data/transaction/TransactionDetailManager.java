package id.seruput.api.data.transaction;

import id.seruput.api.data.product.Product;

public interface TransactionDetailManager {
    void addItem(TransactionId transactionId, Product product, int quantity);
}
