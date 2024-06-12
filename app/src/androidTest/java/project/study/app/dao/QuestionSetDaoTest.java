package project.study.app.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.dao.StudyAppDatabase;
import project.study.app.model.domain.FreeTextQuestion;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class QuestionSetDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private StudyAppDatabase db;
    private QuestionSetDao questionSetDao;


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.inMemoryDatabaseBuilder(context, StudyAppDatabase.class)
                .allowMainThreadQueries()
                .build();

        questionSetDao = db.questionSetDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }



    @Test
    public void insert_and_then_retrieve_questionSet() {

        // create a sample question set:
        FreeTextQuestion ftq1 = new FreeTextQuestion("What is your name?", "Davide");
        FreeTextQuestion ftq2 = new FreeTextQuestion("What is your name?", "Sandro");
        QuestionSet questionSet = new QuestionSet("Sample question set 1");
        questionSet.addQuestion(ftq1);
        questionSet.addQuestion(ftq2);

        // insert the new question set:
        questionSetDao.insert(questionSet);

        // get the question set by its name:
        QuestionSet retrievedQuestionSet = questionSetDao.getQuestionSet("Sample question set 1");

        // (retrieved) question set should be not null:
        assertNotNull(retrievedQuestionSet);
        // the (retrieved) question set name should be equal to the question set created above:
        assertEquals(questionSet.getQuestionSetName(), retrievedQuestionSet.getQuestionSetName());
        // the size should be the same:
        assertEquals(questionSet.getAllQuestions().size(), retrievedQuestionSet.getAllQuestions().size());
    }



}
