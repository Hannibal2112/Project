import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AutomatS extends  JFrame {

    private JButton a5złButton;
    private JButton a50grButton;
    private JButton a5grButton;
    private JButton a2złButton;
    private JButton a1złButton;
    private JButton a20grButton;
    private JButton a10grButton;
    private JButton a2grButton;
    private JButton a1grButton;
    private JPanel AutomatS;
    private JTextField NumerProduktu;
    private JButton zakupButton;
    private JButton a3Button;
    private JButton a2Button;
    private JButton a1Button;
    private JButton a6Button;
    private JButton a5Button;
    private JButton a4Button;
    private JButton a8Button;
    private JButton a0Button;
    private JButton a7Button;
    private JButton a9Button;
    private JTable table1;
    private JButton Admin;
    private JTextField Cena;
    private JButton anulujZakupButton;
    private JButton czyistnieje;
    private JButton wyjscie;
    private String csvFilePath = "C:\\Users\\User\\IdeaProjects\\Automat\\src\\Asortyment.csv";
    private String historyFilePath = "C:\\Users\\User\\IdeaProjects\\Automat\\src\\Historiazakupów.csv";

    public AutomatS() {
        super("Autmat Sprzedażowy");
        this.setContentPane(this.AutomatS);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 600, height = 800;
        this.setSize(width, height);
        this.setVisible(true);
        importCSV();
        NumerProduktu.setEditable(false);
        Cena.setEditable(false);
        Admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login2 Log = new Login2();
                dispose();
            }
        });

        czyistnieje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numerProduktu = NumerProduktu.getText();
                if (!numerProduktu.isEmpty()) {
                    String cena = znajdzCeneProduktu(numerProduktu);
                    if (cena != null) {
                        Cena.setText(cena);
                    } else {
                        JOptionPane.showMessageDialog(null, "Produkt o podanym numerze nie istnieje.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Wprowadź numer produktu.");
                }
            }
        });

        zakupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cenaText = Cena.getText().trim().replace(',', '.');
                double cena = Double.parseDouble(cenaText);
                if (cena < 0) {
                    String komunikat = Reszta.wydajReszte(-cena);
                    JOptionPane.showMessageDialog(null, "Dziękujemy za transakcję. " + komunikat);
                    String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String godzina = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    String producent = "";
                    String nazwaNapoju = "";
                    String cenaNapoju = "";
                    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] parts = line.split(",");
                            if (parts.length >= 5 && parts[4].equals(NumerProduktu.getText())) {
                                producent = parts[0];
                                nazwaNapoju = parts[1];
                                cenaNapoju = parts[2];
                                break;
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Błąd podczas odczytu pliku Asortyment.csv");
                    }

                    String[] transakcja = {producent, nazwaNapoju, cenaNapoju, data, godzina};

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFilePath, true))) {
                        writer.write(String.join(",", transakcja));
                        writer.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Błąd podczas zapisywania do pliku Historiazakupów.csv");
                    }
                    Cena.setText("0.00");
                    NumerProduktu.setText("");
                } else if (cena == 0) {
                    JOptionPane.showMessageDialog(null, "Dziękujemy za transakcję");
                    String producent = "";
                    String nazwaNapoju = "";
                    String cenaNapoju = "";
                    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] parts = line.split(",");
                            if (parts.length >= 5 && parts[4].equals(NumerProduktu.getText())) {
                                producent = parts[0];
                                nazwaNapoju = parts[1];
                                cenaNapoju = parts[2];
                                break;
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Błąd podczas odczytu pliku Asortyment.csv");
                    }
                    String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String godzina = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    String[] transakcja = {producent, nazwaNapoju, cenaNapoju, data, godzina};
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFilePath, true))) {
                        writer.write(String.join(",", transakcja));
                        writer.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Błąd podczas zapisywania do pliku Historiazakupów.csv");
                    }
                    Cena.setText("0.00");
                    NumerProduktu.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Nie udało się przeprowadzić transakcji, wrzucono za małą kwotę");
                }
            }
        });
        anulujZakupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cena.setText("0.00");
                NumerProduktu.setText("");
            }
        });
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("1");
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("2");
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("3");
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("4");
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("5");
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("6");
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("7");
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("8");
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("9");
            }
        });
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajnumerproduktu("0");
            }
        });

        a1grButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odejmij(0.01);
            }
        });
        a2grButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odejmij(0.02);
            }
        });
        a5grButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odejmij(0.05);
            }
        });
        a10grButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odejmij(0.1);
            }
        });
        a20grButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odejmij(0.2);
            }
        });
        a50grButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odejmij(0.5);
            }
        });
        a1złButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odejmij(1.0);
            }
        });
        a2złButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odejmij(2.0);
            }
        });
        a5złButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                odejmij(5.0);
            }
        });
        wyjscie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void dodajnumerproduktu(String text) {
        String currentText = NumerProduktu.getText();
        NumerProduktu.setText(currentText + text);
    }


    public static void main(String[] args) {
        AutomatS Aut = new AutomatS();
        Aut.setVisible(true);
    }

    private String znajdzCeneProduktu(String numerProduktu) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].equals(numerProduktu)) {
                    if (parts.length >= 3) {
                        return parts[2];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void importCSV() {
        File file = new File(csvFilePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String[]> dataList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                dataList.add(data);
            }
            String[][] dataArray = new String[dataList.size()][];
            dataList.toArray(dataArray);
            updateTable(dataArray);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateTable(String[][] data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Producent");
        model.addColumn("Nazwa");
        model.addColumn("Cena");
        model.addColumn("Pojemność Napoju");
        model.addColumn("Numer Produktu");
        for (String[] row : data) {
            model.addRow(row);
        }
        table1.setModel(model);
    }

    private void odejmij(double amount) {
        String currentPriceStr = Cena.getText().trim();
        try {
            double currentPrice = Double.parseDouble(currentPriceStr.replace(',', '.')); // Zamiana przecinka na kropkę
            currentPrice -= amount;

            DecimalFormat df = new DecimalFormat("#0.00");
            Cena.setText(df.format(currentPrice).replace('.', ','));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Błąd przetwarzania kwoty. Upewnij się, że wprowadzona kwota jest liczbą.");
        }
    }

    }





