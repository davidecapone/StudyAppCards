package project.study.app.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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

    @FunctionalInterface
    private interface QuestionSetOperation {
        void perform(QuestionSetEntity existingEntity);
    }

    private void handleQuestionSetOperation(String name, QuestionSetOperation operation) {
        LiveData<QuestionSetEntity> existingLiveData = repository.getQuestionSetByName(name);
        existingLiveData.observeForever(new Observer<QuestionSetEntity>() {
            @Override
            public void onChanged(QuestionSetEntity existingEntity) {
                operation.perform(existingEntity);
                existingLiveData.removeObserver(this);
            }
        });
    }

    @Override
    public void insert(QuestionSet questionSet, Callback callback) {
        handleQuestionSetOperation(questionSet.getQuestionSetName(),
            existingEntity -> {
                if (existingEntity != null) {
                    callback.onError(new IllegalArgumentException("QuestionSet with name " + questionSet.getQuestionSetName() + " already exists."));
                } else {
                    QuestionSetEntity entity = toEntity(questionSet);
                    repository.insert(entity);
                    callback.onSuccess();
                }
            }
        );
    }

    @Override
    public void delete(QuestionSet questionSet, Callback callback) {
        handleQuestionSetOperation(questionSet.getQuestionSetName(),
            existingEntity -> {
                if (existingEntity != null) {
                    repository.delete(existingEntity);
                    callback.onSuccess();
                } else {
                    callback.onError(new IllegalArgumentException("QuestionSet with name " + questionSet.getQuestionSetName() + " does not exist."));
                }
            }
        );
    }

    @Override
    public void update(QuestionSet questionSet, Callback callback) {
        handleQuestionSetOperation(questionSet.getQuestionSetName(),
            existingEntity -> {
                if (existingEntity != null) {
                    existingEntity.setName(questionSet.getQuestionSetName());
                    existingEntity.setQuestions(questionSet.getQuestions());
                    repository.update(existingEntity);
                    callback.onSuccess();
                } else {
                    callback.onError(new IllegalArgumentException("QuestionSet with name " + questionSet.getQuestionSetName() + " does not exist."));
                }
            }
        );
    }

    @Override
    public LiveData<List<QuestionSet>> getAllQuestionSets() {
        return Transformations.map(
                repository.getAllQuestionSets(),
                entities -> entities.stream().map(this::toDomain).collect(Collectors.toList())
        );
    }

    @Override
    public void getQuestionSetByName(String name, SingleItemCallback<QuestionSet> callback) {
        handleQuestionSetOperation(name,
            existingEntity -> {
                if (existingEntity != null) {
                    callback.onSuccess(toDomain(existingEntity));
                } else {
                    callback.onError(new IllegalArgumentException("QuestionSet with name " + name + " does not exist."));
                }
            }
        );
    }

    /**
     * Converts a QuestionSetEntity to a QuestionSet domain object.
     *
     * @param entity The entity to convert.
     * @return The converted domain object.
     */
    private QuestionSet toDomain(QuestionSetEntity entity) {
        QuestionSet domainQuestionSet = new QuestionSet(entity.getName());
        domainQuestionSet.setQuestions(entity.getQuestions());
        return domainQuestionSet;
    }

    /**
     * Converts a QuestionSet domain object to a QuestionSetEntity.
     *
     * @param questionSet The domain object to convert.
     * @return The converted entity.
     */
    private QuestionSetEntity toEntity(QuestionSet questionSet) {
        return new QuestionSetEntity(questionSet.getQuestionSetName(), questionSet.getQuestions());
    }
}
