package project.study.app.model.domain;

/**
 * A class to represent a free text answer
 */
public class FreeTextAnswer extends Answer<String> {

    /**
     * Constructs a FreeTextAnswer with the specified correct answer.
     *
     * @param correctAnswer the correct answer
     */
    public FreeTextAnswer(String correctAnswer) {
        setCorrectAnswer(correctAnswer);
    }

    /**
     * Checks if the given answer is correct.
     *
     * @param answer the answer to check
     * @return true if the answer is correct, false otherwise
     */
    @Override
    public boolean isCorrect(Object answer) {
        return getCorrectAnswer().equals(answer);
    }
}
