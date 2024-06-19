package project.study.app.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
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

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String name;
    @TypeConverters(QuestionListConverter.class)
    private List<Question> questions;

    public QuestionSetEntity(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}