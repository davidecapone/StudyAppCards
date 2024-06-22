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

    public void insert(QuestionSet questionSet, Callback callback) {
        LiveData<QuestionSetEntity> existingLiveData = repository.getQuestionSetByName(questionSet.getQuestionSetName());
        existingLiveData.observeForever(new Observer<QuestionSetEntity>() {
            @Override
            public void onChanged(QuestionSetEntity existingEntity) {
                if (existingEntity != null) {
                    callback.onError(new IllegalArgumentException("QuestionSet with name " + questionSet.getQuestionSetName() + " already exists."));
                } else {
                    QuestionSetEntity entity = new QuestionSetEntity(questionSet.getQuestionSetName(), questionSet.getQuestions());
                    repository.insert(entity);
                    callback.onSuccess();
                }
                existingLiveData.removeObserver(this);
            }
        });
    }


    @Override
    public void delete(QuestionSet questionSet, Callback callback) {
        LiveData<QuestionSetEntity> existingLiveData = repository.getQuestionSetByName(questionSet.getQuestionSetName());
        existingLiveData.observeForever(new Observer<QuestionSetEntity>() {
            @Override
            public void onChanged(QuestionSetEntity existingEntity) {
                if (existingEntity != null) {
                    repository.delete(existingEntity);
                    callback.onSuccess();
                } else {
                    callback.onError(new IllegalArgumentException("QuestionSet with name " + questionSet.getQuestionSetName() + " does not exist."));
                }
                existingLiveData.removeObserver(this);
            }
        });
    }

    @Override
    public void update(QuestionSet questionSet, Callback callback) {
        LiveData<QuestionSetEntity> existingLiveData = repository.getQuestionSetByName(questionSet.getQuestionSetName());
        existingLiveData.observeForever(new Observer<QuestionSetEntity>() {
            @Override
            public void onChanged(QuestionSetEntity existingEntity) {
                if (existingEntity != null) {
                    existingEntity.setName(questionSet.getQuestionSetName());
                    existingEntity.setQuestions(questionSet.getQuestions());
                    repository.update(existingEntity);
                    callback.onSuccess();
                } else {
                    callback.onError(new IllegalArgumentException("QuestionSet with name " + questionSet.getQuestionSetName() + " does not exist."));
                }
                existingLiveData.removeObserver(this);
            }
        });
    }

    @Override
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

    @Override
    public void getQuestionSetByName(String name, SingleItemCallback<QuestionSet> callback) {
        LiveData<QuestionSetEntity> existingLiveData = repository.getQuestionSetByName(name);
        existingLiveData.observeForever(new Observer<QuestionSetEntity>() {
            @Override
            public void onChanged(QuestionSetEntity existingEntity) {
                if (existingEntity != null) {
                    callback.onSuccess(toDomain(existingEntity));
                } else {
                    callback.onError(new IllegalArgumentException("QuestionSet with name " + name + " does not exist."));
                }
                existingLiveData.removeObserver(this);
            }
        });
    }

}
