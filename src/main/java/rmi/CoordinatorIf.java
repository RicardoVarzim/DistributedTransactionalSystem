/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import transaction.Transaction;
import transaction.SubTransaction;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import transactionserver.CoordinatorLog;
import transactionserver.CoordinatorLogger;

/**
 *
 * @author ricardo
 */
public interface CoordinatorIf extends Remote {
    //boolean newCohort(Cohort cohort) throws RemoteException;
    boolean newBank(BankIf bank) throws RemoteException;
    boolean transaction(ArrayList<SubTransaction> requets) throws RemoteException;
    CoordinatorLogger getLogger() throws RemoteException;
    void rollback() throws RemoteException;
    void setTransaction(Transaction transaction) throws RemoteException;
    Transaction getTransaction() throws RemoteException;
    List<CoordinatorLog> getLogItems(String id) throws RemoteException;
}
