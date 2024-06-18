package project.study.app.model.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a set of questions
 */
public class QuestionSet {

    private String questionSetName;
    private final List<Question> questions;

    public QuestionSet(String questionSetName) {
        this.questionSetName = questionSetName;
        this.questions = new ArrayList<>();
    }

    /**
     * Add a question to the question set
     * @param newQuestion the question to be added
     */
    public void addQuestion(Question newQuestion) {

        if (newQuestion == null)
            throw new IllegalArgumentException("New question cannot be empty.");

        questions.add(newQuestion);
    }

    /**
     * Remove a question from the question set
     * @param question the question to be removed
     */
    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getQuestionSetName() {
        return this.questionSetName;
    }

    public void setQuestionSetName(String newName) {
        this.questionSetName = newName;
    }
}