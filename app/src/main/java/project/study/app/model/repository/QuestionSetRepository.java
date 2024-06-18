package project.study.app.model.repository;

import androidx.annotation.NonNull;

import java.util.List;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.domain.QuestionSet;

public class QuestionSetRepository implements IQuestionSetsRepository{

    private final QuestionSetDao questionSetDao;

    public QuestionSetRepository(@NonNull StudyAppDatabase db) {
        this.questionSetDao = db.questionSetDao();
    }

    public void insertQuestionSet(QuestionSet newQuestionSet) {
        this.questionSetDao.insert(newQuestionSet);
    }

    public QuestionSet retrieveQuestionSet(String questionSetName) {
        return this.questionSetDao.getQuestionSet(questionSetName);
    }

    public List<QuestionSet> getAllQuestionSets() {
        return this.questionSetDao.getAllQuestionSets();
    }

    public void deleteQuestionSet(QuestionSet questionSet) {
        this.questionSetDao.delete(questionSet);
    }
}
