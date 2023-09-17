package id.seruput.core.state;

import id.seruput.api.data.product.Product;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.user.User;

import java.util.Map;
import java.util.Optional;

public class AppState {

    private User user;


    public AppState() {

    }

    public void currentUser(User user) {
        this.user = user;
    }

    public Optional<User> currentUser() {
        return Optional.of(user);
    }

}
