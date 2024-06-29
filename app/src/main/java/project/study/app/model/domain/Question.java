package project.study.app.model.domain;

/**
 * A class to represent a question
 */
public class Question {

    // The text of the question
    private String questionText;

    // The answer to the question
    private Answer<?> answer;

    /**
     * Constructs a Question with the specified text and answer.
     *
     * @param questionText the text of the question
     * @param answer the answer to the question
     */
    public Question(String questionText, Answer<?> answer) {
        this.questionText = questionText;
        this.answer = answer;
    }

    /**
     * Gets the text of the question.
     *
     * @return the text of the question
     */
    public String getText() {
        return questionText;
    }

    /**
     * Sets the text of the question.
     *
     * @param questionText the new text of the question
     */
    public void setText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Gets the answer to the question.
     *
     * @return the answer to the question
     */
    public Answer<?> getAnswer() {
        return answer;
    }

    /**
     * Sets the answer to the question.
     *
     * @param answer the new answer to the question
     */
    public void setAnswer(Answer<?> answer) {
        this.answer = answer;
    }

    /**
     * Validates the given answer.
     *
     * @param answer the answer to validate
     * @return true if the answer is correct, false otherwise
     */
    public boolean validateAnswer(Object answer) {
        return this.answer.isCorrect(answer);
    }
}
