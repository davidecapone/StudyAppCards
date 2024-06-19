
package project.study.app.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.model.entity.QuestionSetEntity;

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

    private QuestionSetEntity createQuestionSetEntity(String name, List<Question> questions) {
        return new QuestionSetEntity(name, questions);
    }

    private List<Question> createSampleQuestions() {
        return Arrays.asList(
                new Question("What is the capital of Italy?", new FreeTextAnswer("Rome")),
                new Question("What is the capital of Germany?", new FreeTextAnswer("Berlin"))
        );
    }
    @Test
    public void testInsertQuestionSet() throws InterruptedException {

        QuestionSetEntity questionSetEntity = createQuestionSetEntity("Sample", createSampleQuestions());
        questionSetDao.insert(questionSetEntity);

        List<QuestionSetEntity> allQuestionSets = getValue(questionSetDao.getAllQuestionSets());
        QuestionSetEntity retrieved = allQuestionSets.get(0);

        assertNotNull(retrieved);
        assertEquals("Sample", retrieved.getName());
        assertEquals(2, retrieved.getQuestions().size());
    }

    @Test
    public void testRetrieveAllQuestionSets() throws InterruptedException {
        QuestionSetEntity newQuestionSet1 = createQuestionSetEntity("SAMPLE1", createSampleQuestions());
        QuestionSetEntity newQuestionSet2 = createQuestionSetEntity("SAMPLE2", createSampleQuestions());
        QuestionSetEntity newQuestionSet3 = createQuestionSetEntity("SAMPLE3", createSampleQuestions());

        questionSetDao.insert(newQuestionSet1);
        questionSetDao.insert(newQuestionSet2);
        questionSetDao.insert(newQuestionSet3);


        List<QuestionSetEntity> allQuestionSets = getValue(questionSetDao.getAllQuestionSets());

        assertNotNull(allQuestionSets);
        assertEquals(
                3,
                allQuestionSets.size()
        );
        assertEquals(
                "SAMPLE1",
                allQuestionSets.get(0).getName()
        );
        assertEquals(
                "SAMPLE2",
                allQuestionSets.get(1).getName()
        );
        assertEquals(
                "SAMPLE3",
                allQuestionSets.get(2).getName()
        );
    }

    @Test
    public void testDeleteQuestionSet() throws InterruptedException {
        QuestionSetEntity questionSet1 = createQuestionSetEntity("SAMPLE1", createSampleQuestions());
        QuestionSetEntity questionSet2 = createQuestionSetEntity("SAMPLE2", createSampleQuestions());

        questionSetDao.insert(questionSet1);
        questionSetDao.insert(questionSet2);

        List<QuestionSetEntity> allQuestionSets = getValue(questionSetDao.getAllQuestionSets());
        assertEquals(2, allQuestionSets.size());

        QuestionSetEntity retrievedSet1 = allQuestionSets.get(0);
        QuestionSetEntity retrievedSet2 = allQuestionSets.get(1);


        questionSetDao.delete(retrievedSet2);

        allQuestionSets = getValue(questionSetDao.getAllQuestionSets());

        assertEquals(1, allQuestionSets.size());
        assertEquals("SAMPLE1", allQuestionSets.get(0).getName());
    }

    @Test
    public void testUpdateQuestionSet() throws InterruptedException {
        QuestionSetEntity questionSet = createQuestionSetEntity("SAMPLE", createSampleQuestions());

        questionSetDao.insert(questionSet);

        QuestionSetEntity retrieved = questionSetDao.getQuestionSetByName("SAMPLE");

        assertNotNull(retrieved);

        // update the question set (add a new question):
        retrieved.addQuestion(
                new Question("What is the capital of Portugal?", new FreeTextAnswer("Lisbon"))
        );

        // change the name:
        retrieved.setName("NEW_NAME");

        questionSetDao.update(retrieved);

        retrieved = questionSetDao.getQuestionSetByName("NEW_NAME");

        assertNotNull(retrieved);
        assertEquals(
                3,
                retrieved.getQuestions().size()
        );
    }

    private <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        return (T) data[0];
    }
}

