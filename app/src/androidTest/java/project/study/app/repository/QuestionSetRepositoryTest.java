package project.study.app.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.domain.QuestionSet;
import project.study.app.model.repository.QuestionSetRepository;

@RunWith(AndroidJUnit4.class)
public class QuestionSetRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private StudyAppDatabase db;
    private QuestionSetDao questionSetDao;
    private QuestionSetRepository questionSetRepository;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, StudyAppDatabase.class)
                .allowMainThreadQueries()
                .build();

        questionSetDao = db.questionSetDao();
        questionSetRepository = new QuestionSetRepository(db);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insert_and_retrieve_questionSet() throws InterruptedException {
        // create a new question set
        QuestionSet questionSet = new QuestionSet("Test Set");
        // insert it
        questionSetRepository.insertQuestionSet(questionSet);

        Thread.sleep(1000); // Ensure the insert operation completes
        // retrieve the question set
        QuestionSet retrievedSet = questionSetRepository.retrieveQuestionSet("Test Set");
        assertNotNull(retrievedSet);
        assertEquals("Test Set", retrievedSet.getQuestionSetName());
    }

    @Test
    public void getAllQuestionSets() throws InterruptedException {
        // create sample question sets:
        QuestionSet questionSet1 = new QuestionSet("Test Set 1");
        QuestionSet questionSet2 = new QuestionSet("Test Set 2");

        // insert those question sets:
        questionSetRepository.insertQuestionSet(questionSet1);
        questionSetRepository.insertQuestionSet(questionSet2);

        Thread.sleep(1000); // Ensure the insert operations complete
        List<QuestionSet> questionSets = questionSetRepository.getAllQuestionSets();
        assertEquals(2, questionSets.size());
    }

    @Test
    public void deleteQuestionSet() throws InterruptedException {
        QuestionSet questionSet = new QuestionSet("Test Set");
        questionSetRepository.insertQuestionSet(questionSet);

        Thread.sleep(1000); // Ensure the insert operation completes
        QuestionSet retrievedSet = questionSetRepository.retrieveQuestionSet("Test Set");
        assertNotNull(retrievedSet);

        questionSetRepository.deleteQuestionSet(retrievedSet);

        Thread.sleep(1000); // Ensure the delete operation completes
        QuestionSet deletedSet = questionSetRepository.retrieveQuestionSet("Test Set");
        assertNull(deletedSet);
    }

}
