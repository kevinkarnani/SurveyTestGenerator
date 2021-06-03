import InputOutput.ConsoleIn;
import InputOutput.ConsoleOut;

import java.io.Serializable;

public class EssayQuestion extends Question implements Serializable {
    private static final long serialVersionUID = 4L;
    int limit;

    public EssayQuestion(String prompt) {
        super(prompt);
        this.setLimit(1000);
    }

    public EssayQuestion(String prompt, int limit) {
        super(prompt);
        this.setLimit(limit);
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String validateAnswer() {
        String res = ConsoleIn.getInstance().readStr();
        while (res.length() > this.getLimit()) {
            ConsoleOut.getInstance().say("Response too long! The character limit is " + this.getLimit() + ".");
            res = ConsoleIn.getInstance().readStr();
        }
        return res;
    }
}
