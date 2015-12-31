package rmi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ricardo
 */
public interface TransactionIf extends Remote {
    
    public TransactionIf initTransaction(AccountIf acc1, AccountIf acc2, double amount)
            throws RemoteException; 
    
    public void RegisterTransaction() 
            throws RemoteException;
}
