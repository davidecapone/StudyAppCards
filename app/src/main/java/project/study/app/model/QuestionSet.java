package project.study.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a set of questions
 */
public class QuestionSet {

    private String questionSetName;
    List<Question<?>> questions;

    public QuestionSet(String questionSetName) {
        this.questionSetName = questionSetName;
        this.questions = new ArrayList<>();
    }

    /**
     * Add a question to the question set
     * @param question the question to be added
     */
    public void addQuestion(Question<?> question) {
        questions.add(question);
    }

    /**
     * Remove a question from the question set
     * @param question the question to be removed
     */
    public void removeQuestion(Question<?> question) {
        questions.remove(question);
    }

    /**
     * Retrieve a random question from the question set
     * @return a random question
     */
    public Question<?> getRandomQuestion() {

        // generate a random index to select a question from the list
        int randomIndex = (int) (Math.random() * questions.size());

        return questions.get(randomIndex);
    }

    public List<Question<?>> getQuestions() {
        return questions;
    }
}
