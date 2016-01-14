/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ricardo
 */
public interface AccountIf extends Remote {
    
    void deposit(float amount) throws RemoteException, Exception;
    void withdraw(float amount) throws RemoteException, Exception;
    
    float getAmount() throws RemoteException;

    String getId() throws RemoteException;
    String getBank() throws  RemoteException;
}
