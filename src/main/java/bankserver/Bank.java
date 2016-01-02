/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import rmi.AccountIf;
import rmi.BankIf;

/**
 *
 * @author ricardo
 */
public class Bank extends UnicastRemoteObject implements BankIf {
    
    private String name;
    private Map<String, AccountIf> accounts = new HashMap<String,AccountIf>();
    private Statement sqlStatement;
    private String datasource = "myBankDB";
    private String dbms = "derby";
    
    public Bank(String name, String datasource, String dbms) throws RemoteException{
        super();
        this.name = name;
        this.datasource = datasource;
        this.dbms = dbms;
        createDB();
    }
    
    public Bank(String name) throws RemoteException{
        super();
        this.name = name;
        createDB();
    }
    
    

    
    @Override
    public AccountIf findAccount(String accountId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AccountIf makeAccount() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAccount() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] listAccounts() throws RemoteException {
        return accounts.keySet().toArray(new String[1]);
    }

    private void createDB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
