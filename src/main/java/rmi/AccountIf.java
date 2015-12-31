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
public interface AccountIf extends Remote {
    
    void deposit(int amount) throws RemoteException;
    void withdraw(int amount) throws RemoteException;
    
    int getAmount() throws RemoteException;

    String getId() throws RemoteException;
    
}
