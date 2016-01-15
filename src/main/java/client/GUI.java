/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import transaction.SubTransaction;
import rmi.CoordinatorIf;

/**
 *
 * @author ricardo
 */
class GUI extends JFrame{

    private final CoordinatorIf coordinator;

    GUI(CoordinatorIf coordinator) {
        super()
                ;
        this.coordinator = coordinator;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JButton but1 = new JButton("bank1 -> bank2");
        but1.addActionListener(e -> {
            try {
                SubTransaction trans1 = new SubTransaction("bank1", "0000", 100, true);
                SubTransaction trans2 = new SubTransaction("bank2", "0000", 100, false);
                ArrayList<SubTransaction> sts = new ArrayList<>();
                sts.add(trans1);
                sts.add(trans2);

                boolean answer = coordinator.transaction(sts);
                if(answer)
                    System.out.println("Transferência efectuada!");
                else 
                    System.out.println("Falha na Transferência!");
                //this can be used in the application for further validation
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        add(but1);

        JButton but2 = new JButton("bank1 <- bank2");
        but2.addActionListener(e -> {
            try {
                SubTransaction trans1 = new SubTransaction("bank1", "0000", 100, false);
                SubTransaction trans2 = new SubTransaction("bank2", "0000", 100, true);
                ArrayList<SubTransaction> sts = new ArrayList<>();
                sts.add(trans1);
                sts.add(trans2);

                boolean answer = coordinator.transaction(sts);
                
                if(answer)
                    System.out.println("Transferência efectuada!");
                else 
                    System.out.println("Falha na Transferência!");
                //this can be used in the application for further validation
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        add(but2);
        pack();
    }
    
}
