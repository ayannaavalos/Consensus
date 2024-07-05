package blockchain.usecases.zk;

import java.util.HashMap;
import java.util.HashSet;

import blockchain.Block;
import blockchain.Transaction;

/**
 * A block implemented for the zk use case
 */
public class zkBlock extends Block{

    public zkBlock(HashMap<String, Transaction> txList, String prevBlockHash, int blockId) {

        /* Setting variables inherited from Block class */
        this.txList = new HashMap<>();
        this.prevBlockHash = prevBlockHash;
        this.blockId = blockId;

        /* Converting the transaction from Block to DefiTransactions*/
        HashSet<String> keys = new HashSet<>(txList.keySet());
        for(String key : keys){
            zkTransaction transactionInList = (zkTransaction) txList.get(key);
            this.txList.put(key, transactionInList);
        }
    }
}
