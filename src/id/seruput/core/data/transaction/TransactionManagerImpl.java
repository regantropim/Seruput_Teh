package id.seruput.core.data.transaction;

import id.seruput.api.data.product.Product;
import id.seruput.api.data.transaction.Transaction;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.data.transaction.TransactionManager;
import id.seruput.api.data.user.User;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Database;
import id.seruput.api.exception.DataValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransactionManagerImpl implements TransactionManager {

    private final Database database;
    private final TransactionRepository repository;
    private int highestId;

    private final Map<TransactionId, Transaction> transactions = new HashMap<>();


    TransactionManagerImpl(Database database) {
        this.database = database;
        this.repository = new TransactionRepository(database, new TransactionOperationHelper());
        this.highestId = repository.findHighestId().map(i -> highestId).orElse(0);
    }

    public synchronized void purchaseItem(User user, Product product) throws DataValidationException {
        TransactionId transactionId = TransactionId.of(highestId + 1);
        Transaction transaction = TransactionImpl.builder()
                .transactionId(transactionId)
                .userId(user.id())
                .build();

        if (fetchTransaction(transactionId).isPresent()) {
            throw new DataValidationException("Failed to Purchase, Data already exist!");
        }

        repository.save(transaction);
        highestId++;
    }

    public Optional<Transaction> fetchTransaction(TransactionId transactionId) {
        Transaction transaction = transactions.get(transactionId);
        if (transaction == null) {
            return repository.findById(transactionId);
        }
        return Optional.of(transaction);
    }

    @Override
    public List<Transaction> fetchTransaction(User user) {
        return fetchTransaction(user.id());
    }

    @Override
    public List<Transaction> fetchTransaction(UserId user) {
        return repository.findTransactionByUser(user);
    }

    public List<TransactionId> fetchTransactionDetail(TransactionId transactionId) {
        return null;
    }

}
