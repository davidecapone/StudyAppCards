package project.study.app.model.domain;

/**
 * An abstract class to represent an answer to a question
 * @param <T> the type of the answer
 */
public abstract class Answer<T> {
    private T correctAnswer;

    public T getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(T correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public abstract boolean isCorrect(Object answer);
}
