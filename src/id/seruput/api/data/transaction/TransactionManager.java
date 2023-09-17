package id.seruput.api.data.transaction;

import id.seruput.api.data.user.User;
import id.seruput.api.data.user.UserId;

import java.util.List;

public interface TransactionManager {
    List<Transaction> fetchTransaction(User user);

    List<Transaction> fetchTransaction(UserId user);
}
