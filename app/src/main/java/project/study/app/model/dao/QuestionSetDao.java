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
     * @param questionSetEntity the QuestionSet to insert
     */
    @Insert
    void insert(QuestionSetEntity questionSetEntity);

    @Query("SELECT * FROM question_sets WHERE name = :questionSetName LIMIT 1")
    QuestionSetEntity getQuestionSetByName(String questionSetName);

    @Query("SELECT * FROM question_sets")
    LiveData<List<QuestionSetEntity>> getAllQuestionSets();

    @Delete
    void delete(QuestionSetEntity questionSetEntity);

    @Update
    void update(QuestionSetEntity questionSetEntity);

    @Query("DELETE FROM question_sets")
    void deleteAll();
}
