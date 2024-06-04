package project.study.app;

public class FreeTextQuestion {
    private String question;
    private String correctAnswer;

    public FreeTextQuestion(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public boolean checkAnswer(String inputAnswer) {
        return this.correctAnswer.equals(inputAnswer);
    }

    public String getQuestion() {
        return this.question;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public String setQuestion(String question) {
        return this.question = question;
    }

    public String setCorrectAnswer(String correctAnswer) {
        return this.correctAnswer = correctAnswer;
    }
}
