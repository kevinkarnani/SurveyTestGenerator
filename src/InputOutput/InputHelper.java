package InputOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

public class InputHelper {
    // If file is null, read from System.in
    public static String readStr(String file) {
        BufferedReader in;
        String line = "-1";
        try {
            if (file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            // Verify the input exists
            while (line == null || line.length() == 0) {
                Out.getInstance().say("Please enter valid input of at least 1 char");
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static int readCharAsInt(String file, int size) {
        BufferedReader in;
        String line = "-1";
        try {
            if (file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            // Verify the input exists
            while (line == null || !line.matches("[A-Za-z]") || line.length() != 1 || (line.toUpperCase().charAt(0) - 65) >= size) {
                Out.getInstance().say("Please enter valid input of only 1 char in the valid range");
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (line.toUpperCase().charAt(0) - 65);
    }

    // If file is null, read from System.in
    public static int readInt(String file) {
        BufferedReader in;
        String line = "-1";
        try {
            if (file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            while (line == null || line.length() == 0 || !isInt(line)) {
                Out.getInstance().say("Please enter a valid int");
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(line);
    }

    // If file is null, read from System.in
    public static double readDouble(String file) {
        BufferedReader in;
        String line = "-1";
        try {
            if (file == null)
                in = new BufferedReader(new InputStreamReader(System.in));
            else
                in = new BufferedReader(new FileReader(file));
            line = in.readLine();
            while (line == null || line.length() == 0 || !isDouble(line)) {
                Out.getInstance().say("Please enter a valid double");
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Double.parseDouble(line);
    }

    // Validate input as an int between inclusive range
    public static int readIntInRange(int start, int end) {
        String failSpeech = "Please enter a valid number between " + start + " - " + end;
        // BufferedReader is better than Scanner. Prove me wrong.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = "z";  // Some non-integer value to fail parsing if checks are missed
        try {
            line = br.readLine();
            while (
                    line == null
                            || line.length() <= 0
                            || !isIntBetweenInclusive(start, end, line)
            ) {
                System.out.println(failSpeech);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //noinspection ConstantConditions
        return Integer.parseInt(line);
    }

    public static String readDate(String file) {
        String failSpeech = "Please enter a valid date of form 'yyyy-mm-dd' only.";
        // BufferedReader is better than Scanner. Prove me wrong.
        BufferedReader br;
        String line = "z";  // Some date to fail parsing if checks are missed
        try {
            if (file == null)
                br = new BufferedReader(new InputStreamReader(System.in));
            else
                br = new BufferedReader(new FileReader(file));
            line = br.readLine();
            while (!isValidDate(line)) {
                System.out.println(failSpeech);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    // Returns true if the input can be parsed to an int, else false
    private static boolean isInt(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Returns true if the input can be parsed to a double, else false
    private static boolean isDouble(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate that a String is a valid integer between an inclusive range
     *
     * @param start The start of the inclusive range
     * @param end   The end of the inclusive range
     * @param num   The String to be validated
     * @return True if it's a valid int between the inclusive range, else false
     */
    public static boolean isIntBetweenInclusive(int start, int end, String num) {
        if (!isInt(num)) return false;
        int val = Integer.parseInt(num);
        return val >= start && val <= end;
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (Exception pe) {
            return false;
        }
        return true;
    }
}
