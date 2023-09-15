package id.seruput.api.data.transaction;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Entity;

public interface Transaction extends Entity<CompositeKey<TransactionId, UserId>> {

    CompositeKey<TransactionId, UserId> id();

    default TransactionId transactionId() {
        return id().first();
    }

    default Transaction transactionId(TransactionId transactionId) {
        this.id().first(transactionId);
        return this;
    }

    default UserId userId() {
        return id().second();
    }

    default Transaction userId(UserId userId) {
        this.id().second(userId);
        return this;
    }

}
