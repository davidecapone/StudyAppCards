package project.study.app.model.domain;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import project.study.app.model.dao.QuestionListConverter;

/**
 * A class to represent a set of questions
 */
@Entity(tableName = "question_sets")
@TypeConverters({QuestionListConverter.class})
public class QuestionSet {

    @PrimaryKey
    @NonNull
    private String questionSetName;
    private final List<Question> questions;

    public QuestionSet(@NonNull String questionSetName) {
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

    public List<Question> getAllQuestions() {
        return questions;
    }

    @NonNull
    public String getQuestionSetName() {
        return this.questionSetName;
    }

    public void setQuestionSetName(@NonNull String newName) {
        this.questionSetName = newName;
    }
}
