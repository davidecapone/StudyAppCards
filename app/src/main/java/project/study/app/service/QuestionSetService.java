package project.study.app.service;

import project.study.app.model.domain.QuestionSet;
import project.study.app.model.entity.QuestionSetEntity;
import project.study.app.repository.QuestionSetRepository;

public class QuestionSetService {

    private QuestionSetRepository repository;

    public QuestionSetService(QuestionSetRepository repository) {
        this.repository = repository;
    }

    public void insert(QuestionSet newQuestionSet) {

        if (repository.getQuestionSetByName(newQuestionSet.getQuestionSetName()) != null) {
            throw new IllegalArgumentException("QuestionSet with name " + newQuestionSet.getQuestionSetName() + " already exists.");
        }

        repository.insert(
                toEntity(newQuestionSet)
        );

    }

    private QuestionSetEntity toEntity(QuestionSet questionSet) {
        return new QuestionSetEntity(questionSet.getQuestionSetName(), questionSet.getQuestions());
    }
}
