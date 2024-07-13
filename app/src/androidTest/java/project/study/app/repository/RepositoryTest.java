package project.study.app.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.model.entity.QuestionSetEntity;
import project.study.app.repository.interfaces.Repository;

/**
 * Unit tests for the QuestionSetRepository.
 * These tests verify the correctness of the repository methods and ensure that the data operations are performed as expected.
 * Using an in-memory database for testing isolates the tests from the actual database and provides a controlled environment.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class RepositoryTest {
    // Constants for waiting times
    private static final int TIMEOUT_SECONDS = 2;
    private static final int SLEEP_MILLISECONDS = 600;
    // Rule to execute LiveData operations synchronously
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    // Database and repository instances
    private StudyAppDatabase db;
    private Repository<QuestionSetEntity, String> repository;

    @Before
    public void createInMemoryDB() {
        // Set up the in-memory database for testing
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, StudyAppDatabase.class)
                .allowMainThreadQueries()
                .build();

        repository = new RepositoryImplementation(
                db.questionSetDao(),
                Executors.newSingleThreadExecutor()
        );
    }
    @After
    public void closeInMemoryDB() throws IOException {
        db.close(); // Close DB after each test
    }
    @Before
    public void clearDataFromInMemoryDB() throws InterruptedException {
        this.repository.deleteAll();
    }

    /**
     * Helper method to create a QuestionSetEntity with the given name and list of questions.
     * @param name The name of the question set.
     * @return Sample QuestionSetEntity.
     */
    private QuestionSetEntity createSampleQuestionSetEntity(String name) {
        return new QuestionSetEntity(
                name,
                createSampleQuestions()
        );
    }

    /**
     * Helper method to create a list of sample questions.
     * @return The list of sample questions.
     */
    private List<Question> createSampleQuestions() {
        return Arrays.asList(
                new Question("What is the capital of Italy?", new FreeTextAnswer("Rome")),
                new Question("What is the capital of Germany?", new FreeTextAnswer("Berlin"))
        );
    }

    /**
     * Helper method that inserts a QuestionSetEntity into the repository and waits for the insertion to complete.
     * It will be used many times in different test functions...
     * @param questionSet The QuestionSetEntity to be inserted.
     * @throws InterruptedException If the current thread is interrupted while waiting.
     */
    private void insertQuestionSetAndWait(QuestionSetEntity questionSet) throws InterruptedException {
        repository.insert(questionSet);
        Thread.sleep(SLEEP_MILLISECONDS); // Sleep for a specified amount of time
    }

    /**
     * Test the insertion of a QuestionSetEntity into the repository.
     */
    @Test
    public void testGetQuestionSetByName() throws InterruptedException {

        insertQuestionSetAndWait( // Insert a Sample QuestionSetEntity
                createSampleQuestionSetEntity("Sample")
        );

        // Retrieve the inserted question set by its name "Sample" from the repository
        QuestionSetEntity retrieved = getValue(repository.getEntityByName("Sample"));

        // Assertions:
        assertNotNull("QuestionSetEntity retrieved is null", retrieved);
        assertEquals("Sample", retrieved.getName()); // The name should be "Sample"
        assertEquals(2, retrieved.getQuestions().size()); // The questions should be 2
    }

    /**
     * Test the retrieval of all QuestionSetEntities from the repository.
     */
    @Test
    public void getAllQuestionSets() throws InterruptedException {

        insertQuestionSetAndWait( // Insert a Sample QuestionSetEntity1
                createSampleQuestionSetEntity("Sample1")
        );

        insertQuestionSetAndWait( // Insert a Sample QuestionSetEntity2
                createSampleQuestionSetEntity("Sample2")
        );

        // Retrieve all the question sets from the repository
        List<QuestionSetEntity> allQuestionSets = getValue(repository.getAllEntities());

        // Assertions:
        assertNotNull(allQuestionSets);
        assertEquals(2, allQuestionSets.size()); // The question Sets retrieved should be 2
    }

    /**
     * Test the update operation on a QuestionSetEntity in the repository.
     */
    @Test
    public void testUpdateQuestionSet() throws InterruptedException {

        insertQuestionSetAndWait( // Insert a Sample QuestionSetEntity1
                createSampleQuestionSetEntity("Sample")
        );

        // Retrieve the inserted question set by its name "Sample":
        QuestionSetEntity retrieved = getValue(repository.getEntityByName("Sample"));
        assertNotNull(retrieved); // The question set should be exists

        // Update the name and add a question:
        retrieved.setName("UpdatedSample");
        retrieved.addQuestion(new Question("What is the capital of Portugal?", new MultipleChoiceTextAnswer(Arrays.asList("Lisbon", "Rome"), "Lisbon")));
        repository.update(retrieved);
        Thread.sleep(SLEEP_MILLISECONDS);

        // Retrieve the updated question set by its new name "UpdatedSample"
        QuestionSetEntity updated = getValue(repository.getEntityByName("UpdatedSample"));


        // Assertions:
        assertNotNull(updated);
        assertEquals("UpdatedSample", updated.getName()); // The name should be changed
        assertEquals(3, updated.getQuestions().size()); // The questions should be 3 now
    }

    /**
     * Test the deletion of a QuestionSetEntity from the repository.
     */
    @Test
    public void testDeleteQuestionSet() throws InterruptedException {

        QuestionSetEntity sample = createSampleQuestionSetEntity("Sample");
        insertQuestionSetAndWait(sample);

        // Delete the sample Question set:
        this.repository.delete(sample);
        Thread.sleep(SLEEP_MILLISECONDS);

        // Retrieve all question sets from the repository
        List<QuestionSetEntity> allQuestionSets = getValue(repository.getAllEntities());

        // Assertions:
        assertEquals(allQuestionSets.size(), 1); // Then no question sets should be retrieved
    }


    /**
     * Helper method to synchronously retrieve the value from LiveData.
     * @param liveData The LiveData from which the value needs to be retrieved.
     * @param <T> The type of data held by the LiveData.
     * @return The value held by the LiveData.
     */
    private <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        // Array to hold the data retrieved from LiveData
        final Object[] data = new Object[1];
        // CountDownLatch to wait for LiveData to emit a value
        final CountDownLatch latch = new CountDownLatch(1);
        // Observer to observe the LiveData changes
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T o) {
                // When LiveData changes, store the value in the data array
                data[0] = o;
                // Count down the latch to signal that the value has been received
                latch.countDown();
                // Remove the observer after receiving the value
                liveData.removeObserver(this);
            }
        };
        // Start observing the LiveData
        liveData.observeForever(observer);
        // Wait for the latch to count down, which happens when the value is received
        latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        // Return the value retrieved from LiveData
        return (T) data[0];
    }
}