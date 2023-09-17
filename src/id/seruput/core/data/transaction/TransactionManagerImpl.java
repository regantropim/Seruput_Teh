package id.seruput.core.data.transaction;

import id.seruput.api.data.transaction.Transaction;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.data.transaction.TransactionManager;
import id.seruput.api.data.user.User;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Database;

import java.util.List;

public class TransactionManagerImpl implements TransactionManager {

    private final Database database;
    private final TransactionRepository repository;

    TransactionManagerImpl(Database database) {
        this.database = database;
        this.repository = new TransactionRepository(database);

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
