package project.study.app.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import project.study.app.model.entity.QuestionSetEntity;

/**
 * DAO (Data Access Object) interface for accessing QuestionSet entities from the database.
 * Provides methods for performing CRUD operations.
 * This interface is needed to abstract the database operations related to QuestionSetEntity.
 */
@Dao
public interface QuestionSetDao {

    /**
     * Inserts a new QuestionSet into the database.
     *
     * @param questionSetEntity the QuestionSet to insert
     */
    @Insert
    void insert(QuestionSetEntity questionSetEntity);

    /**
     * Deletes a QuestionSet from the database.
     *
     * @param questionSetEntity the QuestionSet to delete
     */
    @Delete
    void delete(QuestionSetEntity questionSetEntity);

    /**
     * Updates a QuestionSet in the database.
     *
     * @param questionSetEntity the QuestionSet to update
     */
    @Update
    void update(QuestionSetEntity questionSetEntity);

    /**
     * Deletes all QuestionSet entities from the database.
     */
    @Query("DELETE FROM question_sets")
    void deleteAll();

    /**
     * Retrieves a QuestionSet by its name from the database.
     *
     * @param name the name of the QuestionSet to retrieve
     * @return the QuestionSet with the given name
     */
    @Query("SELECT * FROM question_sets WHERE name = :name LIMIT 1")
    LiveData<QuestionSetEntity> getQuestionSetByName(String name);

    /**
     * Retrieves all QuestionSet entities from the database.
     * 
     * @return a list of all QuestionSet entities
     */
    @Query("SELECT * FROM question_sets")
    LiveData<List<QuestionSetEntity>> getAllQuestionSets();
}
