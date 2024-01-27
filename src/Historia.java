import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Historia extends JFrame {

    private JTable table1;
    private JButton powrot;
    private JPanel HistoriaTA;
    private String historyFilePath = "C:\\Users\\User\\IdeaProjects\\Automat\\src\\Historiazakupów.csv";

    public Historia() {
        super("Historia Transakcji");
        this.setContentPane(this.HistoriaTA);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 600, height = 400;
        this.setSize(width, height);
        this.setVisible(true);
        dispose();
        importCSV();
        powrot.addActionListener(e -> {
            AdminPanel Adm = new AdminPanel();
            dispose();

        });
    }

    private void updateTable(String[][] data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Producent");
        model.addColumn("Nazwa");
        model.addColumn("Cena");
        model.addColumn("Data");
        model.addColumn("Godzina");
        for (String[] row : data) {
            model.addRow(row);
        }
        table1.setModel(model);
    }

    private void importCSV() {
        File file = new File(historyFilePath);
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

}