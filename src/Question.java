import InputOutput.ConsoleIn;
import InputOutput.ConsoleOut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Question implements Serializable {
    private static final long serialVersionUID = 2L;
    String prompt;
    ArrayList<ResponseCorrectAnswer> responses;
    int responseLimit;

    public Question(String prompt) {
        this.setPrompt(prompt);
        this.responses = new ArrayList<>();
    }

    public Map<String, Integer> tabulate() {
        Map<String, Integer> responseCounter = new HashMap<>();
        this.responses.forEach(response -> {
            if (responseCounter.containsKey(response.getResponse())) {
                responseCounter.replace(response.getResponse(), responseCounter.get(response.getResponse()) + 1);
            } else {
                responseCounter.put(response.getResponse(), 1);
            }
        });
        return responseCounter;
    }

    public int getResponseLimit() {
        return this.responseLimit;
    }

    public void setResponseLimit(int responseLimit) {
        this.responseLimit = responseLimit;
    }

    public void clearResponses() {
        this.responses.clear();
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public ArrayList<ResponseCorrectAnswer> getResponses() {
        return this.responses;
    }

    public void addResponse(ResponseCorrectAnswer response) {
        if (this.responses.size() < this.responseLimit) {
            this.responses.add(response);
            Collections.sort(this.responses);
        } else {
            System.out.println("You can only have " + this.responseLimit + " responses!");
        }
    }

    public void display() {
        System.out.println(this.prompt);
    }

    public void take() {
        this.display();
        while (this.responses.size() < this.getResponseLimit()) {
            this.addResponse(new ResponseCorrectAnswer(this.validateAnswer()));
        }
    }

    public String validateAnswer() {
        return ConsoleIn.getInstance().readStr();
    }

    public void addHelper() {
        // once again... design choice
        ConsoleOut.getInstance().say("How many responses should this be able to take? Enter 26 for max number of answers.");
        this.setResponseLimit(ConsoleIn.getInstance().readIntInRange(1, 26));
    }

    public void modify() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        out.say("Prompt: " + this.getPrompt());
        out.say("Do you wish to edit the prompt?");
        String res = in.readStr();
        if (res.equalsIgnoreCase("yes") || res.equalsIgnoreCase("y")) {
            out.say("Prompt: " + this.getPrompt());
            out.say("Enter a new prompt:");
            this.setPrompt(in.readStr());
        }
        this.modifyHelper();
    }

    public void modifyHelper() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        out.say("Do you want to change the number of responses the question can take?");
        String res = in.readStr();
        if (res.equalsIgnoreCase("yes") || res.equalsIgnoreCase("y")) {
            out.say("How many responses should this be able to take? Enter 26 for max number of answers.");
            this.setResponseLimit(in.readIntInRange(1, 26));
        }
    }
}
