package id.seruput.core.data.cart;

import id.seruput.api.data.cart.Cart;
import id.seruput.api.data.cart.CartManager;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Database;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.util.logger.Logger;

import java.util.*;

public class CartManagerImpl implements CartManager {

    private static final Logger logger = Logger.getLogger(CartManagerImpl.class);

    private final Map<UserId, List<Cart>> carts = new HashMap<>();
    private final Database database;
    private final CartRepository repository;
    private final CartDataValidator validator = new CartDataValidator();

    public CartManagerImpl(Database database) {
        this.database = database;
        this.repository = new CartRepository(database, new CartOperationHelper());

        loadCarts();
    }

    private void loadCarts() {
        carts.clear();
        repository.findAll().forEach(cart -> carts.computeIfAbsent(cart.userId(), userId -> new ArrayList<>()).add(cart));
    }

    @Override
    public List<Cart> findCart(UserId userId) {
        List<Cart> cart = carts.get(userId);
        if (cart != null) {
            return carts.computeIfAbsent(userId, userId1 -> new ArrayList<>());
        }
        return repository.findByUserId(userId);
    }

    @Override
    public void updateCart(Cart cart) {
        try {
            repository.update(cart);
            carts.computeIfAbsent(cart.userId(), userId -> new ArrayList<>()).add(cart);
        } catch (RuntimeException e) {
            logger.error("Failed to update cart: " + cart);
            throw e;
        }
    }

    @Override
    public void addCart(UserId userId, ProductId productId, int quantity) throws DataValidationException {
        final int[] oldQuantity = {0};
        List<Cart> carts = findCart(userId);
        Cart cart = carts.stream()
                .filter(filter -> filter.productId().equals(productId))
                .findFirst()
                .map(c -> {
                    oldQuantity[0] = c.quantity();
                    return c.quantity(c.quantity() + quantity);
                })
                .orElse(CartImpl.builder()
                        .id(userId, productId)
                        .quantity(quantity)
                        .build(validator)
                );

        try {
            repository.save(cart);
            if (oldQuantity[0] == 0) {
                carts.add(cart);
            }
            logger.info("Cart added: " + cart);
        } catch (RuntimeException e) {
            logger.error("Failed to add cart: " + cart);

            if (oldQuantity[0] != 0) {
                cart.quantity(oldQuantity[0]); // restore old quantity
            }

            throw e;
        }
    }

    public void removeCart(Cart cart) {
        List<Cart> carts = findCart(cart.userId());

        try {
            repository.delete(cart);
            carts.remove(cart);
        } catch (RuntimeException e) {
            logger.error("Failed to remove cart: " + cart);
            throw e;
        }
    }

    public static CartManager build(Database database) {
        return new CartManagerImpl(database);
    }


}
