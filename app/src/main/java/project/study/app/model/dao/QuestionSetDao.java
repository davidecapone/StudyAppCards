package project.study.app.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import project.study.app.model.domain.QuestionSet;

/**
 * Data Access Object (DAO) for the QuestionSet entity.
 * Provides methods for performing database operations on QuestionSets.
 */
@Dao
public interface QuestionSetDao {

    /**
     * Inserts a new QuestionSet into the database.
     * @param questionSet the QuestionSet to insert
     */
    @Insert
    void insert(QuestionSet questionSet);

    /**
     * Retrieves a QuestionSet by its name.
     * @param questionSetName the name of the QuestionSet to retrieve
     * @return the QuestionSet with the specified name, or null if not found
     */
    @Query("SELECT * FROM question_sets WHERE questionSetName = :questionSetName")
    QuestionSet getQuestionSet(String questionSetName);

    /**
     * Retrieves all QuestionSets from the database.
     * @return a list of all QuestionSets
     */
    @Query("SELECT * FROM question_sets")
    List<QuestionSet> getAllQuestionSets();

    /**
     * Deletes a specific QuestionSet from the database.
     * @param questionSet the QuestionSet to delete
     */
    @Delete
    void delete(QuestionSet questionSet);

    /**
     * Updates an existing QuestionSet in the database.
     * @param questionSet the QuestionSet to update
     */
    @Update
    void update(QuestionSet questionSet);
}
