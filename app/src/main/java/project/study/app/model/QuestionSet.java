package project.study.app.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionSet {
    private String questionSetName;
    List<Question<?>> questions;

    public QuestionSet(String questionSetName) {
        this.questionSetName = questionSetName;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question<?> question) {
        questions.add(question);
    }

    public List<Question<?>> getQuestions() {
        return questions;
    }

    public void removeQuestion(Question<?> question) {
        questions.remove(question);
    }
}
