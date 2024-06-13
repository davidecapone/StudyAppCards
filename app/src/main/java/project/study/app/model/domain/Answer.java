package project.study.app.model.domain;

public abstract class Answer<T> {

    T correctAnswer;

    public T getCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(T newCorrectAnswer) {
        this.correctAnswer = newCorrectAnswer;
    }
}
