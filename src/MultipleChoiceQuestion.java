import InputOutput.ConsoleIn;
import InputOutput.ConsoleOut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MultipleChoiceQuestion extends Question implements Serializable {
    private static final long serialVersionUID = 3L;
    protected ArrayList<String> choices;

    public MultipleChoiceQuestion(String prompt) {
        super(prompt);
        this.choices = new ArrayList<>();
    }

    public ArrayList<String> getChoices() {
        return this.choices;
    }

    public void addChoice(String choice) {
        this.choices.add(choice);
    }

    @Override
    public void display() {
        super.display();
        this.displayChoices("\t");
    }

    public void displayChoices(String indent) {
        this.choices.forEach(choice -> System.out.println(indent + (char) (choices.indexOf(choice) + 65) + ") " + choice));
    }

    public void setChoice(int idx, String newChoice) {
        this.choices.set(idx, newChoice);
    }

    @Override
    public void addHelper() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        out.say("How many choices would you like? Enter 26 for max number of answers.");
        // design choice, don't feel like dealing with more than (A) - (Z)
        int numChoices = in.readIntInRange(1, 26);
        for (int i = 0; i < numChoices; i++) {
            out.say("Enter Choice #" + (i + 1));
            this.addChoice(in.readStr());
        }
        super.addHelper();
    }

    @Override
    public String validateAnswer() {
        int choiceIdx = ConsoleIn.getInstance().readCharAsInt(this.getChoices().size());
        return Character.toString((char) (choiceIdx + 65));
    }

    @Override
    public void modifyHelper() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        out.say("Do you wish to modify choices?");
        String res = in.readStr();
        if (res.equalsIgnoreCase("yes") || res.equalsIgnoreCase("y")) {
            out.say("Choices:");
            this.displayChoices("");
            out.say("Which choice do you want to modify?");
            int choiceIdx = in.readCharAsInt(this.getChoices().size());
            out.say("Enter new choice:");
            this.setChoice(choiceIdx, in.readStr());
        }

        out.say("Do you want to change the number of responses the question can take?");
        res = in.readStr();
        if (res.equalsIgnoreCase("yes") || res.equalsIgnoreCase("y")) {
            out.say("How many responses should this be able to take? Enter 26 for max number of answers.");
            this.setResponseLimit(in.readIntInRange(1, this.choices.size()));
        }
    }

    @Override
    public HashMap<String, Integer> tabulate() {
        HashMap<String, Integer> responseCounter = new HashMap<>();
        this.choices.forEach(choice -> responseCounter.put(Character.toString((char) choices.indexOf(choice) + 65), 0));
        this.responses.forEach(response -> {
            Integer count = responseCounter.get(response.getResponse());
            responseCounter.put(response.getResponse(), (count == null) ? 1 : count + 1);
        });
        return responseCounter;
    }
}
