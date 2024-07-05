package blockchain.usecases.zk;
import blockchain.TransactionValidator;

/**
 * An implementation of a TransactionValidator for the Defi use case
 */
public class zkTransactionValidator extends TransactionValidator{

    @Override
    public boolean validate(Object[] objects) {
        return true;
    }
}