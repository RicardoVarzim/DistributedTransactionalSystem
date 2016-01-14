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
import transactionserver.CoordinatorIf;

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
        JButton but1 = new JButton("Transaction 1");
        but1.addActionListener(e -> {
            try {
                SubTransaction trans1 = new SubTransaction("BilDB", "INSERT INTO biler VALUES(DEFAULT,'brafarg')");
                SubTransaction trans2 = new SubTransaction("FlyDB", "INSERT INTO biletter VALUES(DEFAULT,'passasjer')");
                ArrayList<SubTransaction> sts = new ArrayList<>();
                sts.add(trans1);
                sts.add(trans2);

                boolean answer = coordinator.transaction(sts);
                //this can be used in the application for further validation
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        add(but1);

        JButton but2 = new JButton("Transaction 2");
        but2.addActionListener(e -> {
            try {
                SubTransaction trans1 = new SubTransaction("BilDB", "INSERT INTO biler VALUES(DEFAULT,'Fin Farge')");
                //Changed ".. INTO biletter" to "... INTO baasdiletter", to manually force an error.
                SubTransaction trans2 = new SubTransaction("FlyDB", "INSERT INTO baasdiletter VALUES(DEFAULT,'passasjer')");
                ArrayList<SubTransaction> sts = new ArrayList<>();
                sts.add(trans1);
                sts.add(trans2);

                boolean answer = coordinator.transaction(sts);
                //this can be used in the application for further validation
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        add(but2);
        pack();
    }
    
}
