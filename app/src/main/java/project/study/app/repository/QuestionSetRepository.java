package project.study.app.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import project.study.app.model.entity.QuestionSetEntity;

/**
 * Repository interface for QuestionSet entities.
 * This interface is needed to abstract the data operations and provide a clean API for data access to the rest of the application.
 * It serves as a mediator between different data sources (e.g., database, network) and the rest of the app.
 */
public interface QuestionSetRepository {

    /**
     * Insert a new QuestionSetEntity into the database.
     *
     * @param newQuestionSetEntity
     */
    void insert( QuestionSetEntity newQuestionSetEntity );

    /**
     * Get a QuestionSetEntity by its name.
     *
     * @param name
     * @return
     */
    LiveData<QuestionSetEntity> getQuestionSetByName(String name);

    /**
     * Get all QuestionSetEntity objects from the database.
     *
     * @return
     */
    LiveData<List<QuestionSetEntity>> getAllQuestionSets();

    /**
     * Update an existing QuestionSetEntity in the database.
     *
     * @param toBeUpdated
     */
    void update( QuestionSetEntity toBeUpdated );

    /**
     * Delete a QuestionSetEntity from the database.
     *
     * @param toBeDeleted
     */
    void delete( QuestionSetEntity toBeDeleted );

    /**
     * Delete all QuestionSetEntity objects from the database.
     */
    void deleteAll();
}
