package project.study.app.model.domain;

/**
 * A class to represent a free text answer
 */
public class FreeTextAnswer extends Answer<String> {

    public FreeTextAnswer(String correctAnswer) {
        this.setCorrectAnswer(correctAnswer);
    }
}
