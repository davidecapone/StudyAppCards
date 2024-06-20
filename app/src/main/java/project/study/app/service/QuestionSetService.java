package project.study.app.service;

import androidx.lifecycle.LiveData;

import java.util.List;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;

public interface QuestionSetService {
    /**
     * Inserts a new QuestionSet into the repository.
     * @param questionSet The QuestionSet to be inserted.
     * @throws IllegalArgumentException if a QuestionSet with the same name already exists.
     */
    void insert(QuestionSet questionSet);


    /**
     * Deletes an existing QuestionSet from the repository.
     * @param questionSet The QuestionSet to be deleted.
     */
    void delete(QuestionSet questionSet);

    /**
     * Retrieves all QuestionSets.
     * @return A LiveData object containing a list of all QuestionSets.
     */
    LiveData<List<QuestionSet>> getAllQuestionSets();

    /**
     * Adds a question to an existing QuestionSet.
     * @param questionSetName The name of the QuestionSet.
     * @param question The Question to be added.
     */
    void addQuestionToQuestionSet(String questionSetName, Question question);

    /**
     * Removes a question from an existing QuestionSet.
     * @param questionSetName The name of the QuestionSet.
     * @param question The Question to be removed.
     */
    void removeQuestionFromQuestionSet(String questionSetName, Question question);

    /**
     * Changes the name of an existing QuestionSet.
     * @param existingQuestionSetName The current name of the QuestionSet.
     * @param newName The new name of the QuestionSet.
     */
    void changeQuestionSetName(String existingQuestionSetName, String newName);

    QuestionSet getQuestionSetByName(String name);

    void update(QuestionSet questionSet);
}
