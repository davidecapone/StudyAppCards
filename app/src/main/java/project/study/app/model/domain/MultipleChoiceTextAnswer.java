package project.study.app.model.domain;

import java.util.List;

/**
 * A class to represent a multiple choice answer composed of just text answers
 */
public class MultipleChoiceTextAnswer extends Answer<String> {
    private List<String> options;

    /**
     * Constructs a MultipleChoiceTextAnswer with the specified possible answers and correct answer.
     *
     * @param options the list of possible answers
     * @param correctAnswer the correct answer
     */
    public MultipleChoiceTextAnswer(List<String> options, String correctAnswer) {
        this.options = options;
        setCorrectAnswer(correctAnswer);
    }
    /**
     * Gets the list of possible answers.
     *
     * @return the list of possible answers
     */
    public List<String> getOptions() {
        return options;
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
