/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import java.io.Console;
import rmi.BankIf;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import rmi.CoordinatorIf;

/**
 *
 * @author ricardo
 */
public class BankServer {

    private static String DBMS = "derby";
    private static int RMIPORT = 1234;
    
    public BankServer(String rminame, String datasource, String bankId) throws Exception{
        int triesLeft = 10;
        boolean ready = false;
        BankIf bank = null;
        
        //Register RMI Server
        while (triesLeft > 0 && !ready) {            
            
            try {
                LocateRegistry.createRegistry(RMIPORT);
                bank = new Bank(bankId, datasource, DBMS);
                
                Naming.rebind(rminame, bank);
                System.out.println(rminame +" ready");
                ready = true;
            } catch (Exception ex) {
                System.out.println("Error registering rmi");
                if(triesLeft > 1){
                    RMIPORT += 1;
                    rminame = "rmi://localhost:"+RMIPORT+"/bank";
                    triesLeft -= 1;
                    System.out.println("Retry register at port " + RMIPORT);
                }
                else{
                    System.out.println("Failed to register RMI server");
                    ready = true;
                    System.exit(0);
                }
            }
        }
        
        //Register in Transaction Server
        try {
            String rmiTransaction = "//localhost:1235/transaction";
            CoordinatorIf coordinator = (CoordinatorIf) Naming.lookup("rmi:"+rmiTransaction);
            System.out.println("Transaction Server Ready.");
            System.out.println("Registering Bank RMI server on Transaction Server.");
            if(coordinator.newBank(bank))
                System.out.println("Bank available for transaction.");
            else
                System.out.println("Error registering Bank on Transaction Server.");
        } catch (Exception e) {
            System.out.println("Transaction Server NOT FOUND.");
            //e.printStackTrace();
            System.exit(0);
        }
        
    }
    public static void main(String[] args) throws Exception{
        
        Scanner s = new Scanner(System.in);
        
        System.out.println("java bankserver.BankServer [rmi url] [db]");
        
        String rminame = null;
        if(args.length >0)
            rminame = args[0];
        else
            rminame = "rmi://localhost:"+RMIPORT+"/bank";
        
        String datasource = null;
        if(args.length > 1)
            datasource = args[1];
        else
            datasource = "bank";
        
        System.out.println("Insira um ID para o banco:");
        String bankId = s.nextLine();
        
        new BankServer(rminame, datasource,bankId);
    }
}
