/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import rmi.BankIf;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author ricardo
 */
public class BankServer {

    private static String DBMS = "derby";
    private static int RMIPORT = 1234;
    
    public BankServer(String rminame, String datasource) throws Exception{
        try {
            LocateRegistry.createRegistry(RMIPORT);
            BankIf bank = new Bank("bank1", datasource, DBMS);
            Naming.rebind(rminame, bank);
            System.out.println(rminame +" ready");
        } catch (Exception ex) {
            System.err.println("Error registering rmi");
            throw ex;
        }
    }
    public static void main(String[] args) throws Exception{
        
        //TODO: atribuir diferentes url rmi's para cada Instancia
        System.out.println("java bankserver.BankServer [rmi url] [db]");
        
        String rminame = null;
        if(args.length >0)
            rminame = args[0];
        else
            rminame = "//localhost:"+RMIPORT+"/bank";
        
        String datasource = null;
        if(args.length > 1)
            datasource = args[1];
        else
            datasource = "bank";
        
        new BankServer(rminame, datasource);
    }
}
