import InputOutput.ConsoleIn;
import InputOutput.ConsoleOut;

import java.io.Serializable;

public class ShortAnswerQuestion extends EssayQuestion implements Serializable {
    private static final long serialVersionUID = 5L;

    public ShortAnswerQuestion(String prompt) {
        super(prompt, 250);
    }

    public void addHelper() {
        ConsoleOut.getInstance().say("What should the character limit be? (Min: 50, Max: 500)");
        // design choice
        this.setLimit(ConsoleIn.getInstance().readIntInRange(50, 500));
        super.addHelper();
    }

    @Override
    public void modifyHelper() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        out.say("Do you wish to modify the character limit?");
        String res = in.readStr();
        if (res.equalsIgnoreCase("yes") || res.equalsIgnoreCase("y")) {
            out.say("Enter new character limit (Min: 50, Max: 500):");
            this.setLimit(in.readIntInRange(50, 500));
        }
        super.modifyHelper();
    }
}
