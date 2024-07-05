package blockchain.usecases.zk;

import blockchain.Transaction;
import utils.*;

/**
 * A specific transaction made for the zk use case
 */
public class zkTransaction extends Transaction {

    protected String url; // URL to source
    protected String content; // words

    public String getUrl() {
        return url;
    }


    public String getContent() {
        return content;
    }


    public zkTransaction(String url, String content, String timestamp){
        this.timestamp = timestamp;
        this.url = url;
        this.content = content;

        UID = Hashing.getSHAString(url + content + timestamp); // Hashing above fields to generate a unique timestamp
    }


    @Override
    public String toString() {
        return "URL: " + url + "   Content: " + content;
    }
}