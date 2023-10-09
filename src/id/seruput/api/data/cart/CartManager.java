package id.seruput.api.data.cart;

import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.product.ProductManager;
import id.seruput.api.data.user.UserId;
import id.seruput.api.exception.DataValidationException;

import java.util.List;

public interface CartManager {


    List<Cart> findCart(UserId userId);

    void updateCart(Cart cart);

    void addCart(UserId userId, ProductId productId, int quantity) throws DataValidationException;

    void removeCart(Cart cart);

    void clearCart(UserId userId) throws Exception;

    long calculateTotalPrice(UserId userId, ProductManager productManager);
}
