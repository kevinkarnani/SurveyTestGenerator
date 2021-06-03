import InputOutput.ConsoleIn;
import InputOutput.ConsoleOut;
import InputOutput.FileIn;
import InputOutput.FileOut;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Survey implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    ArrayList<Question> questions;

    public Survey() {
        this.questions = new ArrayList<>();
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void addQuestion() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        HashMap<Integer, String> classMappings = new HashMap<>() {{
            put(1, "TrueFalseQuestion");
            put(2, "MultipleChoiceQuestion");
            put(3, "ShortAnswerQuestion");
            put(4, "EssayQuestion");
            put(5, "DateQuestion");
            put(6, "MatchingQuestion");
        }};

        while (true) {
            out.say("Pick one of the following:");
            out.say("1) Add a new T/F Question");
            out.say("2) Add a new Multiple Choice Question");
            out.say("3) Add a new Short Answer Question");
            out.say("4) Add a new Essay Question");
            out.say("5) Add a new Date Question");
            out.say("6) Add a new Matching Question");
            out.say("7) Return to previous menu");
            int res = in.readInt();

            if (res == 7) {
                out.say("Returning to Main Menu");
                break;
            } else if (res > 7) {
                out.say("Invalid Input. Please Try Again");
                continue;
            }
            out.say("Enter the Prompt:");
            String prompt = in.readStr();
            try {
                // Refer to java docs on Reflection
                Class<?> cls = Class.forName(classMappings.get(res));
                Question question = (Question) cls.getDeclaredConstructor(String.class).newInstance(prompt);
                question.addHelper();
                this.addQuestion(question);
            } catch (Exception e) {
                out.say("Something went horribly wrong!");
                e.printStackTrace();
            }
        }
    }

    public void display() {
        this.questions.forEach(question -> {
            System.out.print((this.questions.indexOf(question) + 1) + ") ");
            question.display();
        });
    }

    public void store(String dirPath, String responseDirPath) {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        out.say("Do you wish to save an empty " + this.getClass().getName() + " or one filled with responses? Pick empty or filled.");
        out.say("Note: Saving a filled " + this.getClass().getName() + ", but choosing empty, will clear the responses.");
        String res = in.readStr();
        if (res.equalsIgnoreCase("empty") || res.equalsIgnoreCase("filled")) {
            if (res.equalsIgnoreCase("empty")) {
                this.getQuestions().forEach(Question::clearResponses);
                FileOut.serialize(this.getClass(), this, dirPath, this.name);
                this.display();
            } else {
                if (this.getQuestions().stream().allMatch(question -> question.getResponses().isEmpty())) {
                    out.say("No responses to save!");
                } else {
                    int count = (int) FileIn.getAllFilePathsInDir(responseDirPath).stream().map(response ->
                            response.split(File.separator)[response.split(File.separator).length - 1]).filter(file ->
                            file.split("\\.")[0].equals(this.name.split("\\.")[0])).count();
                    FileOut.serialize(this.getClass(), this, responseDirPath, this.name + "_" + (count + 1));
                    this.display();
                    this.getQuestions().forEach(Question::clearResponses);
                }
            }
        } else {
            out.say("Invalid Input. Returning to main menu");
        }
    }

    public void tabulate(String responseDirPath) {
        ArrayList<String> responses = (ArrayList<String>) FileIn.getAllFilePathsInDir(responseDirPath).stream()
                .map(response -> response.split(File.separator)[response.split(File.separator).length - 1])
                .filter(file -> file.split("\\.")[0].equals(this.name.split("\\.")[0]))
                .collect(Collectors.toList());

        if (responses.isEmpty()) {
            ConsoleOut.getInstance().say("No responses to show!");
        } else {
            ArrayList<Map<String, Integer>> totalMap = (ArrayList<Map<String, Integer>>)
                    this.questions.stream().map(Question::tabulate).collect(Collectors.toList());
            responses.forEach(response -> {
                Survey res = FileIn.deserialize(Survey.class, responseDirPath + response);
                ArrayList<Map<String, Integer>> mapList = (ArrayList<Map<String, Integer>>)
                        res.getQuestions().stream().map(Question::tabulate).collect(Collectors.toList());
                for (int i = 0; i < this.questions.size(); i++) {
                    // https://blog.knoldus.com/merge-lists-of-map-to-map-java-8-style/
                    totalMap.set(i, Stream.concat(totalMap.get(i).entrySet().stream(), mapList.get(i).entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum)));
                }
            });
            for (int i = 0; i < this.questions.size(); i++) {
                ConsoleOut.getInstance().say("\n" + this.questions.get(i).getPrompt());
                int j = i;
                totalMap.get(i).forEach((k, v) -> {
                    if (this.questions.get(j).getClass().equals(EssayQuestion.class)) {
                        ConsoleOut.getInstance().say(k);
                    } else {
                        ConsoleOut.getInstance().say(k + ": " + v);
                    }
                });
            }
        }
    }

    public void create(String surveyDirPath) {
        ConsoleOut out = ConsoleOut.getInstance();
        while (true) {
            out.say("How would you like to name the file of your new " + this.getClass().getName() + "?");
            this.setName(ConsoleIn.getInstance().readStr());
            if (FileIn.getAllFilePathsInDir(surveyDirPath).contains(surveyDirPath + this.name)) {
                out.say("This survey name is already taken!");
            } else {
                break;
            }
        }
        this.name += ".ser";
        this.addQuestion();
    }

    public void take() {
        this.getQuestions().forEach(question -> {
            System.out.print((this.questions.indexOf(question) + 1) + ") ");
            question.take();
        });
    }

    public void modify() {
        ConsoleOut out = ConsoleOut.getInstance();
        ConsoleIn in = ConsoleIn.getInstance();
        String modificationType;

        while (true) {
            out.say("Do you wish to add or modify a question?");
            modificationType = in.readStr();
            if (modificationType.equalsIgnoreCase("add") || modificationType.equalsIgnoreCase("modify")) {
                break;
            } else {
                out.say("You can only add or modify a question!");
            }
        }

        switch (modificationType.toLowerCase()) {
            case "add":
                addQuestion();
                break;
            case "modify":
                out.say("What question do you wish to modify?");
                int idx = in.readIntInRange(1, this.getQuestions().size()) - 1;
                Question question = this.getQuestions().get(idx);
                while (true) {
                    out.say("Do you wish to edit or delete a question?");
                    modificationType = in.readStr();
                    if (modificationType.equalsIgnoreCase("edit") ||
                            modificationType.equalsIgnoreCase("delete")) {
                        break;
                    } else {
                        out.say("You can only edit or delete a question!");
                    }
                }
                switch (modificationType.toLowerCase()) {
                    case "delete":
                        this.removeQuestion(idx);
                        break;
                    case "edit":
                        this.modifyHelper(idx, question);
                        break;
                }
        }
    }

    public void modifyHelper(int index, Question question) {
        question.modify();
    }

    public void removeQuestion(int index) {
        this.questions.remove(index);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
