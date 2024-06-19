package project.study.app.persistence;

import project.study.app.model.domain.QuestionSet;

import java.util.List;
import java.util.ArrayList;

/**
 * Fake repository for testing purposes
 */
public class FakeRepository {

    // list of question sets
    List<QuestionSet> questionSets;

    public FakeRepository() {
        questionSets = new ArrayList<QuestionSet>();
        questionSets.add(new QuestionSet("Question Set 1"));
        questionSets.add(new QuestionSet("Question Set 2"));
        questionSets.add(new QuestionSet("Question Set 3"));
    }

    public List<QuestionSet> getAllQuestionSets() {
        return questionSets;
    }

    public void addQuestionSet(QuestionSet newQuestionSet) {
        questionSets.add(newQuestionSet);
    }

    public QuestionSet searchQuestionSet(String questionSetName) throws QuestionSetNotFoundException {
        return questionSets.stream()
                .filter(questionSet -> questionSet.getQuestionSetName().equals(questionSetName))
                .findFirst()
                .orElseThrow(() -> new QuestionSetNotFoundException("Question set " + questionSetName + " not found"));
    }

    public class QuestionSetNotFoundException extends RuntimeException {

        public QuestionSetNotFoundException(String message) {
            super(message);
        }

    }
}
