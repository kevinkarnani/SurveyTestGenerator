import java.io.Serializable;
import java.util.HashMap;

public class TrueFalseQuestion extends MultipleChoiceQuestion implements Serializable {
    public TrueFalseQuestion(String prompt) {
        super(prompt);
        this.addChoice("True");
        this.addChoice("False");
        this.setResponseLimit(1);
    }

    @Override
    public void addHelper() {
    }

    @Override
    public void modifyHelper() {
    }

    @Override
    public HashMap<String, Integer> tabulate() {
        HashMap<String, Integer> responseCounter = new HashMap<>();
        this.choices.forEach(choice -> responseCounter.put(choices.indexOf(choice) == 0 ? "True" : "False", 0));
        this.responses.forEach(response -> {
            Integer count = responseCounter.get(response.getResponse().equals("A") ? "True" : "False");
            responseCounter.put(response.getResponse().equals("A") ? "True" : "False", (count == null) ? 0 : count + 1);
        });
        return responseCounter;
    }
}
