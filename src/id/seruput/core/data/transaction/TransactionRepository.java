package id.seruput.core.data.transaction;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.transaction.Transaction;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.data.user.User;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Database;
import id.seruput.api.database.OperationHelper;
import id.seruput.api.database.pool.PooledConnection;
import id.seruput.api.database.repository.BootlegRepository;
import id.seruput.api.exception.EmptyConnectionPoolException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository extends BootlegRepository<Transaction, CompositeKey<TransactionId, UserId>> {

    public TransactionRepository(Database database) {
        super(database, new TransactionOperationHelper());
    }

    public List<Transaction> findTransactionByUser(User user) {
        return findTransactionByUser(user.id());
    }

    public List<Transaction> findTransactionByUser(UserId userId) {
        try (PooledConnection pooled = database.fromPool();
             PreparedStatement statement = pooled.connection().prepareStatement(
                     "SELECT * FROM transaction_header WHERE userID = ?")) {
            statement.setString(1, userId.asString());

            ResultSet rs = statement.executeQuery();
            List<Transaction> transactions = new ArrayList<>();
            while (rs.next()) {
                transactions.add(deserialize(rs));
            }
            return transactions;
        } catch (SQLException | InterruptedException | EmptyConnectionPoolException e) {
            throw  new RuntimeException(e);
        }
    }

}
