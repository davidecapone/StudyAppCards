package project.study.app.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import project.study.app.model.domain.QuestionSet;

@Dao
public interface QuestionSetDao {
    @Insert
    void insert(QuestionSet questionSet);

    @Query("SELECT * FROM question_sets WHERE questionSetName = :questionSetName")
    QuestionSet getQuestionSet(String questionSetName);
}
