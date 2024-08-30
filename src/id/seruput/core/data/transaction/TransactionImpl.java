package id.seruput.core.data.transaction;

import id.seruput.api.data.transaction.Transaction;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.DataValidator;
import id.seruput.api.exception.BuilderIncompleteException;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.util.builder.EntityBuilder;

import static id.seruput.core.util.Language.FIELDS_EMPTY;

public class TransactionImpl implements Transaction {

    private final TransactionId key;
    private final UserId userId;

    public TransactionImpl(TransactionId key, UserId userId) {
        this.key = key;
        this.userId = userId;
    }

    @Override
    public TransactionId id() {
        return key;
    }

    @Override
    public TransactionId primaryKey() {
        return key;
    }

    @Override
    public UserId userId() {
        return userId;
    }

    public static TransactionImplBuilder builder() {
        return new TransactionImplBuilder();
    }

    public static class TransactionImplBuilder implements EntityBuilder<Transaction> {

        private TransactionId transactionId;
        private UserId userId;

        public TransactionImplBuilder transactionId(TransactionId transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransactionImplBuilder userId(UserId userId) {
            this.userId = userId;
            return this;
        }

        @Override
        public Transaction build() {
            if (transactionId != null && userId != null) {
                return new TransactionImpl(transactionId, userId);
            } else {
                throw new BuilderIncompleteException("TransactionId and UserId must not be null");
            }
        }

        @Override
        public Transaction build(DataValidator<Transaction> validator) throws DataValidationException {
            try {
                Transaction user = build();
                validator.validate(user);
                return user;
            } catch (BuilderIncompleteException e) {
                throw new DataValidationException(FIELDS_EMPTY);
            }
        }

    }

}
