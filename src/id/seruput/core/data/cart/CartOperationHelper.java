package id.seruput.core.data.cart;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.cart.Cart;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.BootlegOperationHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartOperationHelper extends BootlegOperationHelper<Cart, CompositeKey<UserId, ProductId>> {

    @Override
    public Cart update(ResultSet rs, Cart cart) throws SQLException {
        return cart.quantity(rs.getInt("quantity"));
    }

    @Override
    public Cart deserialize(ResultSet resultSet) throws SQLException {
        return CartImpl.builder()
                .id(CompositeKey.of(UserId.of(resultSet.getString("userID")), ProductId.of(resultSet.getString("productID"))))
                .quantity(resultSet.getInt("quantity"))
                .build();
    }

    @Override
    public PreparedStatement selectAll(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM cart");
    }

    @Override
    public PreparedStatement selectStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM cart WHERE userID = ? AND productID = ?");
    }

    @Override
    public PreparedStatement insertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO cart (userID, productID, quantity) VALUES (?, ?, ?)");
    }

    @Override
    public PreparedStatement deleteStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("DELETE FROM cart WHERE userID = ? AND productID = ?");
    }

    @Override
    public PreparedStatement updateStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("UPDATE cart SET quantity = ? WHERE userID = ? AND productID = ?");
    }

    @Override
    public PreparedStatement updateOrInsertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO cart (userID, productID, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = ?");
    }

    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, Cart cart) throws SQLException {
        setSelectPreparedStatement(preparedStatement, cart.id());
    }

    @Override
    public void setSelectPreparedStatement(PreparedStatement preparedStatement, CompositeKey<UserId, ProductId> id) throws SQLException {
        preparedStatement.setString(1, id.first().asString());
        preparedStatement.setString(2, id.second().asString());
    }

    @Override
    public void setInsertPreparedStatement(PreparedStatement preparedStatement, Cart cart) throws SQLException {
        preparedStatement.setString(1, cart.userId().asString());
        preparedStatement.setString(2, cart.productId().asString());
        preparedStatement.setInt(3, cart.quantity());

    }

    @Override
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, Cart cart) throws SQLException {
        setDeletePreparedStatement(preparedStatement, cart.id());
    }

    @Override
    public void setDeletePreparedStatement(PreparedStatement preparedStatement, CompositeKey<UserId, ProductId> id) throws SQLException {
        preparedStatement.setString(1, id.first().asString());
        preparedStatement.setString(2, id.second().asString());
    }

    @Override
    public void setUpdatePreparedStatement(PreparedStatement preparedStatement, Cart cart) throws SQLException {
        preparedStatement.setInt(1, cart.quantity());
        preparedStatement.setString(2, cart.userId().asString());
        preparedStatement.setString(3, cart.productId().asString());
    }

    @Override
    public void setUpdateOrInsertPreparedStatement(PreparedStatement preparedStatement, Cart cart) throws SQLException {
        preparedStatement.setString(1, cart.userId().asString());
        preparedStatement.setString(2, cart.productId().asString());
        preparedStatement.setInt(3, cart.quantity());
        preparedStatement.setInt(4, cart.quantity());
    }
}
