package project.study.app;

/**
 * Represents a multiple choice question.
 */
public class MultipleChoiceQuestion {

    private String questionText;
    private Answer[] answers;

    /**
     * Constructor.
     *
     * @param questionText The question text.
     * @param answers The possible answers.
     */
    public MultipleChoiceQuestion(String questionText, Answer[] answers) {
        this.questionText = questionText;
        this.answers = answers;
    }

    /**
     * Gets the question text.
     *
     * @return The question text.
     */
    public String getQuestionText() {
        return this.questionText;
    }

    /**
     * Sets the question text.
     *
     * @param questionText The question text.
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Gets the possible answers.
     *
     * @return The possible answers.
     */
    public Answer[] getAnswers(){
        return this.answers;
    }

    /**
     * Sets the possible answers.
     *
     * @param answers The possible answers.
     */
    public void setAnswers(Answer[] answers){
        this.answers = answers;
    }

    /**
     * Checks if the input answer is the correct one.
     *
     * @return true if the answer is correct, false otherwise.
     */
    public Boolean isCorrect(Answer answer) {
        return answer.getCorrectness();
    }

    /**
     * Represents an answer to a multiple choice question.
     */
    public static class Answer{

        private String answerText;
        private Boolean correctness;

        /**
         * Constructor.
         *
         * @param answerText The answer text.
         * @param correctness The correctness of the answer.
         */
        public Answer(String answerText, Boolean correctness){
            this.answerText = answerText;
            this.correctness = correctness;
        }

        /**
         * Gets the answer text.
         *
         * @return The answer text.
         */
        public String getAnswerText(){
            return this.answerText;
        }

        /**
         * Sets the answer text.
         *
         * @param answerText The answer text.
         */
        public void setAnswerText(String answerText){
            this.answerText = answerText;
        }

        /**
         * Gets the correctness of the answer.
         *
         * @return The correctness of the answer.
         */
        public Boolean getCorrectness(){
            return this.correctness;
        }

        /**
         * Sets the correctness of the answer.
         *
         * @param correctness The correctness of the answer.
         */
        public void setCorrectness(Boolean correctness){
            this.correctness = correctness;
        }

        /**
         * Checks equality of two answer objects.
         *
         * @param obj The object to compare.
         * @return true if the answers are equal, false otherwise.
         */
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
