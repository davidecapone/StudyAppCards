package project.study.app.service;

import androidx.lifecycle.LiveData;

import java.util.List;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;

public interface QuestionSetService {

    void insert(QuestionSet questionSet);

    void delete(QuestionSet questionSet);

    LiveData<List<QuestionSet>> getAllQuestionSets();

    QuestionSet getQuestionSetByName(String name);

    void update(QuestionSet questionSet);
}
