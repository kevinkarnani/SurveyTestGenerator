import InputOutput.ConsoleIn;
import InputOutput.ConsoleOut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Test extends Survey implements Serializable {
    private static final long serialVersionUID = 10L;
    ArrayList<ArrayList<ResponseCorrectAnswer>> correctResponses;

    public Test() {
        super();
        this.correctResponses = new ArrayList<>();
    }

    public void getGrade() {
        double correctCount = this.questions.stream().filter(question -> !question.getClass().equals(EssayQuestion.class)
                && question.responses.equals(correctResponses.get(this.questions.indexOf(question)))).count();
        double numEssayQs = this.questions.stream().filter(question -> question.getClass().equals(EssayQuestion.class)).count();
        ConsoleOut.getInstance().say("You received a(n) " + (correctCount / this.questions.size() * 100) + " on the test.");
        if (numEssayQs > 0) {
            ConsoleOut.getInstance().say("The test was worth 100%, but only " + (100 - (numEssayQs / this.questions.size()) * 100)
                    + " of those points could be auto-graded because there was/were " +  (int) numEssayQs + " Essay question(s)");
        }
    }

    public void displayCorrect() {
        this.questions.forEach(question -> {
            System.out.print((this.questions.indexOf(question) + 1) + ") ");
            question.display();
            if (!question.getClass().equals(EssayQuestion.class)) {
                ConsoleOut.getInstance().say("Correct Response(s): ");
                this.correctResponses.get(this.questions.indexOf(question)).forEach(r -> ConsoleOut.getInstance().say(r.getResponse()));
            } else {
                ConsoleOut.getInstance().say("Essays have no correct response(s) to show. We will grade this manually.");
            }
        });
    }

    @Override
    public void addQuestion(Question question) {
        super.addQuestion(question);

        ArrayList<ResponseCorrectAnswer> inputs = new ArrayList<>();

        if (!question.getClass().equals(EssayQuestion.class)) {
            ConsoleOut.getInstance().say("Enter correct choice(s):");
            while (inputs.size() < question.getResponseLimit()) {
                inputs.add(new ResponseCorrectAnswer(question.validateAnswer()));
            }
        }
        Collections.sort(inputs);
        this.addCorrectResponse(inputs);
    }

    public void addCorrectResponse(ArrayList<ResponseCorrectAnswer> responses) {
        this.correctResponses.add(responses);
    }

    @Override
    public void removeQuestion(int index) {
        super.removeQuestion(index);
        this.correctResponses.remove(index);
    }

    @Override
    public void modifyHelper(int index, Question question) {
        super.modifyHelper(index, question);

        ConsoleIn in = ConsoleIn.getInstance();
        ArrayList<ResponseCorrectAnswer> inputs = new ArrayList<>();
        if (!question.getClass().equals(EssayQuestion.class)) {
            ConsoleOut.getInstance().say("Do you want to change the correct answer?");
            String res = in.readStr();
            if (res.equalsIgnoreCase("yes") || res.equalsIgnoreCase("y")) {
                while (inputs.size() < question.getResponseLimit()) {
                    inputs.add(new ResponseCorrectAnswer(question.validateAnswer()));
                }
            }
        }
        this.correctResponses.set(index, inputs);
    }
}
