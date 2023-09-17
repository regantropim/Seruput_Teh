package id.seruput.core.data.transaction.detail;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.product.ProductId;
import id.seruput.api.data.transaction.TransactionDetail;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.database.DataValidator;
import id.seruput.api.exception.BuilderIncompleteException;
import id.seruput.api.exception.DataValidationException;
import id.seruput.api.util.builder.EntityBuilder;

import static id.seruput.core.util.Language.FIELDS_EMPTY;

public class TransactionDetailImpl implements TransactionDetail {

    private final CompositeKey<TransactionId, ProductId> key;

    private final int quantity;

    public TransactionDetailImpl(TransactionId transactionId, ProductId productId, int quantity) {
        this(CompositeKey.of(transactionId, productId), quantity);
    }

    public TransactionDetailImpl(CompositeKey<TransactionId, ProductId> key, int quantity) {
        this.key = key;
        this.quantity = quantity;
    }

    @Override
    public CompositeKey<TransactionId, ProductId> id() {
        return key;
    }

    @Override
    public int quantity() {
        return quantity;
    }

    @Override
    public CompositeKey<TransactionId, ProductId> primaryKey() {
        return key;
    }

    public static class TransactionDetailBuilder implements EntityBuilder<TransactionDetail> {

        private CompositeKey<TransactionId, ProductId> key;
        private int quantity;

        TransactionDetailBuilder() {

        }

        public TransactionDetailBuilder key(CompositeKey<TransactionId, ProductId> key) {
            this.key = key;
            return this;
        }

        public TransactionDetailBuilder key(TransactionId transactionId, ProductId productId) {
            this.key = CompositeKey.of(transactionId, productId);
            return this;
        }

        public TransactionDetailBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        public TransactionDetail build() {
            if (key.first() != null && key.second() != null) {
                return new TransactionDetailImpl(key, quantity);
            } else {
                throw new BuilderIncompleteException("TransactionDetailImpl can't be built, data is not complete!");
            }
        }

        @Override
        public TransactionDetail build(DataValidator<TransactionDetail> validator) throws DataValidationException {
            try {
                TransactionDetail transactionDetail = build();
                validator.validate(transactionDetail);
                return transactionDetail;
            } catch (BuilderIncompleteException e) {
                throw new DataValidationException(FIELDS_EMPTY);
            }
        }

    }

}
