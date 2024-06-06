package project.study.app.model;

/**
 * Represents a multiple choice question.
 */
public class MultipleChoiceQuestion implements Question<MultipleChoiceQuestion.Answer> {

    private String questionText;
    private Answer[] answers;

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

    /**
     * Represents an answer to a multiple choice question.
     */
    public static class Answer{

        private String answerText;
        private Boolean correctness;

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
    }
}
