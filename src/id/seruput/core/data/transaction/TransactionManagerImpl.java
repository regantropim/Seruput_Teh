package id.seruput.core.data.transaction;

import id.seruput.api.data.Identity;
import id.seruput.api.data.cart.Cart;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.data.transaction.*;
import id.seruput.api.data.user.User;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Database;
import id.seruput.api.exception.DataValidationException;
import id.seruput.core.data.transaction.detail.TransactionDetailManagerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionManagerImpl implements TransactionManager {

    private final Database database;
    private final TransactionRepository repository;
    private final TransactionDetailManager transactionDetailManager;
    private int highestId;

    TransactionManagerImpl(Database database) {
        this.database = database;
        this.repository = new TransactionRepository(database, new TransactionOperationHelper());
        this.transactionDetailManager = new TransactionDetailManagerImpl(database);
        this.highestId = repository.findHighestId().map(Identity::identity).orElse(0);
    }

    public static TransactionManager build(Database database) {
        return new TransactionManagerImpl(database);
    }

    @Override
    public synchronized void purchaseItem(User user, List<Cart> carts, ProductManager manager) throws DataValidationException {
        TransactionId transactionId = TransactionId.of(highestId + 1);
        Transaction transaction = TransactionImpl.builder()
                .transactionId(transactionId)
                .userId(user.id())
                .build();

        if (fetchTransaction(transactionId).isPresent()) {
            throw new DataValidationException("Failed to Purchase, Data already exist! data: " + transactionId);
        }

        repository.insert(transaction);

        carts.stream()
                .collect(Collectors.toMap(cart -> cart.product(manager).orElseThrow(), cart -> cart))
                .forEach((product, cart) -> transactionDetailManager.addItem(transactionId, product, cart.quantity()));

        highestId++;
    }

    public Optional<Transaction> fetchTransaction(TransactionId transactionId) {
        return repository.findById(transactionId);
    }

    @Override
    public List<Transaction> fetchTransaction(User user) {
        return fetchTransaction(user.id());
    }

    @Override
    public List<Transaction> fetchTransaction(UserId user) {
        return repository.findTransactionByUser(user);
    }

    @Override
    public List<TransactionDetail> fetchTransactionDetail(Transaction transaction) {
        return transactionDetailManager.fetchDetailByTransactionId(transaction.transactionId());
    }

}
