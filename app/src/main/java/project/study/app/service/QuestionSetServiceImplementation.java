package project.study.app.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.model.entity.QuestionSetEntity;
import project.study.app.repository.QuestionSetRepository;

public class QuestionSetServiceImplementation implements QuestionSetService {

    private final QuestionSetRepository repository;

    public QuestionSetServiceImplementation(QuestionSetRepository repository) {
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

    public void delete(QuestionSet questionSet) {
        // Retrieve the corresponding entity, if it exists
        QuestionSetEntity existingEntity = repository.getQuestionSetByName(questionSet.getQuestionSetName());

        if (existingEntity != null) {
            repository.delete(existingEntity);
        }
    }

    public void update(QuestionSet questionSet) {
        QuestionSetEntity existingEntity = repository.getQuestionSetByName(questionSet.getQuestionSetName());

        if (existingEntity != null) {
            repository.update(existingEntity);
        }
    }

    public void addQuestionToQuestionSet(String questionSetName, Question question) {
        QuestionSetEntity entity = repository.getQuestionSetByName(questionSetName);

        if (entity != null) {
            entity.addQuestion(question);
            repository.update(entity);
        }
    }

    public void removeQuestionFromQuestionSet(String questionSetName, Question question) {
        QuestionSetEntity entity = repository.getQuestionSetByName(questionSetName);
        if (entity != null) {
            entity.getQuestions().remove(question);
            repository.update(entity);
        }
    }

    public LiveData<List<QuestionSet>> getAllQuestionSets() {
        return Transformations.map(repository.getAllQuestionSets(), entities ->
                entities.stream().map(this::toDomain).collect(Collectors.toList())
        );
    }

    private QuestionSet toDomain(QuestionSetEntity entity) {
        QuestionSet domainQuestionSet = new QuestionSet("");
        domainQuestionSet.setQuestionSetName(entity.getName());
        domainQuestionSet.setQuestions(entity.getQuestions());
        return domainQuestionSet;
    }

    private QuestionSetEntity toEntity(QuestionSet questionSet) {
        return new QuestionSetEntity(questionSet.getQuestionSetName(), questionSet.getQuestions());
    }

    public QuestionSet getQuestionSetByName(String name) {
        return toDomain(this.repository.getQuestionSetByName(name));
    }

    public void changeQuestionSetName(String existingQuestionSetName, String newName) {
        QuestionSetEntity existingEntity = repository.getQuestionSetByName(existingQuestionSetName);
        if (existingEntity != null) {
            existingEntity.setName(newName);
            repository.update(existingEntity);
        }
    }
}
