package id.seruput.core.data.transaction.detail;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.transaction.TransactionDetail;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.database.Database;
import id.seruput.api.database.pool.PooledConnection;
import id.seruput.api.database.repository.BootlegRepository;
import id.seruput.api.exception.EmptyConnectionPoolException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDetailRepository extends BootlegRepository<TransactionDetail, CompositeKey<TransactionId, ProductId>> {

    public TransactionDetailRepository(Database database) {
        super(database, new TransactionDetailOperationHelper());
    }

    public List<TransactionDetail> findByTransactionId(TransactionId transactionId) {
        try (PooledConnection connectionPool = database.fromPool();
             PreparedStatement statement = connectionPool.connection().prepareStatement(
                     "SELECT * FROM transaction_detail WHERE transactionID = ?"
             )) {

            statement.setString(1, transactionId.asString());

            ResultSet rs = statement.executeQuery();
            List<TransactionDetail> transactionDetails = new ArrayList<>();

            while (rs.next()) {
                transactionDetails.add(deserialize(rs));
            }

            return transactionDetails;

        } catch (SQLException | InterruptedException | EmptyConnectionPoolException e) {
            throw new RuntimeException(e);
        }
    }

}
