package project.study.app.service.interfaces;

import androidx.lifecycle.LiveData;
import java.util.List;
import project.study.app.model.domain.QuestionSet;

/**
 * A service interface to manage QuestionSets.
 */
public interface Service {

    /**
     * Inserts a new QuestionSet.
     *
     * @param questionSet The QuestionSet to insert.
     * @param callback The callback to notify the result.
     * @throws IllegalArgumentException if a QuestionSet with the same name already exists.
     */
    void insert(QuestionSet questionSet, Callback callback);

    /**
     * Deletes an existing QuestionSet.
     *
     * @param questionSet The QuestionSet to delete.
     * @param callback The callback to notify the result.
     * @throws IllegalArgumentException if the QuestionSet does not exist.
     */
    void delete(QuestionSet questionSet, Callback callback);

    /**
     * Updates an existing QuestionSet.
     *
     * @param questionSet The QuestionSet to update.
     * @param callback The callback to notify the result.
     * @throws IllegalArgumentException if the QuestionSet does not exist.
     */
    void update(QuestionSet questionSet, Callback callback);

    /**
     * Retrieves all QuestionSets.
     *
     * @return A LiveData list of all QuestionSets.
     */
    LiveData<List<QuestionSet>> getAllQuestionSets();

    /**
     * Retrieves a QuestionSet by name.
     *
     * @param name The name of the QuestionSet.
     * @param callback The callback to notify the result.
     * @throws IllegalArgumentException if the QuestionSet does not exist.
     */
    void getQuestionSetByName(String name, SingleItemCallback<QuestionSet> callback);
}
