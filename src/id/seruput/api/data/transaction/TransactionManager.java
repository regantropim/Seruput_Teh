package id.seruput.api.data.transaction;

import id.seruput.api.data.cart.Cart;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.data.user.User;
import id.seruput.api.data.user.UserId;
import id.seruput.api.exception.DataValidationException;

import java.util.List;

public interface TransactionManager {
    void purchaseItem(User user, List<Cart> carts, ProductManager manager) throws DataValidationException;

    List<Transaction> fetchTransaction(User user);

    List<Transaction> fetchTransaction(UserId user);
}
