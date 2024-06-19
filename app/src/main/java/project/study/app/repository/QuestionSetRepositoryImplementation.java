package project.study.app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

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

    public QuestionSetRepositoryImplementation(Context appContext) {
        StudyAppDatabase db = StudyAppDatabase.getDatabase(appContext);
        questionSetDao = db.questionSetDao();
    }

    public void insert(QuestionSetEntity newSet) {
        questionSetDao.insert( newSet );
    }

    public QuestionSetEntity getQuestionSetByName( String setName ) {
        return questionSetDao.getQuestionSetByName(setName);
    }

    public LiveData<List<QuestionSetEntity>> getAllQuestionSets() {
        return questionSetDao.getAllQuestionSets();
    }

    public void update( QuestionSetEntity setToBeUpdated ) {
        this.questionSetDao.update(setToBeUpdated);
    }

    public void delete(QuestionSetEntity setToBeDeleted) {
        this.questionSetDao.delete(setToBeDeleted);
    }

    public void deleteAll() {
        this.questionSetDao.deleteAll();
    }
}
