/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import rmi.AccountIf;

/**
 *
 * @author ricardo
 */
public class Account extends UnicastRemoteObject implements AccountIf{

    private float balance;
    private String id;
    private String bank;
    private Connection connection;
    private PreparedStatement sqlStatement;
    
    public Account(String id, String bankname, float balance, Connection connection) throws RemoteException, SQLException{
        super();
        this.balance = balance;
        this.bank = bankname;
        this.id = id;
        this.connection = connection;
        
        try{
            sqlStatement = connection.prepareStatement("UPDATE " + bankname + " SET BALANCE =? WHERE id = '" + id +"'");
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage());
        }
    }

    Account(String accountId, String name, Connection connection) throws RemoteException, SQLException {
        this(accountId,name,1000,connection);
    }
    
    //<editor-fold desc="Account Interface Implementation">
    @Override
    public synchronized void deposit(float amount) throws RemoteException, Exception {
        //TODO: concorrency with lock for this
        if(amount < 0){
            throw new Exception("Negative amount");
        }
        
        boolean success = false;
        try {
            balance += amount;
            sqlStatement.setDouble(1, balance);
            int rows = sqlStatement.executeUpdate();
            if(rows != 1){
                throw new Exception("Deposit fail into account:" + bank +":" +id);
            } else{
                success = true;
            }
            System.out.println("Deposit into account:" + bank +":" +id +"\t amount:"+ amount +"\t balance:"+ balance);
        } catch (Exception e) {
            throw new Exception("Deposit fail into account:" + bank +":" +id);
        }
        finally{
            if(!success){
                balance -= amount;
            }
        }
    }

    @Override
    public synchronized void withdraw(float amount) throws RemoteException, Exception {
        if(amount < 0){
            throw new Exception("Negative amount");
        }
        
        if ((balance - amount) < 0){
            throw new Exception("Insuficient amount");
        }
        
        boolean success = false;
        try {
            balance -= amount;
            sqlStatement.setDouble(1, balance);
            int rows = sqlStatement.executeUpdate();
            if(rows != 1){
                throw new Exception("Withdraw fail into account:" + bank +":" +id);
            } else{
                success = true;
            }
            System.out.println("Withdraw into account:" + bank +":" +id +"\t amount:"+ amount +"\t balance:"+ balance);
        } catch (Exception e) {
            throw new Exception("Withdraw fail into account:" + bank +":" +id);
        }
        finally{
            if(!success){
                balance += amount;
            }
        }
    }

    @Override
    public float getAmount() throws RemoteException {
        return this.balance;
    }

    @Override
    public String getId() throws RemoteException {
        return this.id;
    }

    @Override
    public String getBank() throws RemoteException {
        return this.bank;
    }
    //</editor-fold>
    
}
