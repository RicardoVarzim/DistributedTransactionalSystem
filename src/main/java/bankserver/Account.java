/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.AccountIf;

/**
 *
 * @author ricardo
 */
public class Account extends UnicastRemoteObject implements AccountIf{

    protected Account(String id) throws RemoteException{
        super();
        this.amount = 0;
        this.Id = id;
    }
    
    private int amount;
    private String Id;
    
    @Override
    public void deposit(int amount) throws RemoteException {
        this.amount += amount;
    }

    @Override
    public void withdraw(int amount) throws RemoteException {
        this.amount -= amount;
    }

    @Override
    public int getAmount() throws RemoteException {
        return this.amount;
    }

    @Override
    public String getId() throws RemoteException {
        return this.Id;
    }
    
}
