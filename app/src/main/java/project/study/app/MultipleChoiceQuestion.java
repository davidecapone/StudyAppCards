package project.study.app;
/**
 * Represents a multiple choice question.
 */
public class MultipleChoiceQuestion implements Question<MultipleChoiceQuestion.Answer>{
    private String questionText;
    private Answer[] answers;

    /**
     * Constructor for a multiple choice question.
     * @param questionText The question text.
     * @param answers The possible answers.
     */
    public MultipleChoiceQuestion(String questionText, Answer[] answers) {
        this.questionText = questionText;
        this.answers = answers;
    }

    /**
     * Checks if the input answer is the correct one.
     * @param answer The answer to check.
     * @return true if the answer is correct, false otherwise.
     */
    public Boolean checkAnswer(Answer answer) {
        return answer.getCorrectness();
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public Answer[] getAnswers(){
        return this.answers;
    }

    public void setQuestionText(String newText) {
        this.questionText = newText;
    }

    public void setAnswers(Answer[] answers){
        this.answers = answers;
    }

    public static class Answer{
        /**
         * Represents an answer to a multiple choice question.
         */
        private String answerText;
        private Boolean correctness;

        /**
         * Constructor for an answer.
         * @param answerText The text of the answer.
         * @param correctness The correctness of the answer.
         */
        public Answer(String answerText, Boolean correctness){
            this.answerText = answerText;
            this.correctness = correctness;
        }


        public String getAnswerText(){
            return this.answerText;
        }

        public void setAnswerText(String answerText){
            this.answerText = answerText;
        }

        public Boolean getCorrectness(){
            return this.correctness;
        }

        public void setCorrectness(Boolean correctness){
            this.correctness = correctness;
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
    }
}
