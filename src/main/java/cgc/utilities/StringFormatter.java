package cgc.utilities;

public class StringFormatter {
    public static String formatString(String input) {
        return input.replace(" ", "+").replace(",", "%2C");
    }
}
