package InputOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Sean Grimes, sean@seanpgrimes.com
 */
public class FileIn extends In {
    // The instance that's returned in the getInstance() call
    private static FileIn instance;
    // Path to read from
    private String file = "default.out";
    // No public c'tor to fit the singleton requirements
    private FileIn() {
    }

    // Return a FileIn object in a threadsafe manner with lazy initialization
    public static FileIn getInstance() {
        if (instance == null) {
            synchronized (FileIn.class) {
                if (instance == null)
                    instance = new FileIn();
            }
        }
        return instance;
    }

    /**
     * Read a file line by line
     *
     * @param filePath The path to the file
     * @return A list of lines of the file (as strings) if file is found and readable
     */
    public static List<String> readLineByLine(String filePath) {
        List<String> lines = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(Paths.get(filePath));
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("Failed to finalize readLineByLine");
                }
            }
        }
        return lines;
    }

    /**
     * Get a sorted list of all files in a directory
     *
     * @param path The path to the directory
     * @return The sorted list of paths
     */
    public static List<String> getAllFilePathsInDir(String path) {
        List<String> paths = new ArrayList<>();
        File[] files = new File(path).listFiles();
        if (files == null)
            files = new File[]{};
        for (File f : files) {
            if (f.isFile())
                paths.add(f.getPath());
        }
        return sortPaths(paths);
    }

    /**
     * Sort strings *in place* using a custom comparator
     *
     * @param paths The list of strings to be sorted
     * @return The sorted list of strings
     */
    private static List<String> sortPaths(List<String> paths) {
        if (paths.isEmpty()) return Collections.emptyList();
        if (paths.size() == 1) return paths;
        Comparator<String> comp = Comparator.comparing((String x) -> x);
        paths.sort(comp);
        return paths;
    }

    /**
     * Displays a user a list of files available in a given directory. Allows user to
     * select a single file from this list of files
     *
     * @param dirPath The path to the directory that holds the files
     * @return The absolute path to the selected file
     */
    public static String listAndPickFileFromDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory())
            throw new IllegalStateException(dirPath + " is invalid");

        File[] files = dir.listFiles();
        if (files == null || files.length == 0)
            throw new IllegalStateException(dirPath + " is empty");

        System.out.println("Please enter number of file to load: ");
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isFile())
                // Adding 1 to avoid 0-indexed UI. getName to chop off full path.
                System.out.println((i + 1) + ") " + files[i].getName());
        }

        // Get a valid integer between 1 and number of files
        int fileSelection = InputHelper.readIntInRange(1, files.length);

        // Remember to subtract 1 to get back to 0-indexed value
        return files[fileSelection - 1].getPath();
    }

    /**
     * This is a generic deserialization method, it only needs to know the type of
     * object it is deserializing. It will work for *anything* that can be deserialized.
     *
     * @param type The object type, passed as, for example, Object.class
     * @param path The path to the file to be deserialized
     * @param <T>  A necessary generic qualifier, it is implicitly passed
     * @return The deserialized object
     * NOTE: This would be called like so:
     * utils.SerializationHelper.deserialize(Object.class, path) where "Object.class" is
     * whatever object type you're deserializing, e.g. "vehicle.Car.class" to deserialize the vehicle.Car
     */
    public static <T> T deserialize(Class<T> type, String path) {
        // Test to make sure the path exists and is a file
        File tst = new File(path);
        if (!tst.exists() || !tst.isFile())
            throw new IllegalArgumentException(path + " is invalid");

        // Create the generic object that will hold the deserialized object
        T deserializedObject = null;
        try {
            // Streams to read it in
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read it in with the cast to specific type
            deserializedObject = type.cast(ois.readObject());

            // Cleanup
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("The provided file is corrupted/unserializable!");
        }
        return deserializedObject;
    }

    // Read a string from the *first line* of a file
    public String readStr() {
        return InputHelper.readStr(file);
    }

    // Read an int from the *first line* of a file
    public int readInt() {
        return InputHelper.readInt(file);
    }

    // Read a double from the *first line* of a file
    public double readDouble() {
        return InputHelper.readDouble(file);
    }
}
