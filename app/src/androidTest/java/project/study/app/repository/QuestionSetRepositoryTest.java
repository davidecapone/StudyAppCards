package project.study.app.repository;

import static org.junit.Assert.assertEquals;
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
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.model.entity.QuestionSetEntity;

/**
 * Unit tests for the QuestionSetRepository.
 * These tests verify the correctness of the repository methods and ensure that the data operations are performed as expected.
 * Using an in-memory database for testing isolates the tests from the actual database and provides a controlled environment.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class QuestionSetRepositoryTest {

    // Constants for waiting times
    private static final int TIMEOUT_SECONDS = 2;
    private static final int SLEEP_MILLISECONDS = 500;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private StudyAppDatabase db;
    private QuestionSetRepository repository;

    @Before
    public void createDb() {
        // Set up the in-memory database for testing
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, StudyAppDatabase.class)
                .allowMainThreadQueries()
                .build();

        // Create the repository using the in-memory database and an executor service
        repository = new QuestionSetRepositoryImplementation(db.questionSetDao(), Executors.newSingleThreadExecutor());
    }

    @Before
    public void clear() throws InterruptedException {
        // Clear the repository before each test
        this.repository.deleteAll();
    }

    @After
    public void closeDb() throws IOException {
        // Close the database after each test
        db.close();
    }

    // Helper method to create a QuestionSetEntity
    private QuestionSetEntity createQuestionSetEntity(String name, List<Question> questions) {
        return new QuestionSetEntity(name, questions);
    }

    // Helper method to create a sample list of questions
    private List<Question> createSampleQuestions() {
        return Arrays.asList(
                new Question("What is the capital of Italy?", new FreeTextAnswer("Rome")),
                new Question("What is the capital of Germany?", new FreeTextAnswer("Berlin"))
        );
    }

    @Test
    public void testGetQuestionSetByName() throws InterruptedException {
        // Insert a question set and then retrieve it by name

        // Create a QuestionSetEntity with the name "Sample" and sample questions
        QuestionSetEntity questionSet = createQuestionSetEntity("Sample", createSampleQuestions());

        // Insert the created question set into the repository and wait for the insertion to complete
        insertQuestionSetAndWait(questionSet);

        // Retrieve the inserted question set by its name "Sample" from the repository
        QuestionSetEntity retrieved = getValue(repository.getQuestionSetByName("Sample"));

        // Ensure that the retrieved question set is not null
        assertNotNull("QuestionSetEntity retrieved is null", retrieved);

        // Verify that the retrieved question set has the expected name
        assertEquals("Sample", retrieved.getName());

        // Verify that the retrieved question set has the expected number of questions
        assertEquals(2, retrieved.getQuestions().size());
    }

    @Test
    public void getAllQuestionSets() throws InterruptedException {
        // Insert multiple question sets and retrieve all

        // Insert sample question sets into the repository
        insertSampleQuestionSets();

        // Retrieve all question sets from the repository
        List<QuestionSetEntity> allQuestionSets = getValue(repository.getAllQuestionSets());

        // Ensure that the retrieved list of question sets is not null
        assertNotNull(allQuestionSets);

        // Verify that the size of the retrieved list is 2, indicating that both question sets were retrieved
        assertEquals(2, allQuestionSets.size());
    }

    @Test
    public void updateQuestionSet() throws InterruptedException {
        // Insert a question set, update it, and verify the changes

        // Create a new QuestionSetEntity with the name "Sample" and sample questions
        QuestionSetEntity questionSet = new QuestionSetEntity("Sample", createSampleQuestions());
        // Insert the question set and wait for the operation to complete
        insertQuestionSetAndWait(questionSet);

        // Retrieve the inserted question set by its name "Sample"
        QuestionSetEntity retrieved = getValue(repository.getQuestionSetByName("Sample"));
        // Ensure that the retrieved question set is not null
        assertNotNull(retrieved);

        // Update the name of the question set and add a new question
        retrieved.setName("UpdatedSample");
        retrieved.addQuestion(new Question("What is the capital of Portugal?", new MultipleChoiceTextAnswer(Arrays.asList("Lisbon", "Rome"), "Lisbon")));
        // Update the question set in the repository
        repository.update(retrieved);

        // Wait for the update operation to complete
        Thread.sleep(SLEEP_MILLISECONDS);

        // Retrieve the updated question set by its new name "UpdatedSample"
        QuestionSetEntity updated = getValue(repository.getQuestionSetByName("UpdatedSample"));
        // Ensure that the updated question set is not null
        assertNotNull(updated);
        // Verify that the name of the updated question set is "UpdatedSample"
        assertEquals("UpdatedSample", updated.getName());
        // Verify that the updated question set contains 3 questions
        assertEquals(3, updated.getQuestions().size());
    }

    @Test
    public void deleteQuestionSet() throws InterruptedException {
        // Insert multiple question sets and then delete one
        insertSampleQuestionSets();

        // Retrieve all question sets from the repository
        List<QuestionSetEntity> allQuestionSets = getValue(repository.getAllQuestionSets());
        // Assert that there are 2 question sets in the repository
        assertEquals(2, allQuestionSets.size());

        // Get the second question set entity from the list
        QuestionSetEntity retrievedSet2 = allQuestionSets.get(1);
        // Delete the second question set entity from the repository
        repository.delete(retrievedSet2);

        // Wait for the deletion to complete
        Thread.sleep(SLEEP_MILLISECONDS);

        // Retrieve all question sets from the repository again
        allQuestionSets = getValue(repository.getAllQuestionSets());
        // Assert that there is only 1 question set left in the repository
        assertEquals(1, allQuestionSets.size());
        // Assert that the remaining question set is the first one inserted with the name "Sample1"
        assertEquals("Sample1", allQuestionSets.get(0).getName());
    }

    /**
     * Inserts sample QuestionSetEntities into the repository and waits for each insertion to complete.
     *
     * @throws InterruptedException If the current thread is interrupted while waiting.
     */
    private void insertSampleQuestionSets() throws InterruptedException {
        // Create a sample QuestionSetEntity with the name "Sample1" and a list of sample questions
        QuestionSetEntity questionSet1 = createQuestionSetEntity("Sample1", createSampleQuestions());

        // Create another sample QuestionSetEntity with the name "Sample2" and a list of sample questions
        QuestionSetEntity questionSet2 = createQuestionSetEntity("Sample2", createSampleQuestions());

        // Insert the first sample QuestionSetEntity and wait for the insertion to complete
        insertQuestionSetAndWait(questionSet1);

        // Insert the second sample QuestionSetEntity and wait for the insertion to complete
        insertQuestionSetAndWait(questionSet2);
    }

    /**
     * Inserts a QuestionSetEntity into the repository and waits for the insertion to complete.
     *
     * @param questionSet The QuestionSetEntity to be inserted.
     * @throws InterruptedException If the current thread is interrupted while waiting.
     */
    private void insertQuestionSetAndWait(QuestionSetEntity questionSet) throws InterruptedException {
        // Insert the QuestionSetEntity into the repository
        repository.insert(questionSet);

        // Sleep for a specified amount of time to ensure the insertion is completed
        Thread.sleep(SLEEP_MILLISECONDS);
    }

    /**
     * Helper method to synchronously retrieve the value from LiveData.
     *
     * @param liveData The LiveData from which the value needs to be retrieved.
     * @param <T> The type of data held by the LiveData.
     * @return The value held by the LiveData.
     * @throws InterruptedException If the current thread is interrupted while waiting.
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