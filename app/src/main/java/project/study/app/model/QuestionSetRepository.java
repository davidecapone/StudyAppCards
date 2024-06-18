package project.study.app.model;

import android.content.Context;

import java.util.List;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.domain.QuestionSet;
import project.study.app.model.entity.QuestionSetEntity;

public class QuestionSetRepository {

    private final QuestionSetDao questionSetDao;

    public QuestionSetRepository(Context appContext) {
        StudyAppDatabase db = StudyAppDatabase.getDatabase(appContext);
        questionSetDao = db.questionSetDao();
    }

    public void insert(QuestionSetEntity questionSet) {
        questionSetDao.insert(questionSet);
    }

    public QuestionSetEntity getQuestionSetByName(String name) {
        return questionSetDao.getQuestionSetByName(name);
    }

    public List<QuestionSetEntity> getAllQuestionSets() {
        return questionSetDao.getAllQuestionSets();
    }

    public void update(QuestionSetEntity questionSet) {
        this.questionSetDao.update(questionSet);
    }

    public void delete(QuestionSetEntity questionSet) {
        this.questionSetDao.delete(questionSet);
    }
}
