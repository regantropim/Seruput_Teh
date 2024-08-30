package id.seruput.core.data.transaction.detail;

import id.seruput.api.data.product.Product;
import id.seruput.api.data.transaction.TransactionDetail;
import id.seruput.api.data.transaction.TransactionDetailManager;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.database.Database;
import id.seruput.api.util.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDetailManagerImpl implements TransactionDetailManager {

    private static final Logger log = Logger.getLogger(TransactionDetailManagerImpl.class);

    private final Database database;
    private final TransactionDetailRepository repository;

    public TransactionDetailManagerImpl(Database database) {
        this.database = database;
        this.repository = new TransactionDetailRepository(database);
    }

    @Override
    public List<TransactionDetail> fetchDetailByTransactionId(TransactionId transactionId) {
        return repository.findByTransactionId(transactionId);
    }

    @Override
    public void addItem(TransactionId transactionId, Product product, int quantity) {
        TransactionDetail transactionDetail = TransactionDetailImpl.builder()
                .key(transactionId, product.productId())
                .quantity(quantity)
                .build();

        repository.insert(transactionDetail);
        fetchDetailByTransactionId(transactionId).add(transactionDetail);
        log.info("Transaction Detail added: " + transactionDetail);
    }

}
