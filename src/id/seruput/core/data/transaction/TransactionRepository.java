package id.seruput.core.data.transaction;

import id.seruput.api.data.CompositeKey;
import id.seruput.api.data.transaction.Transaction;
import id.seruput.api.data.transaction.TransactionId;
import id.seruput.api.data.user.UserId;
import id.seruput.api.database.Database;
import id.seruput.api.database.OperationHelper;
import id.seruput.api.database.repository.BootlegRepository;

public class TransactionRepository extends BootlegRepository<Transaction, CompositeKey<TransactionId, UserId>> {

    public TransactionRepository(Database database, OperationHelper<Transaction, CompositeKey<TransactionId, UserId>> operationHelper) {
        super(database, operationHelper);
    }
}
