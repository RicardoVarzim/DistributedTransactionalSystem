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
    
    public Coordinator() throws RemoteException{
        
    }
    
    @Override
    public boolean newCohort(Cohort cohort) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean transaction(ArrayList<SubTransaction> requets) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CoordinatorLogger getLogger() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rollback() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTransaction(Transaction transaction) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void commit(Long id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Transaction getTransaction() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CoordinatorLog> getLogItems(String id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
