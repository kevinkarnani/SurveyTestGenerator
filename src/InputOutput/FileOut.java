package InputOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Please note this class is here as an example and the error checking is minimal.
 *
 * @author Sean Grimes, sean@seanpgrimes.com
 */
public class FileOut extends Out {
    // The output file, if using abstractedInputOutput.FileOut
    public static String outputFile = "default.out";
    // The instance that's returned in the getInstance() call
    private static FileOut instance;
    // No public c'tor to fit the singleton requirements
    private FileOut() {
    }

    // Return a abstractedInputOutput.ConsoleOut object in a threadsafe manner with lazy initialization
    public static FileOut getInstance() {
        if (instance == null) {
            synchronized (FileOut.class) {
                if (instance == null)
                    instance = new FileOut();
            }
        }
        return instance;
    }

    /**
     * Append data to an existing file
     *
     * @param filename Path to the file
     * @param str      The string to write to the new file
     */
    public static boolean appendToFile(String filename, String str) {
        BufferedWriter writer;
        try {
            writer = Files.newBufferedWriter(Paths.get(filename),
                    StandardOpenOption.APPEND);
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Write a new file to disk. Will overwrite existing file
     *
     * @param fileName Path to the file
     * @param str      The string to write to the file
     */
    public static boolean writeNewFile(String fileName, String str) {
        BufferedWriter writer;
        try {
            writer = Files.newBufferedWriter(Paths.get(fileName));
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Create a directory if one does not exist
     *
     * @param directoryPath The path to the directory to be created
     * @return True if success, else false
     */
    public static boolean createDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        // Nothing exists here, create the directory and all parent directories
        if (!dir.exists())
            return dir.mkdirs();

        // Something exists at the supplied path, see if it's a directory. If it is,
        // return true. If it's not, it's something else so return false.
        return dir.isDirectory();
    }

    public static <T> void serialize(Class<T> type, Object obj, String dirPath,
                                     String fileName) {
        // Make sure the directory exists
        createDirectory(dirPath);

        String fullPath = dirPath + fileName;
        try {
            FileOutputStream fos = new FileOutputStream(fullPath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(type.cast(obj));

            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    // This function will append some content to the file that lives at outputFile
    // NOTE: This is a very simple function. It will not check to see if the file
    // exists, if it has permission, if the directory structure exists, etc...
    public void say(Object msg) {
        BufferedWriter writer;
        try {
            writer = Files.newBufferedWriter(Paths.get(outputFile),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            writer.write(msg.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

