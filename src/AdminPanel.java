import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminPanel extends JFrame {

    private JButton edytuj;
    private JButton historia;
    private JPanel AdminP;
    private JButton Powrot;

    public AdminPanel() {
        super("Admin Panel");
        this.setContentPane(this.AdminP);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 600, height = 400;
        this.setSize(width, height);
        this.setVisible(true);


        edytuj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Edycja Edy = new Edycja();
                Edy.setVisible(true);
                dispose();
            }
        });


        historia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Historia His = new Historia();
                His.setVisible(true);
                dispose();
            }
        });

        Powrot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutomatS Aut = new AutomatS();
                dispose();
            }
        });
    }

    }



