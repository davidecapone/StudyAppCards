package project.study.app.model.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import project.study.app.model.entity.QuestionSetEntity;

public interface QuestionSetRepository {

    void insert( QuestionSetEntity newQuestionSetEntity );

    QuestionSetEntity getQuestionSetByName( String name );

    LiveData<List<QuestionSetEntity>> getAllQuestionSets();

    void update( QuestionSetEntity toBeUpdated );

    void delete( QuestionSetEntity toBeDeleted );

    void deleteAll();
}
