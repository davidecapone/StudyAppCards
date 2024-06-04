package project.study.app;

public class MultipleChoiceQuestion {
    private String question;
    private Answer[] answers;

    public MultipleChoiceQuestion(String question, Answer[] answers) {
        this.question = question;
        this.answers = answers;
    }

    /**
     * Checks if the input answer is correct.
     *
     * @return true if the answer is correct, false otherwise.
     */
    public Boolean isCorrect(Answer answer) {
        return answer.getCorrectAnswer();
    }

    public static class Answer{
        private String answerText;
        private Boolean correctness;

        public Answer(String answerText, Boolean correctness){
            this.answerText = answerText;
            this.correctness = correctness;
        }

        public Boolean getCorrectAnswer(){
            return this.correctness;
        }
    }
}
