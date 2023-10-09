package id.seruput.api;

import id.seruput.api.data.cart.CartManager;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.data.transaction.TransactionManager;
import id.seruput.api.data.user.User;
import id.seruput.api.data.user.UserManager;
import id.seruput.api.database.Database;

import java.util.Optional;

public interface SeruputTeh {

    UserManager userManager();

    ProductManager productManager();

    Database database();

    TransactionManager transactionManager();

    CartManager cartManager();

    void currentUser(User user);

    Optional<User> currentUser();

}
