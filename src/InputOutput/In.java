package InputOutput;

/**
 * Please note this class is here as an example and the error checking is minimal.
 *
 * @author Sean Grimes, sean@seanpgrimes.com
 */
public abstract class In {
    // The current flag value for abstractedInputOutput.ComType (console, file, audio, video) that this class
    // should use
    /*
        NOTE: Default is CONSOLE -- communicate with user on command line by default
        This will ultimately use the abstractedInputOutput.ConsoleIn class by default. Similar to how abstractedInputOutput.Out has
        abstractedInputOutput.ConsoleOut, abstractedInputOutput.FileOut, abstractedInputOutput.AudioOut, to use other abstractedInputOutput.In types you would need to write new
        classes that extend abstractedInputOutput.In (e.g. AudioIn, FileIn)
     */
    protected static ComType comType = ComType.CONSOLE;
    // The instance that gets returned for all getInstance calls
    private static In input;
    // No public c'tor to fit the singleton requirements
    protected In() {
    }

    public static ComType getComType() {
        return comType;
    }

    public static void setComType(ComType ct) {
        comType = ct;
    }

    public static In getInstance() {
        if (In.comType == ComType.CONSOLE) {
            input = ConsoleIn.getInstance();
        }
        // If other input types are implemented, uncomment similar to the
        // abstractedInputOutput.Out class and remove the UnsupportedOperationException
        else if (In.comType == ComType.FILE) {
            input = FileIn.getInstance();
        }
        return input;
    }

    // The following abstract functions must be implemented in child classes
    public abstract String readStr();

    public abstract int readInt();

    public abstract double readDouble();
}
