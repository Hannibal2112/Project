import java.util.HashMap;
import java.util.Map;

public class Reszta {
    private static final double[] NOMINALY = {5.0, 2.0, 1.0, 0.5, 0.2, 0.1, 0.05, 0.02, 0.01};
    private static final String[] NOMINALY_STR = {"5 zł", "2 zł", "1 zł", "50 gr", "20 gr", "10 gr", "5 gr", "2 gr", "1 gr"};

    public static String wydajReszte(double reszta) {
        StringBuilder komunikat = new StringBuilder("Oto Twoja reszta:\n");

        Map<Double, Integer> wydaneNominaly = new HashMap<>();

        for (int i = 0; i < NOMINALY.length; i++) {
            int iloscNominalow = (int) (reszta / NOMINALY[i]);
            if (iloscNominalow > 0) {
                wydaneNominaly.put(NOMINALY[i], iloscNominalow);
                reszta -= iloscNominalow * NOMINALY[i];
            }
        }

        for (Map.Entry<Double, Integer> entry : wydaneNominaly.entrySet()) {
            komunikat.append(entry.getValue()).append("x ").append(NOMINALY_STR[znajdzIndeksNominalu(entry.getKey())]).append("\n");
        }

        return komunikat.toString();
    }
    private static int znajdzIndeksNominalu(double nominal) {
        for (int i = 0; i < NOMINALY.length; i++) {
            if (NOMINALY[i] == nominal) {
                return i;
            }
        }
        return -1;
    }
}