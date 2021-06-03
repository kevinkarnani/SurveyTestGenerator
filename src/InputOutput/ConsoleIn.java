package InputOutput;

/**
 * Please note this class is here as an example and the error checking is minimal.
 *
 * @author Sean Grimes, sean@seanpgrimes.com
 * Edited by Kevin Karnani, adds some functionality
 */
public class ConsoleIn extends In {
    // The instance that's returned in the getInstance() call
    private static ConsoleIn instance;

    // No public c'tor to fit the singleton requirements
    private ConsoleIn() {
    }

    // Return a ConsoleIn object in a threadsafe manner with lazy initialization
    public static ConsoleIn getInstance() {
        if (instance == null) {
            synchronized (ConsoleIn.class) {
                if (instance == null)
                    instance = new ConsoleIn();
            }
        }
        return instance;
    }

    // Read a string from the console
    public String readStr() {
        return InputHelper.readStr(null);
    }

    // Read an int, keep asking for a valid one while it's invalid
    public int readInt() {
        return InputHelper.readInt(null);
    }

    // Read a valid double, keep asking while it's invalid
    public double readDouble() {
        return InputHelper.readDouble(null);
    }

    public int readIntInRange(int start, int end) {
        return InputHelper.readIntInRange(start, end);
    }

    public int readCharAsInt(int size) {
        return InputHelper.readCharAsInt(null, size);
    }

    public String readDate() {
        return InputHelper.readDate(null);
    }
}
