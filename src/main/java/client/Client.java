/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.rmi.Naming;
import rmi.CoordinatorIf;

/**
 *
 * @author ricardo
 */
public class Client {
    
    public static void main(String[] args) throws Exception{
        try {
            String rmiTransaction = "//localhost:1235/transaction";
            CoordinatorIf coordinator = (CoordinatorIf) Naming.lookup("rmi:"+rmiTransaction);
            //System.out.println(coordinator);
            MainWindow gui = new MainWindow(coordinator);
            gui.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
