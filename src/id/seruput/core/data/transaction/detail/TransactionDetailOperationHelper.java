package id.seruput.core.data.transaction.detail;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.transaction.TransactionDetail;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.database.BootlegOperationHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDetailOperationHelper extends BootlegOperationHelper<TransactionDetail, CompositeKey<TransactionId, ProductId>> {

    @Override
    public TransactionDetail update(ResultSet rs, TransactionDetail transactionDetail) throws SQLException {
        return transactionDetail.quantity(rs.getInt("quantity"));
    }

    @Override
    public TransactionDetail deserialize(ResultSet resultSet) throws SQLException {
        return TransactionDetailImpl.builder()
                .key(CompositeKey.of(
                        TransactionId.of(resultSet.getString("transactionID")),
                        ProductId.of(resultSet.getString("productID"))
                ))
                .quantity(resultSet.getInt("quantity"))
                .build();
    }

    @Override
    public PreparedStatement selectAll(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "SELECT * FROM transaction_detail"
        );
    }

    @Override
    public PreparedStatement selectStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "SELECT * FROM transaction_detail WHERE transactionID = ? and productID = ?"
        );
    }

    @Override
    public PreparedStatement insertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "INSERT INTO transaction_detail (transactionID, productID, quantity) VALUE (?, ?, ?)"
        );
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "DELETE FROM transaction_detail WHERE transactionID = ? AND productID = ?"
        );
    }

    @Override
    public PreparedStatement updateStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "UPDATE transaction_detail SET transactionID = ?, productID = ?, quantity = ? WHERE transactionID = ? AND productID = ?"
        );
    }

    @Override
    public PreparedStatement updateOrInsertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(
                "INSERT INTO transaction_detail (transactionID, productID, quantity) VALUE (?, ?, ?) ON DUPLICATE KEY UPDATE transactionID = ?, productID = ?, quantity = ?"
        );
    }

    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, TransactionDetail transactionDetail) throws SQLException {
        setSelectPreparedStatement(preparedStatement, transactionDetail.id());
    }

    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, CompositeKey<TransactionId, ProductId> id) throws SQLException {
        preparedStatement.setString(1, id.first().asString());
        preparedStatement.setString(2, id.second().asString());
    }

    @Override
    public void setInsertPreparedStatement(PreparedStatement preparedStatement, TransactionDetail transactionDetail) throws SQLException {
        preparedStatement.setString(1, transactionDetail.transactionId().asString());
        preparedStatement.setString(2, transactionDetail.productId().asString());
        preparedStatement.setInt(3, transactionDetail.quantity());
    }

    @Override
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, TransactionDetail transactionDetail) throws SQLException {
        setDeletePreparedStatement(preparedStatement, transactionDetail.id());
    }

    @Override
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, CompositeKey<TransactionId, ProductId> id) throws SQLException {
        preparedStatement.setString(1, id.first().asString());
        preparedStatement.setString(2, id.second().asString());
    }

    @Override
    public void setUpdatePreparedStatement(PreparedStatement preparedStatement, TransactionDetail transactionDetail) throws SQLException {
        preparedStatement.setString(1, transactionDetail.transactionId().asString());
        preparedStatement.setString(2, transactionDetail.productId().asString());
        preparedStatement.setInt(3, transactionDetail.quantity());
    }

    @Override
    public void setUpdateOrInsertPreparedStatement(PreparedStatement preparedStatement, TransactionDetail transactionDetail) throws SQLException {
        preparedStatement.setString(1, transactionDetail.transactionId().asString());
        preparedStatement.setString(2, transactionDetail.productId().asString());
        preparedStatement.setInt(3, transactionDetail.quantity());
        preparedStatement.setString(4, transactionDetail.transactionId().asString());
        preparedStatement.setString(5, transactionDetail.productId().asString());
        preparedStatement.setInt(6, transactionDetail.quantity());
    }

}
