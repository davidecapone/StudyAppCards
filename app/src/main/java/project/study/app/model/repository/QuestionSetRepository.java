package project.study.app.model.repository;

import androidx.annotation.NonNull;

import java.util.List;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.domain.QuestionSet;

/**
 * This class acts as a repository for handling data operations related to `QuestionSet` objects.
 * It uses a `QuestionSetDao` for interacting with the database.
 */
public class QuestionSetRepository implements IQuestionSetsRepository{

    // The DAO used for database operations on QuestionSet objects:
    private final QuestionSetDao questionSetDao;

    /**
     * Constructs a new QuestionSetRepository with a given database.
     * @param db The database instance used for obtaining the QuestionSetDao.
     */
    public QuestionSetRepository(@NonNull StudyAppDatabase db) {
        this.questionSetDao = db.questionSetDao();
    }

    /**
     * Inserts a new QuestionSet into the database.
     * @param newQuestionSet The QuestionSet to be inserted.
     */
    public void insertQuestionSet(QuestionSet newQuestionSet) {
        this.questionSetDao.insert(newQuestionSet);
    }

    /**
     * Retrieves a QuestionSet from the database by its name.
     * @param questionSetName The name of the QuestionSet to retrieve.
     * @return The retrieved QuestionSet, or null if not found.
     */
    public QuestionSet retrieveQuestionSet(String questionSetName) {
        return this.questionSetDao.getQuestionSet(questionSetName);
    }

    /**
     * Retrieves all QuestionSets from the database.
     * @return A list of all QuestionSets.
     */
    public List<QuestionSet> getAllQuestionSets() {
        return this.questionSetDao.getAllQuestionSets();
    }

    /**
     * Deletes a given QuestionSet from the database.
     * @param questionSet The QuestionSet to delete.
     */
    public void deleteQuestionSet(QuestionSet questionSet) {
        this.questionSetDao.delete(questionSet);
    }
}
