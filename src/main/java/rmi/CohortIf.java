/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import transactionserver.Coordinator;

/**
 *
 * @author ricardo
 */
public interface CohortIf extends Remote {
    
    boolean voteRequest(long id, float value, boolean balance) throws RemoteException;
    boolean commit(long id) throws RemoteException;
    String getBankName() throws RemoteException;
    boolean roolback(long id) throws RemoteException;
    Coordinator getCoord() throws RemoteException;
    void executeAndCommit(String sql) throws RemoteException;
}
