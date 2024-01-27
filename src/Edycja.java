import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Edycja extends JFrame {

    private JPanel Edycja;
    private JButton zapisz;
    private JButton dodaj;
    private JButton usun;
    private JButton wyczysc;
    private JTable Asortyment;
    private JTextField Producent;
    private JTextField Nazwa;
    private JTextField Pojemnosc;
    private JTextField Cena;
    private JButton Powrot;
    private JTextField NumerProduktu;
    private JButton Odswiez;
    private DefaultTableModel model;
    private String csvFilePath = "C:\\Users\\User\\IdeaProjects\\Automat\\src\\Asortyment.csv";

    public Edycja() {
        super("Edycja Asortymentu");
        this.setContentPane(this.Edycja);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 600, height = 400;
        this.setSize(width, height);
        this.setVisible(true);
        importCSV();


        dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String producent = Producent.getText();
                String nazwa = Nazwa.getText();
                String cena = Cena.getText();
                String pojemnosc = Pojemnosc.getText();
                String numerproduktu = NumerProduktu.getText();
                model = new DefaultTableModel();
                if (czyIstniejeNumer(numerproduktu)) {
                    JOptionPane.showMessageDialog(null, "Produkt o podanym numerze już istnieje.");
                    return;
                }
                if (!producent.isEmpty() && !nazwa.isEmpty() && !pojemnosc.isEmpty() && !cena.isEmpty() && !numerproduktu.isEmpty()) {
                    model.addRow(new String[]{producent, nazwa, cena, pojemnosc, numerproduktu});
                    try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath, true))) {
                        writer.println(producent + "," + nazwa + "," + cena + "," + pojemnosc+ ","+ numerproduktu);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Błąd podczas dodawania");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Proszę wypełnić wszystkie pola");
                }
            }
        });


        usun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = Asortyment.getSelectedRow();
                if (selectedRowIndex >= 0) {
                    model.removeRow(selectedRowIndex);
                    zapiszDoPliku();
                } else {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać wiersz do usunięcia.");
                }
            }
        });

        zapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = Asortyment.getSelectedRow();
                if (selectedRowIndex >= 0) {
                    String producent = Producent.getText();
                    String nazwa = Nazwa.getText();
                    String pojemnosc = Pojemnosc.getText();
                    String cena = Cena.getText();
                    String numerProduktu = NumerProduktu.getText();
                    if (!producent.isEmpty() && !nazwa.isEmpty() && !pojemnosc.isEmpty() && !cena.isEmpty() && !numerProduktu.isEmpty()) {
                        model.setValueAt(producent, selectedRowIndex, 0);
                        model.setValueAt(nazwa, selectedRowIndex, 1);
                        model.setValueAt(cena, selectedRowIndex, 2);
                        model.setValueAt(pojemnosc, selectedRowIndex, 3);
                        model.setValueAt(numerProduktu, selectedRowIndex, 4);
                        zapiszDoPliku();
                    } else {
                        JOptionPane.showMessageDialog(null, "Proszę wypełnić wszystkie pola.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać wiersz do edycji.");
                }
            }
        });
        wyczysc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producent.setText("");
                Nazwa.setText("");
                Pojemnosc.setText("");
                Cena.setText("");
                NumerProduktu.setText("");
            }
        });
        Powrot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminPanel Adm = new AdminPanel();
                dispose();
            }
        });



        Odswiez.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Asortyment();
            }
        });
        Asortyment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = Asortyment.rowAtPoint(e.getPoint());
                int col = Asortyment.columnAtPoint(e.getPoint());
                if (row >= 0 && col >= 0) {
                    String producent = model.getValueAt(row, 0).toString();
                    String nazwa = model.getValueAt(row, 1).toString();
                    String cena = model.getValueAt(row, 2).toString();
                    String pojemnosc = model.getValueAt(row, 3).toString();
                    String numerProduktu = model.getValueAt(row, 4).toString();
                    Producent.setText(producent);
                    Nazwa.setText(nazwa);
                    Pojemnosc.setText(pojemnosc);
                    Cena.setText(cena);
                    NumerProduktu.setText(numerProduktu);
                }
            }
        });


        Cena.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String filterStr = "0123456789.";
                char c = e.getKeyChar();
                if(filterStr.indexOf(c)<0){
                    e.consume();
                }
                }

        });
        Pojemnosc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String filterStr = "0123456789.";
                char c = e.getKeyChar();
                if(filterStr.indexOf(c)<0){
                    e.consume();
                }
            }
        });
        NumerProduktu.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(!Character.isDigit(c)){
                    e.consume();
                }
            }
        });
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
        model = new DefaultTableModel();
        model.addColumn("Producent");
        model.addColumn("Nazwa");
        model.addColumn("Cena");
        model.addColumn("Pojemność Napoju");
        model.addColumn("Numer Produktu");
        for (String[] row : data) {
            model.addRow(row);
        }
        Asortyment.setModel(model);
    }

    private boolean czyIstniejeNumer(String numerProduktu) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].equals(numerProduktu)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void Asortyment() {
        importCSV();
    }
    private void zapiszDoPliku() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    rowBuilder.append(model.getValueAt(i, j));
                    if (j < model.getColumnCount() - 1) {
                        rowBuilder.append(",");
                    }
                }
                writer.println(rowBuilder.toString());
            }
            JOptionPane.showMessageDialog(null, "Pomyślnie edytowano.");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Błąd podczas zapisywania do pliku.");
        }
    }

    
   }
