package project.study.app.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import project.study.app.model.domain.QuestionSet;
import project.study.app.model.entity.QuestionSetEntity;
import project.study.app.repository.interfaces.Repository;
import project.study.app.service.interfaces.Callback;
import project.study.app.service.interfaces.QuestionSetService;
import project.study.app.service.interfaces.SingleItemCallback;

/**
 * A service class to manage QuestionSets.
 */
public class QuestionSetServiceImplementation implements QuestionSetService {

    // The repository to manage QuestionSetEntities.
    private final Repository repository;

    /**
     * Creates a new QuestionSetServiceImplementation.
     *
     * @param repository The repository to manage QuestionSetEntities.
     */
    public QuestionSetServiceImplementation(Repository repository) {
        this.repository = repository;
    }

    /**
     * A functional interface to perform an operation on a QuestionSetEntity.
     */
    @FunctionalInterface
    private interface QuestionSetOperation {
        void perform(QuestionSetEntity existingEntity);
    }

    /**
     * Handles an operation on a QuestionSetEntity.
     *
     * @param name The name of the QuestionSet.
     * @param operation The operation to perform.
     */
    private void handleQuestionSetOperation(String name, QuestionSetOperation operation) {

        // Observe the LiveData of the QuestionSetEntity with the given name.
        LiveData<QuestionSetEntity> existingLiveData = repository.getQuestionSetByName(name);

        // Perform the operation when the LiveData is updated.
        existingLiveData.observeForever(new Observer<QuestionSetEntity>() {

            /**
             * Performs the operation on the existing entity and removes the observer.
             *
             * @param existingEntity The existing entity.
             */
            @Override
            public void onChanged(QuestionSetEntity existingEntity) {

                operation.perform(existingEntity);

                existingLiveData.removeObserver(this);
            }
        });
    }

    /**
     * Inserts a new QuestionSet.
     *
     * @param questionSet The QuestionSet to insert.
     * @param callback The callback to notify the result.
     */
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

    /**
     * Deletes an existing QuestionSet.
     *
     * @param questionSet The QuestionSet to delete.
     * @param callback The callback to notify the result.
     */
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

    /**
     * Updates an existing QuestionSet.
     *
     * @param questionSet The QuestionSet to update.
     * @param callback The callback to notify the result.
     */
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

    /**
     * Retrieves all QuestionSets.
     *
     * @return A LiveData list of all QuestionSets.
     */
    @Override
    public LiveData<List<QuestionSet>> getAllQuestionSets() {

        return Transformations.map(
                repository.getAllQuestionSets(),
                entities -> entities.stream().map(this::toDomain).collect(Collectors.toList())
        );
    }

    /**
     * Retrieves a QuestionSet by name.
     *
     * @param name The name of the QuestionSet.
     * @param callback The callback to notify the result.
     */
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
