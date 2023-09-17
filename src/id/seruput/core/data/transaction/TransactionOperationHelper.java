package id.seruput.core.data.transaction;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.transaction.Transaction;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.BootlegOperationHelper;
import id.seruput.api.database.DataValidator;
import id.seruput.api.database.OperationHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionOperationHelper extends BootlegOperationHelper<Transaction, CompositeKey<TransactionId, UserId>> {

    @Override
    public Transaction update(ResultSet rs, Transaction transaction) throws SQLException {
        transaction = cacheOrCreate(transaction);

        return transaction.userId(UserId.of(rs.getString("userID")))
                   .transactionId(TransactionId.of(rs.getString("transactionID")));
    }

    @Override
    public Transaction deserialize(ResultSet resultSet) throws SQLException {
        return TransactionImpl.builder()
                .transactionId(TransactionId.of(resultSet.getString("transactionID")))
                .userId(UserId.of(resultSet.getString("userID")))
                .build();
    }

    @Override
    public PreparedStatement selectAll(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM transaction_header");
    }

    @Override
    public PreparedStatement selectStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM transaction_header WHERE transactionID = ? and userID = ?");
    }

    @Override
    public PreparedStatement insertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO transaction_header (transactionID, userID) VALUES (?, ?)");
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("DELETE FROM transaction_header WHERE transactionID = ?");
    }

    @Override
    public PreparedStatement updateStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("UPDATE transaction_header SET userID = ? WHERE transactionID = ? and userID = ?");
    }

    @Override
    public PreparedStatement updateOrInsertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO transaction_header (transactionID, userID) VALUES (?, ?) ON " +
                "DUPLICATE KEY UPDATE transactionID = ?, userID = ?");
    }

    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, Transaction transaction) throws SQLException {
        preparedStatement.setString(1, transaction.transactionId().asString());
    }

    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, CompositeKey<TransactionId, UserId> id) throws SQLException {
        preparedStatement.setString(1, id.first().asString());
        preparedStatement.setString(2, id.second().asString());
    }

    @Override
    public void setInsertPreparedStatement(PreparedStatement preparedStatement, Transaction user) throws SQLException {
        preparedStatement.setString(1, user.transactionId().asString());
        preparedStatement.setString(2, user.userId().asString());
    }

    @Override
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, Transaction user) throws SQLException {
        preparedStatement.setString(1, user.transactionId().asString());
    }

    @Override
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, CompositeKey<TransactionId, UserId> id) throws SQLException {
        preparedStatement.setString(1, id.first().asString());
        preparedStatement.setString(2, id.second().asString());
    }

    @Override
    public void setUpdatePreparedStatement(PreparedStatement preparedStatement, Transaction user) throws SQLException {
        preparedStatement.setString(1, user.userId().asString());
        preparedStatement.setString(2, user.transactionId().asString());
    }

    @Override
    public void setUpdateOrInsertPreparedStatement(PreparedStatement preparedStatement, Transaction user) throws SQLException {
        String transactionId = user.transactionId().asString();
        String userId = user.userId().asString();

        preparedStatement.setString(1, transactionId);
        preparedStatement.setString(2, userId);
        preparedStatement.setString(3, transactionId);
        preparedStatement.setString(4, userId);
    }
}
