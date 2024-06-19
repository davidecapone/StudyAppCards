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

    void insert( QuestionSetEntity newQuestionSetEntity );

    QuestionSetEntity getQuestionSetByName( String name );

    LiveData<List<QuestionSetEntity>> getAllQuestionSets();

    void update( QuestionSetEntity toBeUpdated );

    void delete( QuestionSetEntity toBeDeleted );

    void deleteAll();
}
