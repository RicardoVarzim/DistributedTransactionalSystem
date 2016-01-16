/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import rmi.AccountIf;
import rmi.BankIf;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ricardo
 */
public class Bank extends UnicastRemoteObject implements BankIf {
    
    private String name;
    private Map<String, AccountIf> accounts = new HashMap<String,AccountIf>();
    private Statement sqlStatement;
    private String datasource = "bank";
    private String dbms = "derby";
    private Connection con;
    
    public Bank(String name, String datasource, String dbms) throws RemoteException{
        super();
        this.name = name;
        this.datasource = datasource;
        this.dbms = dbms;
        createDB();
        try {
            //default Account
            if(findAccount("0000") == null)
                makeAccount("0000");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Bank(String name) throws RemoteException{
        super();
        this.name = name;
        createDB();
    }
    
    //<editor-fold desc="Bank Interface Implementation">
    @Override
    public AccountIf findAccount(String accountId) throws RemoteException, Exception {
        
        if(accountId == null)
            return null;
        
        AccountIf tempAccount = accounts.get(accountId);
        if(tempAccount == null){
            
            try {
                String sql = "SELECT * FROM " + name + " WHERE ID='" + accountId + "'";
                ResultSet result = sqlStatement.executeQuery(sql);

                if(result.next()){
                    tempAccount = new Account(result.getString("ID"), name, result.getFloat("BALANCE"),getConnection());
                    result.close();

                    accounts.put(name,tempAccount);
                }
                else
                    return null;
            } catch (Exception e) {
                throw new Exception("Error finding account " + accountId,e);
            }
        }
        return tempAccount;
    }

    @Override
    public AccountIf makeAccount(String accountId) throws RemoteException, Exception {
        Account tempAccount = (Account) accounts.get(accountId);
        if(tempAccount != null){
            System.out.println("Already exists Account " +accountId);
            throw new Exception("Already exists Account " +accountId);
        }
        ResultSet result = null;
        try {
            result = sqlStatement.executeQuery("SELECT * from "+name+" WHERE ID = '"+accountId+"' ");
            
            if(result.next()){
                //account exists
                tempAccount = new Account(accountId, name, result.getFloat("balance"),getConnection());
                accounts.put(accountId, tempAccount);
                throw new Exception("Already exists Account " +accountId);
                //System.out.println("Already exists Account " +accountId);
                
            }
            result.close();
            
            int rows = sqlStatement.executeUpdate("INSERT INTO "+name+" VALUES ( '"+accountId+"' ,1000)");
            
            if(rows == 1){
                tempAccount = new Account(accountId, name, getConnection());
                accounts.put(name, tempAccount);
                System.out.println("Account " + accountId + " created");
                return tempAccount;
            }
            else{
               throw new Exception("Cannot create " +accountId); 
            }
                    
        } catch (Exception e) {
            throw new Exception("Cannot create " +accountId,e); 
        }
    }

    @Override
    public boolean deleteAccount(String accountId) throws RemoteException, Exception {
        if(accounts.get(accountId)!= null)
            accounts.remove(accountId);
        try {
            int rows = sqlStatement.executeUpdate("DELETE FROM " + name + " WHERE ID='" + accountId +"'");
            if(rows !=1)
                throw new Exception("Cannot delete " +accountId);
        } catch (Exception e) {
            System.out.println("Cannot delete "+accountId);
            throw new Exception("Cannot delete "+ accountId,e);
        }
        System.out.println("Account " + accountId + " deleted!");
        return true;
    }

    @Override
    public List<String> listAccounts() throws RemoteException {
        ArrayList<String> values = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + name + ";";
            ResultSet result = sqlStatement.executeQuery(sql);
            
            while(result.next()){
                values.add(result.getString("ID"));
                result.close();
            }
            
        } catch (Exception e) {
            return null;
        }
        return values ;
    }
    //</editor-fold>
    
    private void createDB() {
        try {
            con = getConnection();
            con.setAutoCommit(true);
            sqlStatement = con.createStatement();
            boolean exists = ExistsDatabase(con);
            if(!exists){
                sqlStatement.executeUpdate("CREATE TABLE "+name+" (Id VARCHAR(32) PRIMARY KEY, Balance FLOAT)");
            }
        } catch (Exception e) {
            System.err.println("Error creating DB.");
            System.exit(1);
        }
    }

    private boolean ExistsDatabase(Connection connection) throws SQLException {
        //TODO: REFACTOR THIS ONE
        DatabaseMetaData dbm = connection.getMetaData();
        boolean existsDb = false;
        for(ResultSet rs = dbm.getTables(null, null, null, null); rs.next();){
            String tableName = rs.getString(3);
            if(tableName.equalsIgnoreCase(name)){
                existsDb = true;
                rs.close();
                break;
            }
        }
        return existsDb;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        //Class.forName("org.apache.derby.jdbc.ClientXADataSource");
        return DriverManager.getConnection("jdbc:derby://localhost:1527/"+ datasource );
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

}
