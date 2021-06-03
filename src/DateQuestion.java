import InputOutput.ConsoleIn;

import java.io.Serializable;

public class DateQuestion extends Question implements Serializable {
    private static final long serialVersionUID = 8L;

    public DateQuestion(String prompt) {
        super(prompt + "\nNote: The only accepted date format is YYYY-MM-DD.");
    }

    @Override
    public String validateAnswer() {
        return ConsoleIn.getInstance().readDate();
    }
}
