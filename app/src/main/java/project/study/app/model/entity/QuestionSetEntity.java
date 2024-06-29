package project.study.app.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import project.study.app.model.converters.QuestionListConverter;
import project.study.app.model.domain.Question;

/**
 * A class to represent a set of questions for persistence.
 * This class is used to persist a set of questions in the database.
 * It uses Room annotations to define the database schema and provides
 * methods to manage the questions within the set.
 */
@Entity(tableName = "question_sets")
public class QuestionSetEntity {

    // The primary key for the question set
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    // The name of the question set
    private String name;

    // The list of questions in the set
    @TypeConverters(QuestionListConverter.class)
    private List<Question> questions;

    /**
     * Constructor to create a QuestionSetEntity with a name and list of questions.
     *
     * @param name The name of the question set.
     * @param questions The list of questions in the set.
     */
    public QuestionSetEntity(String name, List<Question> questions) {

        this.name = name;
        this.questions = questions;
    }

    /**
     * Gets the ID of the question set.
     *
     * @return The ID of the question set.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the question set.
     *
     * @param id The ID of the question set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the question set.
     *
     * @return The name of the question set.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the question set.
     *
     * @param name The name of the question set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of questions in the set.
     *
     * @return The list of questions in the set.
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Sets the list of questions in the set.
     *
     * @param questions The list of questions in the set.
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * Adds a question to the set.
     *
     * @param question The question to add to the set.
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}