/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ricardo
 */
public class Coordinator extends UnicastRemoteObject implements CoordinatorIf{
    
    private List<Cohort> cohorts = Collections.synchronizedList(new ArrayList<>());
    List<Boolean> votes = Collections.synchronizedList(new ArrayList<>());
    private ArrayList<Thread> cohortThreads = new ArrayList<>();
    private CoordinatorLogger logger = new CoordinatorLogger("Coordinatorlog");
    private Transaction transaction;
    private CoordinatorPingThread pingThread = new CoordinatorPingThread(cohorts);
    private long THREAD_WAIT_TIME = 1000;
    
    public Coordinator() throws RemoteException{
        pingThread.start();
    }
    
    @Override
    public boolean newCohort(Cohort cohort) throws RemoteException {
        for(Cohort c: cohorts){
            if(c.getBankName().equals(cohort.getBankName())) return false;
        }
        this.cohorts.add(cohort);
        System.out.println("New cohort added: " + cohort.getBankName());
        return true;
    }

    @Override
    public boolean transaction(ArrayList<SubTransaction> requests) throws RemoteException {
        System.out.println("Begin transasction");
        this.transaction = new Transaction(requests);
        logger.log(new CoordinatorLog(transaction));
        voteRequest();
        System.out.println("Checking if all votes are positive, and number of votes are correct.");
        if(votes.contains(false) || votes.size() < requests.size()){
            System.out.println("Too few votes / at least 1 false vote");
            System.out.println("Logging status ABORT");
            logger.log(new CoordinatorLog(transaction.getTransId(), CoordinatorStatus.ABORT));
            System.out.println("Starting rollback");
            rollback();
            System.out.println("Rollback sucessful");
            return false;
        }
        System.out.println("Correct number of votes.");
        System.out.println("Logging status COMMIT");
        logger.log(new CoordinatorLog(transaction.getTransId(), CoordinatorStatus.COMMIT));
        System.out.println("Loggin status FINISHED");
        return true;
    }

    @Override
    public CoordinatorLogger getLogger() throws RemoteException {
        return logger;
    }

    @Override
    public void rollback() throws RemoteException {
        for(Cohort c :cohorts)
            if(isCohortInTransaction(c)) c.roolback(transaction.getTransId());
        
        logger.log(new CoordinatorLog(transaction.getTransId(), CoordinatorStatus.FINISHED));
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
    public void commit(Long id) throws RemoteException {
        ArrayList<String> failureList = new ArrayList<>();
        ArrayList<Boolean> acks = new ArrayList<>();
        for(Cohort c: cohorts){
            if(isCohortInTransaction(c)){
                // this can timeout - threads?
                if(c.commit(id)) acks.add(true);
                else failureList.add(c.getBankName());
            }
        }
        if(acks.size() == transaction.getSubTransactions().size() && !acks.contains(false)){
            System.out.println("Commit successful");
            logger.log(new CoordinatorLog(transaction.getTransId(), CoordinatorStatus.FINISHED));
        }else{
            System.out.println("Commit failure.");
            failureList.forEach(s -> System.out.println("Run recovery on: " + s));
            logger.errorLog(id);
            logger.log(new CoordinatorLog(transaction.getTransId(), CoordinatorStatus.FINISHED));
        }}

    @Override
    public List<CoordinatorLog> getLogItems(String id) throws RemoteException {
        return logger.getLogItems(id);
    }

    private void voteRequest() throws RemoteException {
        System.out.println("Begin vote requests");
        cohortThreads = new ArrayList<>();
		votes = Collections.synchronizedList(new ArrayList<>());
        System.out.println("Creating " + transaction.getSubTransactions().size() + " threads, 1 per SubTrans");
        for(SubTransaction st : transaction.getSubTransactions()) {
            this.cohortThreads.add(new VoteThread(transaction.getTransId(), votes, getCohort(st.getBankname()), st));
        }

        cohortThreads.forEach(Thread::start); // SEND VOTE REQUESTS

        System.out.println("Vote requests are sent");
        System.out.println("Logging status WAIT");
        logger.log(new CoordinatorLog(transaction.getTransId(), CoordinatorStatus.WAIT));

        for (Thread t : cohortThreads) {
            try {
                //Timeout threads after a given time.
                t.join(THREAD_WAIT_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
    public Cohort getCohort(String dbname) throws RemoteException {
        for(Cohort c : this.cohorts){
            if(c.getBankName().equals(dbname)) return c;
        }
        return null; 
    }
        
    private boolean isCohortInTransaction(Cohort c) throws RemoteException  {
        for(SubTransaction st: transaction.getSubTransactions()){
            if(st.getBankname().equals(c.getBankName())) return true;
        }
        return false;
    }

    private static class CoordinatorPingThread extends Thread {
        private List<Cohort> cohorts;
        
        public CoordinatorPingThread(List<Cohort> cohorts) {
            this.cohorts = cohorts;
        }

        @Override
        public void run(){
            while(true){
                for(int i = cohorts.size()-1; i>=0;i--){
                    try {
                        cohorts.get(i).getBankName();
                    } catch (Exception e) {
                        System.out.println("PingThread: A bank didnt answer in time. Bank removed.");
                        cohorts.remove(i);
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
