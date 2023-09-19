package id.seruput.api.data.transaction;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Entity;

public interface Transaction extends Entity<TransactionId> {

    TransactionId id();

    default TransactionId transactionId() {
        return id();
    }

    UserId userId();
}
