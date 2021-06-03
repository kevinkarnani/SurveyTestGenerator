import InputOutput.ConsoleIn;
import InputOutput.ConsoleOut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchingQuestion extends Question implements Serializable {
    private static final long serialVersionUID = 9L;
    ArrayList<String> left;
    ArrayList<String> right;

    public MatchingQuestion(String prompt) {
        super(prompt);
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
    }

    public ArrayList<String> getLeft() {
        return this.left;
    }

    public ArrayList<String> getRight() {
        return this.right;
    }

    public void setLeftChoice(int index, String choice) {
        this.left.set(index, choice);
    }

    public void setRightChoice(int index, String choice) {
        this.right.set(index, choice);
    }

    public void addLeft(String choice) {
        this.left.add(choice);
    }

    public void addRight(String choice) {
        this.right.add(choice);
    }

    @Override
    public void display() {
        this.display("\t");
    }

    public void display(String indent) {
        super.display();
        for (int i = 0; i < left.size(); i++) {
            // https://stackoverflow.com/questions/20202563/how-to-print-columns-in-java-txt-file
            System.out.printf(indent + "%-75s%-75s\n", (char) (i + 65) + ") " + left.get(i), (i + 1) + ") " + right.get(i));
        }
    }

    @Override
    public void addHelper() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        out.say("How many matching choices would you like? Enter 26 for max number of answers.");
        // design choice, don't feel like dealing with more than (A) - (Z)
        int numMatchingChoices = in.readIntInRange(1, 26);
        for (int i = 0; i < numMatchingChoices; i++) {
            out.say("Enter Choice " + (char) (i + 65));
            this.addLeft(in.readStr());
            out.say("Enter Choice #" + (i + 1));
            this.addRight(in.readStr());
        }
        super.addHelper();
    }

    @Override
    public String validateAnswer() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        String res = in.readStr();
        String left = res.substring(0, 1).toUpperCase();
        String right = res.substring(1);
        // ^ anchors the start, gets only alphanumeric pairs, $ anchors the end
        // https://stackoverflow.com/questions/32435949/regex-to-allow-only-number-between-1-to-12
        while (!res.matches("^[A-Za-z](2[0-6]|1[0-9]|[1-9])$") || (left.charAt(0) - 65) >= this.left.size() ||
                Integer.parseInt(right) - 1 >= this.right.size()) {
            out.say("Invalid Input! Please put a valid pair such as 'A3' or 'Z19'.");
            out.say("Make sure you pick from the provided options.");
            res = in.readStr();
            left = res.substring(0, 1).toUpperCase();
            right = res.substring(1);
        }
        return left + right;
    }

    @Override
    public void modifyHelper() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        out.say("Do you wish to modify the left side?");
        String res = in.readStr();
        if (res.equalsIgnoreCase("yes") || res.equalsIgnoreCase("y")) {
            out.say("Choices:");
            this.display("");
            out.say("Which choice do you want to modify?");
            int choiceIdx = in.readCharAsInt(this.getLeft().size());
            out.say("Enter new choice:");
            this.setLeftChoice(choiceIdx, in.readStr());
        }
        out.say("Do you wish to modify the right side?");
        res = in.readStr();
        if (res.equalsIgnoreCase("yes") || res.equalsIgnoreCase("y")) {
            out.say("Choices:");
            this.display("");
            out.say("Which choice do you want to modify?");
            int choiceIdx = in.readIntInRange(1, this.getRight().size()) - 1;
            out.say("Enter new choice:");
            this.setRightChoice(choiceIdx, in.readStr());
        }
        super.modifyHelper();
    }

    @Override
    public HashMap<String, Integer> tabulate() {
        HashMap<String, Integer> responseCounter = new HashMap<>();
        this.left.forEach(val1 -> this.right.forEach(val2 -> responseCounter.put(
                String.format("%s%d", Character.toString(this.left.indexOf(val1) + 65), (this.right.indexOf(val2) + 1)), 0)));
        this.responses.forEach(response -> {
            Integer count = responseCounter.get(response.getResponse());
            responseCounter.put(response.getResponse(), (count == null) ? 0 : count + 1);
        });
        return responseCounter;
    }
}
