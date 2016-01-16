/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import rmi.TransactionManagerIf;

/**
 *
 * @author ricardo
 */
public class TransactionServer {
    
    private static String DBMS = "derby";
    private static int RMIPORT = 1235;
    private String datasource = "log";
    private TransactionManagerIf coordinator;
    
    private TransactionServer(TransactionManagerIf coordinator, String datasource) {
        this.coordinator = coordinator;
        this.datasource = datasource;
    }
    
    public static void main(String[] args) throws Exception{
        try {
            
            System.out.println("java transactionserver.TransactionServer [rmi url] [db]");

            String rminame = null;
            if(args.length >0)
                rminame = args[0];
            else
                rminame = "//localhost:"+RMIPORT+"/transaction";

            String datasource = null;
            if(args.length > 1)
                datasource = args[1];
            else
                datasource = "log";

            LocateRegistry.createRegistry(RMIPORT);
            TransactionManagerIf coordinator = new TransactionManager();
            Naming.rebind("rmi:"+rminame,coordinator);
            System.out.println("RMI transaction Manager object registered");
            System.out.println("URL: rmi:"+rminame);
            new TransactionServer(coordinator, datasource);
            
        } catch (Exception e) {
            throw e;
        }
        
    }

    
}
