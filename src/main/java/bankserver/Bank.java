/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import bankserver.Account;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.AccountIf;
import rmi.BankIf;

/**
 *
 * @author ricardo
 */
public class Bank extends UnicastRemoteObject implements BankIf {
    private AccountIf[] accounts;
    private int currentId;
    
    public Bank() throws RemoteException{
        this.currentId = 0;
    }
    
    public Bank(AccountIf[] accounts) throws RemoteException{
        this.accounts = accounts;
        this.currentId = 0;
    }

    @Override
    public AccountIf find(Integer accountId) throws RemoteException {
        for(AccountIf a : accounts){
            if(a.getId().equals(accountId))
                return a;
        }
        return null;
    }

    @Override
    public AccountIf makeAccount() throws RemoteException {
        this.currentId++;
        return new Account(String.valueOf(currentId));
    }
}
