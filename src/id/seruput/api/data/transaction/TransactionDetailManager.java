package id.seruput.api.data.transaction;

import id.seruput.api.data.product.Product;

import java.util.List;

public interface TransactionDetailManager {
    List<TransactionDetail> fetchDetailByTransactionId(TransactionId transactionId);

    void addItem(TransactionId transactionId, Product product, int quantity);
}
