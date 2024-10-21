package org.example.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@RequiredArgsConstructor
public class TransactionsHelper {

    private final PlatformTransactionManager transactionManager;


    public <T> T doInTransaction(TransactionCallback<T> callback) {
        return doInTransaction(callback, false, Propagation.REQUIRED);
    }

    public <T> T doInReadOnlyTransaction(TransactionCallback<T> callback) {
        return doInTransaction(callback, true, Propagation.REQUIRED);
    }

    public <T> T doInTransaction(TransactionCallback<T> callback, Propagation propagation) {
        return doInTransaction(callback, false, propagation);
    }

    public <T> T doInTransaction(TransactionCallback<T> callback,
                                 Boolean readOnly,
                                 Propagation propagation) {
        TransactionTemplate transaction = new TransactionTemplate(transactionManager);
        transaction.setReadOnly(readOnly);
        transaction.setPropagationBehavior(propagation.value());
        return transaction.execute(callback);
    }
}
