package project.study.app.model.domain;

import java.util.List;

/**
 * A class to represent a multiple choice answer composed of just text answers
 */
public class MultipleChoiceTextAnswer extends Answer<String> {

    private List<String> possibleAnswers;

    public MultipleChoiceTextAnswer(List<String> possibleAnswers, String correctAnswer) {
        this.possibleAnswers = possibleAnswers;
        this.correctAnswer = correctAnswer;
    }
    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
