package project.study.app.model.repository;

import java.util.List;

import project.study.app.model.domain.QuestionSet;

public interface IQuestionSetsRepository {

    void insertQuestionSet(QuestionSet newQuestionSet);

    QuestionSet retrieveQuestionSet(String questionSetName);

    void deleteQuestionSet(QuestionSet questionSet);

    List<QuestionSet> getAllQuestionSets();
}
