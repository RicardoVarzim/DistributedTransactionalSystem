/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import transaction.Transaction;
import transaction.SubTransaction;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import rmi.AccountIf;
import rmi.BankIf;
import rmi.TransactionManagerIf;

/**
 *
 * @author ricardo
 */
public class TransactionManager extends UnicastRemoteObject implements TransactionManagerIf{
    
    private List<BankIf> banks = Collections.synchronizedList(new ArrayList<>());
    List<Boolean> votes = Collections.synchronizedList(new ArrayList<>());
    private ArrayList<Thread> bankThreads = new ArrayList<>();
    private Logger logger = new Logger("Coordinatorlog");
    private Transaction transaction;
    private CoordinatorPingThread pingThread = new CoordinatorPingThread(banks);
    private long THREAD_WAIT_TIME = 1000;
    
    public TransactionManager() throws RemoteException{
        pingThread.start();
    }
    
    @Override
    public boolean newBank(BankIf bank) throws RemoteException {
        for(BankIf b: banks){
           if(b.getName().equals(bank.getName())) return false;
        }
        this.banks.add(bank);
        System.out.println("Nova Banco adicionado " + bank.getName());
        return true;
    }
    
    @Override
    public boolean transaction(ArrayList<SubTransaction> requests) throws RemoteException {
        System.out.println("Begin transasction");
        this.transaction = new Transaction(requests);
        logger.log(new Log(transaction));
        
        voteRequest();
        System.out.println("Checking if all votes are positive, and number of votes are correct.");
        if(votes.contains(false) || votes.size() < requests.size()){
            System.out.println("Too few votes / at least 1 false vote");
            System.out.println("Logging status ABORT");
            logger.log(new Log(transaction.getTransId(), CoordinatorStatus.ABORT));
            System.out.println("Starting rollback");
            rollback();
            System.out.println("Rollback sucessful");
            return false;
        }
        System.out.println("Correct number of votes.");
        System.out.println("Logging status COMMIT");
        logger.log(new Log(transaction.getTransId(), CoordinatorStatus.COMMIT));
        
        commitRequest();
        System.out.println("Checking if all votes are positive, and number of votes are correct.");
        if(votes.contains(false) || votes.size() < requests.size()){
            System.out.println("Too few votes / at least 1 false vote");
            System.out.println("Logging status ABORT");
            logger.log(new Log(transaction.getTransId(), CoordinatorStatus.ABORT));
            System.out.println("Starting rollback");
            rollback();
            System.out.println("Rollback sucessful");
            return false;
        }
        System.out.println("Correct number of votes.");
        System.out.println("Loggin status FINISHED");
        return true;
    }

    private void voteRequest() throws RemoteException {
        System.out.println("Begin vote requests");
        bankThreads = new ArrayList<>();
        votes = Collections.synchronizedList(new ArrayList<>());
        System.out.println("Creating " + transaction.getSubTransactions().size() + " threads, 1 per SubTrans");
        for(SubTransaction st : transaction.getSubTransactions()) {
            this.bankThreads.add(new PrepareVoteThread(transaction.getTransId(), votes, getAccount(st.getBankname(),st.getAccount()), st));
        }

        bankThreads.forEach(Thread::start); // SEND VOTE REQUESTS

        System.out.println("Vote requests are sent");
        System.out.println("Logging status WAIT");
        logger.log(new Log(transaction.getTransId(), CoordinatorStatus.WAIT));

        for (Thread t : bankThreads) {
            try {
                t.join(THREAD_WAIT_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
        
    @Override
    public Logger getLogger() throws RemoteException {
        return logger;
    }

    @Override
    public void rollback() throws RemoteException {

    }

    @Override
    public void setTransaction(Transaction transaction) throws RemoteException {
        this.transaction = transaction;
    }

    @Override
    public Transaction getTransaction() throws RemoteException {
        return transaction;
    }

    @Override
    public List<Log> getLogItems(String id) throws RemoteException {
        return logger.getLogItems(id);
    }

    
    public AccountIf getAccount(String bankName, String accountName) throws RemoteException {
        AccountIf account= null;
        for(BankIf b : this.banks){
            if(b.getName().equals(bankName)){
                
                try {
                    account = b.findAccount(accountName);
                    return account;
                } catch (Exception ex) {
                    System.out.println("Cant find remote account.");
                    ex.printStackTrace();
                }
               
            }
        }
        return account;
    }
        
    private void commitRequest() throws RemoteException {
        System.out.println("Begin commit requests");
        bankThreads = new ArrayList<>();
        votes = Collections.synchronizedList(new ArrayList<>());
        System.out.println("Creating " + transaction.getSubTransactions().size() + " threads, 1 per SubTrans");
        for(SubTransaction st : transaction.getSubTransactions()) {
            this.bankThreads.add(new CommitVoteThread(transaction.getTransId(), votes, getAccount(st.getBankname(),st.getAccount()), st));
        }

        bankThreads.forEach(Thread::start); // SEND VOTE REQUESTS

        System.out.println("Vote requests are sent");
        System.out.println("Logging status WAIT");
        logger.log(new Log(transaction.getTransId(), CoordinatorStatus.WAIT));

        for (Thread t : bankThreads) {
            try {
                t.join(THREAD_WAIT_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getAvailableBanks() throws RemoteException {
        List<String> result = new ArrayList<>();
        for(BankIf b: banks){
            String name = b.getName();
            result.add(name);
        }
        return result;
    }

    @Override
    public List<String> getAccounts(String bank) throws RemoteException {
        if(bank != null){
            for(BankIf b : this.banks){
                if(b.getName().equals(bank)){

                    try {
                        List<String> list = b.listAccounts();
                        return list;
                    } catch (Exception ex) {
                        System.out.println("Cant find remote account.");
                        ex.printStackTrace();
                    }

                }
            }
        }
        return null;
    }

    private static class CoordinatorPingThread extends Thread {
        private List<BankIf> banks;
        
        public CoordinatorPingThread(List<BankIf> banks) {
            this.banks = banks;
        }

        @Override
        public void run(){
            while(true){
                for(int i = banks.size()-1; i>=0;i--){
                    try {
                        banks.get(i).getName();
                    } catch (Exception e) {
                        System.out.println("A bank didnt answer in time. Bank removed.");
                        banks.remove(i);
                    }
                }
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public enum CoordinatorStatus {
        INIT,
        WAIT,
        ABORT,
        COMMIT,
        FINISHED
    }
}
