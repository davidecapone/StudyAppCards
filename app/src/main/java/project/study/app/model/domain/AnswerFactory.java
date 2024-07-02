package project.study.app.model.domain;

import java.util.List;

/**
 * Factory class for creating Answer objects.
 */
public class AnswerFactory {
    /**
     * Creates a MultipleChoiceTextAnswer
     *
     * @param correctAnswer   the correct answer
     * @param possibleAnswers the list of possible answers for multiple-choice questions
     * @return an instance of Answer
     */
    public static Answer<?> createMultipleChoiceAnswer(Object correctAnswer, List<String> possibleAnswers) {
        return new MultipleChoiceTextAnswer(possibleAnswers, (String) correctAnswer);
    }
    /**
     * Creates a FreeTextAnswer
     *
     * @param correctAnswer the correct answer
     * @return an instance of Answer
     */
    public static Answer<?> createFreeTextAnswer(Object correctAnswer) {
        return new FreeTextAnswer((String) correctAnswer);
    }
}
