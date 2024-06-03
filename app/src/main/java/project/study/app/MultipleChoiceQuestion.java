package project.study.app;

public class MultipleChoiceQuestion {
    private String question;
    private String answer1;

    private String answer2;

    private String correctAnswer;

    public MultipleChoiceQuestion(String question, String answer1, String answer2, String correctAnswer) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }
}
