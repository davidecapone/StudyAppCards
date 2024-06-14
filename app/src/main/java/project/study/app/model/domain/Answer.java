package project.study.app.model.domain;

/**
 * An abstract class to represent an answer to a question
 * @param <T> the type of the answer
 */
public abstract class Answer<T> {

    T correctAnswer;

    public T getCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(T newCorrectAnswer) {
        this.correctAnswer = newCorrectAnswer;
    }
}
