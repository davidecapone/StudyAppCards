package project.study.app.model.domain;

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

    /**
     * Generate a list of all questions in randomized order
     * @return a list of all questions in randomized order
     */
    public List<Question<?>> getRandomizedQuestionList() {

        // create a new list to store the randomized questions
        List<Question<?>> randomizedQuestions = new ArrayList<>();

        // create a copy of the original list of questions
        List<Question<?>> copyOfQuestions = new ArrayList<>(questions);

        // iterate through the original list of questions
        while (!copyOfQuestions.isEmpty()) {

            // generate a random index to select a question from the list
            int randomIndex = (int) (Math.random() * copyOfQuestions.size());

            // add the randomly selected question to the list of randomized questions
            randomizedQuestions.add(copyOfQuestions.get(randomIndex));

            // remove the randomly selected question from the copy of the original list of questions
            copyOfQuestions.remove(randomIndex);
        }

        return randomizedQuestions;
    }

    public List<Question<?>> getQuestions() {
        return questions;
    }
}
