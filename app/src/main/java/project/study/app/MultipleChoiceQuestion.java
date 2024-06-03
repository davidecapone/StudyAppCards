package project.study.app;

public class MultipleChoiceQuestion {
    private String question;

    private Answer[] answers;

    public MultipleChoiceQuestion(String question, Answer[] answers) {
        this.question = question;
        this.answers = answers;
    }

    public Boolean checkAnswerCorrectness(Answer answer) {
        return answer.isCorrect();
    }

    public static class Answer{
        private String answerText;
        private Boolean correctness;

        public Answer(String answerText, Boolean correctness){
            this.answerText = answerText;
            this.correctness = correctness;
        }

        public Boolean isCorrect(){
            return this.correctness;
        }
    }
}
