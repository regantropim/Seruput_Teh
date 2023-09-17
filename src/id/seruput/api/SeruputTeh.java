package id.seruput.api;

import id.seruput.api.data.cart.CartManager;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.data.user.User;
import id.seruput.api.database.Database;
import id.seruput.api.data.user.UserManager;

import java.util.Optional;

public interface SeruputTeh {

    UserManager userManager();

    ProductManager productManager();

    Database database();

    CartManager cartManager();

    void currentUser(User user);

    Optional<User> currentUser();

}
