package id.seruput.core.data.cart;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.cart.Cart;
import id.seruput.api.data.product.ProductId;
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

public class CartRepository extends BootlegRepository<Cart, CompositeKey<UserId, ProductId>> {

    public CartRepository(Database database, OperationHelper<Cart, CompositeKey<UserId, ProductId>> operationHelper) {
        super(database, operationHelper);
    }

    public List<Cart> findByUserId(UserId userId) {
        try(PooledConnection connection = database.fromPool();
            PreparedStatement statement = connection.connection().prepareStatement(
                    "SELECT * FROM cart WHERE userID = ?"
            )) {

            statement.setString(1, userId.asString());

            ResultSet resultSet = statement.executeQuery();

            List<Cart> carts = new ArrayList<>();
            while (resultSet.next()) {
                carts.add(operationHelper.deserialize(resultSet));
            }
            return carts;
        } catch (SQLException | InterruptedException | EmptyConnectionPoolException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertOrIncrease(Cart cart) {
        try(PooledConnection connection = database.fromPool();
            PreparedStatement statement = connection.connection().prepareStatement(
                    "INSERT INTO cart (userID, productID, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + ?"
            )) {

            statement.setString(1, cart.userId().asString());
            statement.setString(2, cart.productId().asString());
            statement.setInt(3, cart.quantity());
            statement.setInt(4, cart.quantity());

            statement.executeUpdate();

        } catch (SQLException | InterruptedException | EmptyConnectionPoolException e) {
            throw new RuntimeException(e);
        }
    }

}
