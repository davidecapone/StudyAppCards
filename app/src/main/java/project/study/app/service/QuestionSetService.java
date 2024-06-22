package project.study.app.service;

import androidx.lifecycle.LiveData;

import java.util.List;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;

public interface QuestionSetService {

    void insert(QuestionSet questionSet, Callback callback);

    void delete(QuestionSet questionSet, Callback callback);

    LiveData<List<QuestionSet>> getAllQuestionSets();

    void getQuestionSetByName(String name, SingleItemCallback<QuestionSet> callback);

    void update(QuestionSet questionSet, Callback callback);
}
