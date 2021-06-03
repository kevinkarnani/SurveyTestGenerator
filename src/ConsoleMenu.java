import InputOutput.ConsoleIn;
import InputOutput.ConsoleOut;
import InputOutput.FileIn;
import InputOutput.FileOut;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ConsoleMenu {
    static final String surveyDirPath = "src" + File.separator + "serialized" + File.separator + "surveys" + File.separator;
    static final String surveyResponseDirPath = "src" + File.separator + "serialized" + File.separator + "surveyResponses" + File.separator;
    static final String testDirPath = "src" + File.separator + "serialized" + File.separator + "tests" + File.separator;
    static final String testResponseDirPath = "src" + File.separator + "serialized" + File.separator + "testResponses" + File.separator;
    public static Survey currentSurvey;
    public static String currentSurveyName;
    static ConsoleOut out = ConsoleOut.getInstance();
    static ConsoleIn in = ConsoleIn.getInstance();

    public static void main(String[] args) {
        FileOut.createDirectory(surveyDirPath);
        FileOut.createDirectory(surveyResponseDirPath);
        FileOut.createDirectory(testDirPath);
        FileOut.createDirectory(testResponseDirPath);
        out.say("Welcome to Survey Generator!");
        boolean exit = false;

        while (!exit) {
            out.say("\nPick one of the following:");
            out.say("1) Survey");
            out.say("2) Test");
            out.say("3) Exit");
            int res = in.readInt();

            switch (res) {
                case 1:
                    surveyMenu();
                    break;
                case 2:
                    testMenu();
                    break;
                case 3:
                    out.say("Thank you for using Survey and Test Generator!");
                    exit = true;
                    break;
                default:
                    out.say("Invalid Input. Try Again.");
                    break;
            }
        }
    }

    public static void surveyMenu() {
        boolean exit = false;

        while (!exit) {
            out.say("\nPick one of the following:");
            out.say("1) Create a new Survey");
            out.say("2) Display an existing Survey");
            out.say("3) Load an existing Survey");
            out.say("4) Save the current Survey");
            out.say("5) Take the current Survey");
            out.say("6) Modify the current Survey");
            out.say("7) Tabulate a survey");
            out.say("8) Return to previous menu");
            int res = in.readInt();
            switch (res) {
                case 1:
                    createSurvey();
                    break;
                case 2:
                    displaySurvey();
                    break;
                case 3:
                    currentSurvey = loadSurvey();
                    if (currentSurvey != null) {
                        currentSurvey.setName(currentSurveyName);
                    }
                    break;
                case 4:
                    storeSurvey();
                    break;
                case 5:
                    takeSurvey();
                    break;
                case 6:
                    modifySurvey();
                    break;
                case 7:
                    tabulateSurvey();
                    break;
                case 8:
                    out.say("Returning to previous menu!");
                    exit = true;
                    break;
                default:
                    out.say("Invalid Input. Please Try Again.");
                    break;
            }
        }
    }

    public static void testMenu() {
        boolean exit = false;

        while (!exit) {
            out.say("\nPick one of the following:");
            out.say("1) Create a new Test");
            out.say("2) Display an existing Test without correct answers");
            out.say("3) Display an existing Test with correct answers");
            out.say("4) Load an existing Test");
            out.say("5) Save the current Test");
            out.say("6) Take the current Test");
            out.say("7) Modify the current Test");
            out.say("8) Tabulate a Test");
            out.say("9) Grade a Test");
            out.say("10) Return to previous menu");
            int res = in.readInt();
            switch (res) {
                case 1:
                    createTest();
                    break;
                case 2:
                    displayTest();
                    break;
                case 3:
                    displayTestCorrect();
                    break;
                case 4:
                    currentSurvey = loadTest();
                    if (currentSurvey != null) {
                        currentSurvey.setName(currentSurveyName);
                    }
                    break;
                case 5:
                    storeTest();
                    break;
                case 6:
                    takeTest();
                    break;
                case 7:
                    modifyTest();
                    break;
                case 8:
                    tabulateTest();
                    break;
                case 9:
                    gradeTest();
                    break;
                case 10:
                    out.say("Returning to previous menu!");
                    exit = true;
                    break;
                default:
                    out.say("Invalid Input. Please Try Again.");
                    break;
            }
        }
        currentSurvey = null;
        currentSurveyName = null;
    }

    public static void createSurvey() {
        currentSurvey = new Survey();
        currentSurvey.create(surveyDirPath);
    }

    public static void createTest() {
        currentSurvey = new Test();
        currentSurvey.create(testDirPath);
    }

    public static void modifySurvey() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a survey loaded in order to modify it.");
        } else {
            currentSurvey.modify();
        }
    }

    public static void modifyTest() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a test loaded in order to modify it.");
        } else {
            currentSurvey.modify();
        }
    }

    public static void storeSurvey() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a survey loaded in order to save it or its responses.");
        } else {
            currentSurvey.store(surveyDirPath, surveyResponseDirPath);
        }
    }

    public static void storeTest() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a test loaded in order to save it or its responses.");
        } else {
            currentSurvey.store(testDirPath, testResponseDirPath);
        }
    }

    public static Survey loadSurvey() {
        try {
            String[] currentPath = FileIn.listAndPickFileFromDir(surveyDirPath).split(File.separator);
            currentSurveyName = currentPath[currentPath.length - 1];
            return FileIn.deserialize(Survey.class, surveyDirPath + currentSurveyName);
        } catch (Exception e) {
            out.say("There are no files to pick!");
            return null;
        }
    }

    public static Test loadTest() {
        try {
            String[] currentPath = FileIn.listAndPickFileFromDir(testDirPath).split(File.separator);
            currentSurveyName = currentPath[currentPath.length - 1];
            return FileIn.deserialize(Test.class, testDirPath + currentSurveyName);
        } catch (Exception e) {
            out.say("There are no files to pick!");
            return null;
        }
    }

    public static void takeSurvey() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a survey loaded in order to take it.");
        } else {
            currentSurvey.take();
        }
    }

    public static void takeTest() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a test loaded in order to take it.");
        } else {
            currentSurvey.take();
        }
    }

    public static void displaySurvey() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a survey loaded in order to display it.");
        } else {
            currentSurvey.display();
        }
    }

    public static void displayTest() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a test loaded in order to display it.");
        } else {
            currentSurvey.display();
        }
    }

    public static void displayTestCorrect() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a test loaded in order to display it.");
        } else {
            ((Test) currentSurvey).displayCorrect();
        }
    }

    public static void tabulateSurvey() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a survey loaded in order to tabulate it.");
        } else {
            currentSurvey.tabulate(surveyResponseDirPath);
        }
    }

    public static void tabulateTest() {
        if (currentSurvey == null || currentSurvey.getName() == null) {
            out.say("You must have a test loaded in order to tabulate it.");
        } else {
            currentSurvey.tabulate(testResponseDirPath);
        }
    }

    public static void gradeTest() {
        if (FileIn.getAllFilePathsInDir(testDirPath).isEmpty()) {
            out.say("No tests to grade!");
        } else {
            out.say("Select an existing test to grade:");
            String[] currentPath = FileIn.listAndPickFileFromDir(testDirPath).split(File.separator);
            String testName = currentPath[currentPath.length - 1];
            ArrayList<String> responses = (ArrayList<String>) FileIn.getAllFilePathsInDir(testResponseDirPath).stream()
                    .map(response -> response.split(File.separator)[response.split(File.separator).length - 1])
                    .filter(file -> file.split("\\.")[0].equals(testName.split("\\.")[0])).collect(Collectors.toList());
            if (responses.isEmpty()) {
                out.say("No responses to pick!");
            } else {
                out.say("Select an existing response set:");
                responses.forEach(response -> out.say("\t" + (responses.indexOf(response) + 1) + ") " + response));
                int index = in.readIntInRange(1, responses.size()) - 1;
                Test test = FileIn.deserialize(Test.class, testResponseDirPath + responses.get(index));
                test.getGrade();
            }
        }
    }
}
