package project.study.app.model;

import project.study.app.model.Question;

/**
 * Represents a free-text question.
 */
public class FreeTextQuestion implements Question<String> {

    private String questionText;
    private String correctAnswer;

    public FreeTextQuestion(String questionText, String correctAnswer) {

        if (questionText == null || questionText.isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be empty.");
        }
        if (correctAnswer == null || correctAnswer.isEmpty()) {
            throw new IllegalArgumentException("Correct answer cannot be empty.");
        }

        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Checks if the given answer is correct.
     * @param inputAnswer The input answer.
     * @return true if the answer is correct, false otherwise
     */
    public Boolean checkAnswer(String inputAnswer) {

        if (inputAnswer == null || inputAnswer.isEmpty())
            throw new IllegalArgumentException("Input answer cannot be empty.");

        return this.correctAnswer.equals(inputAnswer);
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String newText) {
        this.questionText = newText;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public String setCorrectAnswer(String correctAnswer) {
        return this.correctAnswer = correctAnswer;
    }
}
