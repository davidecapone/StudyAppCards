package project.study.app;

public class MultipleChoiceQuestion {
    private String questionText;
    private Answer[] answers;

    public MultipleChoiceQuestion(String questionText, Answer[] answers) {
        this.questionText = questionText;
        this.answers = answers;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Checks if the input answer is correct.
     *
     * @return true if the answer is correct, false otherwise.
     */
    public Boolean isCorrect(Answer answer) {
        return answer.getCorrectness();
    }

    public Answer[] getAnswers(){
        return this.answers;
    }

    public static class Answer{
        private String answerText;
        private Boolean correctness;

        public Answer(String answerText, Boolean correctness){
            this.answerText = answerText;
            this.correctness = correctness;
        }

        public Boolean getCorrectness(){
            return this.correctness;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Answer answer = (Answer) obj;
            return answerText.equals(answer.answerText) && correctness.equals(answer.correctness);
        }

        public String getAnswerText(){
            return this.answerText;
        }

        public void setAnswerText(String answerText){
            this.answerText = answerText;
        }

        public void setCorrectness(Boolean correctness){
            this.correctness = correctness;
        }
    }
}
