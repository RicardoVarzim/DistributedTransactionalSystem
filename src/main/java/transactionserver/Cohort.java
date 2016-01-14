/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author ricardo
 */
public class Cohort extends UnicastRemoteObject implements CohortIf {
    
    private Coordinator coord;
    //private Connection con; //TODO rmi connection to the Bank?
    private String bankName;
    private CohortLogger logger;
    private boolean shouldNotCommit = false;

    
    
    public Cohort(String cordUrl, String user, String pass, String name, String coordAdress) throws RemoteException {
        this.bankName = name;
        this.logger = new CohortLogger(bankName);
        try{
            this.coord = (Coordinator)Naming.lookup(coordAdress);
            //this.con = DriverManager.getConnection(cordUrl, user, pass);
            //con.setAutoCommit(false);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean voteRequest(long id, String sql) throws RemoteException{
        System.out.println("Vote request ");
        logger.log(new CohortLog(id, CohortStatus.INIT));
        System.out.println("Logged status INIT: " + id);
        try {
            //Statement st = this.con.createStatement();
            System.out.println("Created statement");
            //int res = st.executeUpdate(sql);
//            if(res==1){
//                logger.log(new CohortLog(id, CohortStatus.READY));
//                System.out.println("ExecuteUpdate successful. ");
//                System.out.println("Logged status READY" + id);
//                return true;
//            }else{
//                System.out.println("ExecuteUpdate failure");
//                System.out.println("Logged status ABORT: " + id);
//                logger.log(new CohortLog(id, CohortStatus.ABORT));
//                return false;
//            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("ExecuteUpdate failure");
            System.out.println("Logged status ABORT: " + id);
            logger.log(new CohortLog(id, CohortStatus.ABORT));
            return false;
        }
    }

    @Override
    public boolean commit(long id) throws RemoteException {
        if(shouldNotCommit) return false;
        System.out.println("Logging status COMMIT: " + id);
        logger.log(new CohortLog(id, CohortStatus.COMMIT));
        try {
            //con.commit();
            System.out.println("Commit successful.");
            logger.log(new CohortLog(id, CohortStatus.FINISHED));
            System.out.println("Logging status FINISHED: " + id);
            return true;
        } catch (Exception e) {
            System.out.println("Commit failure. ");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getBankName() throws RemoteException {
         return this.bankName;
    }

    @Override
    public boolean roolback(long id) throws RemoteException {
        
    System.out.println("Logging status ABORT: " + id);
        logger.log(new CohortLog(id, CohortStatus.ABORT));
        try {
            //con.rollback();
            System.out.println("Rollback successful.");
            logger.log(new CohortLog(id, CohortStatus.FINISHED));
            System.out.println("Logging status FINISHED: " + id);
            return true;
        } catch (Exception e) {
            System.out.println("Rollback failure");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Coordinator getCoord() throws RemoteException {
        return coord;
    }

    @Override
    public void executeAndCommit(String sql) throws RemoteException {
        try{
//            Statement st = this.con.createStatement();
//            st.executeUpdate(sql);
//            con.commit();
            System.out.println("ExecuteAndCommit successful");

        }catch(Exception e){
            System.out.println("ExecuteAndCommit failure");
            e.printStackTrace();
        }
    }
    
    public void changeShouldCommit(){
        this.shouldNotCommit = !this.shouldNotCommit;
    }

    public enum CohortStatus {
        INIT,
        READY,
        ABORT,
        COMMIT,
        FINISHED
    }

    
}
