package project.study.app.model.domain;

/**
 * An abstract class to represent an answer to a question
 * @param <T> the type of the answer
 */
public abstract class Answer<T> {
    private T correctAnswer;

    /**
     * Method to get the correct answer to the question
     *
     * @return the correct answer
     */
    public T getCorrectAnswer() {
        return correctAnswer;
    }
    /**
     * Method to set the correct answer to the question
     *
     * @param correctAnswer the correct answer
     */
    public void setCorrectAnswer(T correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    /**
     * Method to check if the given answer is correct
     *
     * @param answer the answer to check
     * @return true if the answer is correct, false otherwise
     */
    public abstract boolean isCorrect(Object answer);
}
