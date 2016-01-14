/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

/**
 *
 * @author ricardo
 */
public class TransactionServer {
    
    private static String DBMS = "derby";
    private static int RMIPORT = 1234;
    
    private TransactionServer(String rminame, String datasource) {
        //TODO:
    }
    
    public static void main(String[] args) throws Exception{
        
        System.out.println("java transactionserver.TransactionServer [rmi url] [db]");
        
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
        
        new TransactionServer(rminame, datasource);
    }

    
}
