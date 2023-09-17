package id.seruput.core.data.transaction.detail;

import id.seruput.api.data.transaction.TransactionDetail;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.database.Database;

import java.util.List;

public class TransactionDetailManagerImpl {

    private final Database database;
    private final TransactionDetailRepository repository;

    public TransactionDetailManagerImpl(Database database) {
        this.database = database;
        this.repository = new TransactionDetailRepository(database);
    }

    public List<TransactionDetail> fetchDetailByTransactionId(TransactionId transactionId) {
        return repository.findByTransactionId(transactionId);
    }

}
