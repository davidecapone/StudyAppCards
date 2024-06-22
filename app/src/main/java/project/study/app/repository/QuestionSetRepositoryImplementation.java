package project.study.app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.entity.QuestionSetEntity;

/**
 * Implementation of the QuestionSetRepository interface.
 * This class is needed to provide a concrete implementation of the repository interface.
 * It handles data operations using the DAO and serves as the single source of truth for QuestionSet data.
 */
public class QuestionSetRepositoryImplementation implements QuestionSetRepository {

    private final QuestionSetDao questionSetDao;
    private final ExecutorService executorService;

    public QuestionSetRepositoryImplementation(Context appContext) {
        StudyAppDatabase db = StudyAppDatabase.getDatabase(appContext);
        questionSetDao = db.questionSetDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void insert(QuestionSetEntity newSet) {
        executorService.execute(() -> questionSetDao.insert(newSet));
    }

    @Override
    public void delete(QuestionSetEntity setToBeDeleted) {
        executorService.execute(() -> questionSetDao.delete(setToBeDeleted));
    }

    @Override
    public LiveData<List<QuestionSetEntity>> getAllQuestionSets() {
        return questionSetDao.getAllQuestionSets();
    }

    @Override
    public LiveData<QuestionSetEntity> getQuestionSetByName(String name) {
        return questionSetDao.getQuestionSetByName(name);
    }

    @Override
    public void update(QuestionSetEntity questionSet) {
        executorService.execute(() -> questionSetDao.update(questionSet));
    }

    @Override
    public void deleteAll() {
        executorService.execute(questionSetDao::deleteAll);
    }
}
