package id.seruput.api.data.transaction;

import id.seruput.api.data.Identity;

public class TransactionId extends Identity {

    public TransactionId(int identity, String prefix) {
        super(identity, prefix);
    }

    public static TransactionId of(String transactionId) {
        return of(Integer.parseInt(transactionId.substring(2)));
    }

    public static TransactionId of(int transactionId) {
        return new TransactionId(transactionId, "TR");
    }

}
