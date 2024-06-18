package project.study.app.model.domain;

import java.util.List;

public class AnswerFactory {
    /**
     * Creates an answer of a specified type.
     *
     * @param type           the type of the answer ("FreeText" or "MultipleChoice")
     * @param correctAnswer  the correct answer
     * @param possibleAnswers the list of possible answers for multiple-choice questions
     * @return an instance of Answer
     */
    public static Answer<?> createAnswer(String type, Object correctAnswer, List<String> possibleAnswers) {
        switch (type) {
            case "FreeText":
                return new FreeTextAnswer((String) correctAnswer);
            case "MultipleChoice":
                return new MultipleChoiceTextAnswer(possibleAnswers, (String) correctAnswer);
            default:
                throw new IllegalArgumentException("Unknown answer type: " + type);
        }
    }
}
