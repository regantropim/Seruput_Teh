package id.seruput.core;

import id.seruput.api.SeruputTeh;
import id.seruput.api.data.cart.CartManager;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.data.user.User;
import id.seruput.api.database.Database;
import id.seruput.api.data.user.UserManager;
import id.seruput.core.data.cart.CartManagerImpl;
import id.seruput.core.data.product.ProductManagerImpl;
import id.seruput.core.database.DatabaseImpl;
import id.seruput.core.data.user.UserManagerImpl;

import java.util.Optional;

public class SeruputTehCore implements SeruputTeh {

    private final UserManager userManager;
    private final ProductManager productManager;
    private final CartManager cartManager;
    private final Database database;

    private User currentUser;

    SeruputTehCore(String databasePassword) {
        this.database = DatabaseImpl.builder()
                .database("seruput_teh")
                .host("127.0.0.1")
                .port((short) 3306)
                .username("root")
                .build(databasePassword);

        if (this.database == null) {
            throw new RuntimeException("Database connection failed");
        }

        this.userManager = UserManagerImpl.build(database);
        this.productManager = ProductManagerImpl.build(database);
        this.cartManager = CartManagerImpl.build(database);
    }

    public static SeruputTeh create(String databasePassword) {
        return new SeruputTehCore(databasePassword);
    }

    @Override
    public UserManager userManager() {
        return userManager;
    }

    @Override
    public Database database() {
        return database;
    }

    @Override
    public ProductManager productManager() {
        return productManager;
    }

    @Override
    public CartManager cartManager() {
        return cartManager;
    }

    @Override
    public void currentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public Optional<User> currentUser() {
        return Optional.ofNullable(currentUser);
    }
}
