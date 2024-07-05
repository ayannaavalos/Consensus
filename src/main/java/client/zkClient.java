package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashSet;

import blockchain.usecases.defi.DefiTransaction;
import blockchain.usecases.zk.zkTransaction;
import communication.messaging.Message;
import utils.Address;

public class zkClient {

    Object updateLock;
    BufferedReader reader;
    HashSet<DefiTransaction> seenTransactions; // Transactions we've seen from full nodes
    Address myAddress;
    ArrayList<Address> fullNodes; // List of full nodes we want to use
    boolean test; // Boolean for test vs normal output


    /**
     * Constructs a DefiClient instance.
     * @param updateLock The lock for multithreading.
     * @param reader The BufferedReader for reading user input.
     * @param myAddress The address of the client.
     * @param fullNodes The list of full nodes to interact with.
     */
    public zkClient(Object updateLock, BufferedReader reader, Address myAddress, ArrayList<Address> fullNodes){
        this.reader = reader;
        this.updateLock = updateLock;
        this.myAddress = myAddress;
        this.fullNodes = fullNodes;
        seenTransactions = new HashSet<>();
    }

    /**
     * Submits a new transaction to the Defi network.
     * @throws IOException If an I/O error occurs.
     */
    protected void submitTransaction() throws IOException{
        System.out.println("Generating Transaction");
        System.out.println("URL?");
        String url = reader.readLine();
        System.out.println("Content?");
        String content = reader.readLine();

        // ZK stuff here for proposer


        // PrivateKey pk = chosenAccount.getKeyPair().getPrivate();
        // String myPublicKeyString = DSA.bytesToString(chosenAccount.getKeyPair().getPublic().getEncoded());

        zkTransaction newTransaction = new zkTransaction(url, content, String.valueOf(System.currentTimeMillis()));
        String UID = newTransaction.getUID();
        //byte[] signedUID = DSA.signHash(UID, pk);
        //newTransaction.setSigUID(signedUID);

        System.out.println("Submitting transaction to nodes: ");
        for(Address address : fullNodes){
            submitTransaction(newTransaction, address);
        }
    }

    /**
     * Submits a transaction to a specific full node.
     * @param transaction The DefiTransaction to submit.
     * @param address The address of the full node.
     */
    protected void submitTransaction(zkTransaction transaction, Address address){
        try {
            Socket s = new Socket(address.getHost(), address.getPort());
            OutputStream out = s.getOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(out);
            Message message = new Message(Message.Request.ADD_TRANSACTION, transaction);
            oout.writeObject(message);
            oout.flush();
            Thread.sleep(1000);
            s.close();
            if(!this.test) System.out.println("Full node: " + address);
        } catch (IOException e) {
            System.out.println("Full node at " + address + " appears down.");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Prints information about the client's accounts and their balances.
     */
    // protected void printAccounts(){
    //     System.out.println("=============== Accounts ================");
    //     for(Account account :  accounts){
    //         System.out.println(account.getNickname() + " balance: " + account.getBalance());
    //         System.out.println("Pubkey: " + DSA.bytesToString(account.getKeyPair().getPublic().getEncoded()) + "\n");
    //     }
    //     System.out.print(">");
    // }

    /**
     * Prints the usage information for the BlueChain Wallet.
     */
    protected void printUsage(){
        System.out.println("BlueChain Wallet Usage:");
        System.out.println("t: Create a transaction");
    }
}