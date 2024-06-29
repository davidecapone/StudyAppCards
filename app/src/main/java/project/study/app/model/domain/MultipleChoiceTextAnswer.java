package project.study.app.model.domain;

import java.util.List;

/**
 * A class to represent a multiple choice answer composed of just text answers
 */
public class MultipleChoiceTextAnswer extends Answer<String> {

    // List of possible answers
    private List<String> possibleAnswers;

    /**
     * Constructs a MultipleChoiceTextAnswer with the specified possible answers and correct answer.
     *
     * @param possibleAnswers the list of possible answers
     * @param correctAnswer   the correct answer
     */
    public MultipleChoiceTextAnswer(List<String> possibleAnswers, String correctAnswer) {
        this.possibleAnswers = possibleAnswers;
        setCorrectAnswer(correctAnswer);
    }

    /**
     * Gets the list of possible answers.
     * @return the list of possible answers
     */
    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    /**
     * Sets the list of possible answers.
     * @param possibleAnswers the new list of possible answers
     */
    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    /**
     * Checks if the given answer is correct.
     * @param answer the answer to check
     * @return true if the answer is correct, false otherwise
     */
    @Override
    public boolean isCorrect(Object answer) {
        return getCorrectAnswer().equals(answer);
    }
}
