import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login2 extends  JFrame {

    private JTextField Login;
    private JPasswordField Haslo;
    private JButton zaloguj;
    private JPanel Login2;
    private JButton anulujButton;

    public Login2() {
        super("Logowanie");
        this.setContentPane(this.Login2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 600, height = 400;
        this.setSize(width, height);
        this.setVisible(true);

        zaloguj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = Login.getText();
                String password = new String(Haslo.getPassword());
                if (username.equals("Admin") && password.equals("Admin")) {
                    JOptionPane.showMessageDialog(Login2.this, "Zalogowano jako Admin!");
                    AdminPanel adminPanel = new AdminPanel();
                    adminPanel.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login2.this, "Błędna nazwa użytkownika lub hasło!", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        anulujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutomatS AutomatS = new AutomatS();
                AutomatS.setVisible(true);
                dispose();
            }
        });
    }



    }
