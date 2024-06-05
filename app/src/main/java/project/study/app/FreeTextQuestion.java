package project.study.app;
/**
 * Represents a free-text question.
 */
public class FreeTextQuestion implements Question<String>{
    private String questionText;
    private String correctAnswer;

    /**
     * Constructor for a free-text question.
     * @param questionText The question text.
     * @param correctAnswer The correct answer.
     */
    public FreeTextQuestion(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Checks if the given answer is correct.
     * @param inputAnswer The input answer.
     * @return true if the answer is correct, false otherwise
     */
    public Boolean checkAnswer(String inputAnswer) {
        return this.correctAnswer.equals(inputAnswer);
    }

    public String getQuestionText() {
        return this.questionText;
    }

    @Override
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
