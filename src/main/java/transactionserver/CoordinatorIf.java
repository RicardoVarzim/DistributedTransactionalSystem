/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import transaction.Transaction;
import transaction.SubTransaction;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ricardo
 */
public interface CoordinatorIf extends Remote {
    boolean newCohort(Cohort cohort) throws RemoteException;
    boolean transaction(ArrayList<SubTransaction> requets) throws RemoteException;
    CoordinatorLogger getLogger() throws RemoteException;
    void rollback() throws RemoteException;
    void setTransaction(Transaction transaction) throws RemoteException;
    void commit(Long id) throws RemoteException;
    Transaction getTransaction() throws RemoteException;
    List<CoordinatorLog> getLogItems(String id) throws RemoteException;
}
