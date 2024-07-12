package project.study.app.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.entity.QuestionSetEntity;
import project.study.app.repository.interfaces.Repository;

/**
 * - Concrete implementation of the Repository interface for QuestionSet entities.
 * - This class handles data operations using the QuestionSetDao
 *      and executes tasks asynchronously using an ExecutorService.
 * - It acts as the single source of truth for QuestionSet data within the application.
 */
public class RepositoryImplementation implements Repository<QuestionSetEntity, String> {
    private final QuestionSetDao questionSetDao;
    private final ExecutorService executorService;

    /**
     * Constructs a new RepositoryImplementation instance.
     *
     * @param questionSetDao The DAO for accessing QuestionSet entities.
     * @param executorService The ExecutorService for executing asynchronous tasks.
     */
    public RepositoryImplementation(QuestionSetDao questionSetDao, ExecutorService executorService) {
        this.questionSetDao = questionSetDao;
        this.executorService = executorService;
    }

    /**
     * Inserts a new QuestionSetEntity into the database.
     *
     * @param newSet The QuestionSetEntity to be inserted.
     */
    @Override
    public void insert(QuestionSetEntity newSet) {
        executorService.execute(() -> questionSetDao.insert(newSet));
    }

    /**
     * Deletes a QuestionSetEntity from the database.
     *
     * @param setToBeDeleted The QuestionSetEntity to be deleted.
     */
    @Override
    public void delete(QuestionSetEntity setToBeDeleted) {
        executorService.execute(() -> questionSetDao.delete(setToBeDeleted));
    }

    /**
     * Updates an existing QuestionSetEntity in the database.
     *
     * @param questionSet The QuestionSetEntity to be updated.
     */
    @Override
    public void update(QuestionSetEntity questionSet) {
        executorService.execute(() -> questionSetDao.update(questionSet));
    }

    /**
     * Retrieves all QuestionSetEntity objects from the database.
     *
     * @return A LiveData object containing a list of all QuestionSetEntity objects.
     */
    @Override
    public LiveData<List<QuestionSetEntity>> getAllEntities() {
        return questionSetDao.getAllQuestionSets();
    }

    /**
     * Retrieves a QuestionSetEntity by its name.
     *
     * @param name The name of the QuestionSetEntity to retrieve.
     * @return A LiveData object containing the QuestionSetEntity with the specified name.
     */
    @Override
    public LiveData<QuestionSetEntity> getEntityByName(String name) {
        return questionSetDao.getQuestionSetByName(name);
    }

    /**
     * Deletes all QuestionSetEntity objects from the database.
     */
    @Override
    public void deleteAll() {
        executorService.execute(questionSetDao::deleteAll);
    }
}
