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

    private final Map<TransactionId, List<TransactionDetail>> transactionDetails = new HashMap<>();

    public TransactionDetailManagerImpl(Database database) {
        this.database = database;
        this.repository = new TransactionDetailRepository(database);
    }

    public List<TransactionDetail> fetchDetailByTransactionId(TransactionId transactionId) {
        List<TransactionDetail> transactionDetail = transactionDetails.computeIfAbsent(transactionId, k -> new ArrayList<>());
        if (transactionDetail.isEmpty()) {
            transactionDetail = repository.findByTransactionId(transactionId);
            transactionDetails.put(transactionId, transactionDetail);
        }
        return transactionDetail;
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
