
package project.study.app.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import java.util.List;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.domain.FreeTextAnswer;
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
        // Get the application context to be used for building the database.
        Context context = ApplicationProvider.getApplicationContext();

        // Build an in-memory database instance for testing purposes (not persisted to disk).
        db = Room.inMemoryDatabaseBuilder(context, StudyAppDatabase.class)
                .allowMainThreadQueries() // Allow database operations to run on the main thread for the sake of testing.
                .build();

        // Retrieve the DAO for accessing QuestionSet data in the database.
        questionSetDao = db.questionSetDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }



    @Test
    public void insert_new_questionSet() {

        QuestionSet newQuestionSet = new QuestionSet("Sample");

        // INSERT DAO:
        questionSetDao.insert(newQuestionSet);

        // retrieve the question set by its name:
        QuestionSet retrievedQuestionSet = questionSetDao.getQuestionSet("Sample");

        assertNotNull(retrievedQuestionSet);
        assertEquals(newQuestionSet.getQuestionSetName(), retrievedQuestionSet.getQuestionSetName());
        assertEquals(newQuestionSet.getAllQuestions().size(), retrievedQuestionSet.getAllQuestions().size());
    }

    @Test
    public void retrieve_all_questionSets() {
        QuestionSet newQuestionSet1 = new QuestionSet("Sample1");
        QuestionSet newQuestionSet2 = new QuestionSet("Sample2");
        QuestionSet newQuestionSet3 = new QuestionSet("Sample3");

        questionSetDao.insert(newQuestionSet1);
        questionSetDao.insert(newQuestionSet2);
        questionSetDao.insert(newQuestionSet3);

        List<QuestionSet> allQuestionSets = questionSetDao.getAllQuestionSets();

        assertNotNull(allQuestionSets);
        assertEquals(3, allQuestionSets.size());
        assertEquals("Sample1", allQuestionSets.get(0).getQuestionSetName());
        assertEquals("Sample2", allQuestionSets.get(1).getQuestionSetName());
        assertEquals("Sample3", allQuestionSets.get(2).getQuestionSetName());
    }

    @Test
    public void delete_questionSet() {
        QuestionSet questionSet1 = new QuestionSet("Sample1");
        QuestionSet questionSet2 = new QuestionSet("Sample2");

        // INSERT DAO:
        questionSetDao.insert(questionSet1);
        questionSetDao.insert(questionSet2);

        questionSetDao.delete(questionSet2);

        List<QuestionSet> allQuestionSets = questionSetDao.getAllQuestionSets();

        assertEquals(1, allQuestionSets.size());
        assertEquals("Sample1", allQuestionSets.get(0).getQuestionSetName());
    }

    @Test
    public void add_question_to_questionSet() {
        QuestionSet questionSet = new QuestionSet("Sample");
        questionSet.addQuestion(new Question("What is the capital of Italy?", new FreeTextAnswer("Rome")));
        questionSetDao.insert(questionSet);

        QuestionSet sampleQuestionSet = questionSetDao.getQuestionSet("Sample");
        // update the sample question set with new questions:
        sampleQuestionSet.addQuestion(new Question("This second question should be added?", new FreeTextAnswer("Yes")));

        // UPDATE DAO
        questionSetDao.update(sampleQuestionSet);

        questionSet = questionSetDao.getQuestionSet("Sample");

        assertNotNull(questionSet);
        assertEquals(2, questionSet.getQuestions().size());
        assertEquals("This second question should be added?", questionSet.getQuestions().get(1).getText());
    }
}

