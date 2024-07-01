package project.study.app.repository;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.entity.QuestionSetEntity;
import project.study.app.repository.interfaces.Repository;

/**
 * Implementation of the QuestionSetRepository interface.
 * This class is needed to provide a concrete implementation of the repository interface.
 * It handles data operations using the DAO and serves as the single source of truth for QuestionSet data.
 */
public class RepositoryImplementation implements Repository {

    // DAO for QuestionSetEntity objects
    private final QuestionSetDao questionSetDao;

    // ExecutorService for handling asynchronous tasks
    private final ExecutorService executorService;

    /**
     * Constructor for the QuestionSetRepositoryImplementation class.
     *
     * @param questionSetDao The DAO for QuestionSetEntity objects.
     * @param executorService The ExecutorService for handling asynchronous tasks.
     */
    public RepositoryImplementation(QuestionSetDao questionSetDao, ExecutorService executorService) {
        this.questionSetDao = questionSetDao;
        this.executorService = executorService;
    }

    /**
     * Insert a new QuestionSetEntity into the database.
     *
     * @param newSet The new QuestionSetEntity to be inserted.
     */
    @Override
    public void insert(QuestionSetEntity newSet) {
        executorService.execute(() -> questionSetDao.insert(newSet));
    }

    /**
     * Delete a QuestionSetEntity from the database.
     *
     * @param setToBeDeleted The QuestionSetEntity to be deleted.
     */
    @Override
    public void delete(QuestionSetEntity setToBeDeleted) {
        executorService.execute(() -> questionSetDao.delete(setToBeDeleted));
    }

    /**
     * Update an existing QuestionSetEntity in the database.
     *
     * @param questionSet The QuestionSetEntity to be updated.
     */
    @Override
    public void update(QuestionSetEntity questionSet) {
        executorService.execute(() -> questionSetDao.update(questionSet));
    }

    /**
     * Get all QuestionSetEntity objects from the database.
     *
     * @return A LiveData object containing a list of all QuestionSetEntity objects.
     */
    @Override
    public LiveData<List<QuestionSetEntity>> getAllQuestionSets() {
        return questionSetDao.getAllQuestionSets();
    }

    /**
     * Get a QuestionSetEntity by its name.
     *
     * @param name The name of the QuestionSetEntity to retrieve.
     * @return A LiveData object containing the QuestionSetEntity with the specified name.
     */
    @Override
    public LiveData<QuestionSetEntity> getQuestionSetByName(String name) {
        return questionSetDao.getQuestionSetByName(name);
    }

    /**
     * Delete all QuestionSetEntity objects from the database.
     */
    @Override
    public void deleteAll() {
        executorService.execute(questionSetDao::deleteAll);
    }
}
