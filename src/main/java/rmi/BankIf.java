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
public interface BankIf extends Remote{
    
    public AccountIf findAccount(String accountId) throws RemoteException;
    
    public AccountIf makeAccount() throws RemoteException;
    
    public boolean deleteAccount() throws RemoteException;
    
    public String[] listAccounts() throws RemoteException;
}
