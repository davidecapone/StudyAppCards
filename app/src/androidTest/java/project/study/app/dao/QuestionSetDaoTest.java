
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
import java.util.Arrays;
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
    private QuestionSet createQuestionSet(String name, List<Question> questions) {
        QuestionSet sample = new QuestionSet(name);
        sample.setQuestions(questions);
        return sample;
    }

    private List<Question> createSampleQuestions() {
        return Arrays.asList(
                new Question("What is the capital of Italy?", new FreeTextAnswer("Rome")),
                new Question("What is the capital of Germany?", new FreeTextAnswer("Berlin"))
        );
    }
    @Test
    public void testInsertQuestionSet() {
        String questionSetName = "sample question set";
        QuestionSet newQuestionSet = createQuestionSet(questionSetName, createSampleQuestions());

        questionSetDao.insert(newQuestionSet);

        QuestionSet retrievedQuestionSet = questionSetDao.getQuestionSet(questionSetName);

        assertNotNull(retrievedQuestionSet);
        assertEquals(
                newQuestionSet.getQuestionSetName(),
                retrievedQuestionSet.getQuestionSetName()
        );

        assertEquals(
                newQuestionSet.getQuestions().size(),
                retrievedQuestionSet.getQuestions().size()
        );
    }

    @Test
    public void testRetrieveAllQuestionSets() {
        QuestionSet newQuestionSet1 = createQuestionSet("SAMPLE1", createSampleQuestions());
        QuestionSet newQuestionSet2 = createQuestionSet("SAMPLE2", createSampleQuestions());
        QuestionSet newQuestionSet3 = createQuestionSet("SAMPLE3", createSampleQuestions());

        questionSetDao.insert(newQuestionSet1);
        questionSetDao.insert(newQuestionSet2);
        questionSetDao.insert(newQuestionSet3);

        List<QuestionSet> allQuestionSets = questionSetDao.getAllQuestionSets();

        assertNotNull(allQuestionSets);
        assertEquals(
                3,
                allQuestionSets.size()
        );
        assertEquals(
                "SAMPLE1",
                allQuestionSets.get(0).getQuestionSetName()
        );
        assertEquals(
                "SAMPLE2",
                allQuestionSets.get(1).getQuestionSetName()
        );
        assertEquals(
                "SAMPLE3",
                allQuestionSets.get(2).getQuestionSetName()
        );
    }

    @Test
    public void testDeleteQuestionSet() {
        QuestionSet questionSet1 = createQuestionSet("SAMPLE1", createSampleQuestions());
        QuestionSet questionSet2 = createQuestionSet("SAMPLE2", createSampleQuestions());

        questionSetDao.insert(questionSet1);
        questionSetDao.insert(questionSet2);

        questionSetDao.delete(questionSet2);

        List<QuestionSet> allQuestionSets = questionSetDao.getAllQuestionSets();

        assertEquals(
                1,
                allQuestionSets.size()
        );
        assertEquals(
                "SAMPLE1",
                allQuestionSets.get(0).getQuestionSetName());
    }
}

