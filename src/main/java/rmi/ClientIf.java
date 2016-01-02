/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ricardo
 */

public interface ClientIf extends Remote {
    
    
    //public AccountIf findAccount(String accountId) throws RemoteException; //this should be done on the bank server
    
    //public double getBalance(AccountIf acc) throws RemoteException; //this should be done on the bank server
    
    
    public void notifyTransaction(AccountIf acc1, AccountIf acc2, double amount, Boolean state) throws RemoteException;//notify and update section log maybe
    
    //MAYBE IMPLEMENT THIS
    public String getName() throws RemoteException;
    public String getPassword() throws RemoteException;
    
}
