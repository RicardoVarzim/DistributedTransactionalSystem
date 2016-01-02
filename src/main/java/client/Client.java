/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.AccountIf;
import rmi.ClientIf;

/**
 *
 * @author ricardo
 */
public class Client extends UnicastRemoteObject implements ClientIf {

    private String name;
    private String pass;
    
    Client(String name, String pass) throws RemoteException{
        this.name = name;
        this.pass = pass;
    }
    
    @Override
    public void notifyTransaction(AccountIf acc1, AccountIf acc2, double amount, Boolean state) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPassword() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
