package project.study.app.model.domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import project.study.app.model.converters.QuestionListConverter;

/**
 * A class to represent a set of questions
 */
public class QuestionSet {

    private String questionSetName;
    private List<Question> questions;

    /**
     * Constructs a QuestionSet with the specified name.
     * @param questionSetName the name of the question set
     */
    public QuestionSet(String questionSetName) {
        this.questionSetName = questionSetName;
        this.questions = new ArrayList<>();
    }

    /**
     * Adds a question to the question set.
     * @param newQuestion the question to be added
     */
    public void addQuestion(Question newQuestion) {
        if (newQuestion == null) throw new IllegalArgumentException("New question cannot be empty.");
        questions.add(newQuestion);
    }

    /**
     * Removes a question from the question set.
     * @param question the question to be removed
     */
    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    /**
     * Gets all the questions in the question set.
     * @return the list of questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Sets the list of questions in the question set.
     * @param newQuestions the new list of questions
     */
    public void setQuestions(List<Question> newQuestions) {
        this.questions = newQuestions;
    }

    /**
     * Gets the name of the question set.
     * @return the name of the question set
     */
    public String getQuestionSetName() {
        return questionSetName;
    }

    /**
     * Sets the name of the question set.
     * @param questionSetName the new name of the question set
     */
    public void setQuestionSetName(String questionSetName) {
        this.questionSetName = questionSetName;
    }
}
