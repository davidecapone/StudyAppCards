package project.study.app.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.repository.QuestionSetRepositoryImplementation;
import project.study.app.repository.QuestionSetRepository;
import project.study.app.model.entity.QuestionSetEntity;
@RunWith(AndroidJUnit4.class)
public class QuestionSetServiceTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private QuestionSetServiceImplementation service;
    private QuestionSetRepository repository;

    @Before
    public void set_up() {
        repository = mock(QuestionSetRepositoryImplementation.class);
        service = new QuestionSetServiceImplementation(repository);
    }

    @Test
    public void testInsert() {

        // Step 1: Create a new QuestionSet object with the name "Sample"
        QuestionSet questionSet = new QuestionSet("Sample");

        // Step 2: Mock the repository's getQuestionSetByName method to return null for "Sample"
        // This simulates the behavior that there is no existing QuestionSet with the name "Sample"
        when(repository.getQuestionSetByName("Sample")).thenReturn(null);

        // Step 3: Call the insert method on the service with the newly created QuestionSet
        service.insert(questionSet);

        // Step 4: Verify that the repository's insert method is called with any QuestionSetEntity object
        // This ensures that the service's insert method correctly calls the repository's insert method
        verify(repository).insert(any(QuestionSetEntity.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertDuplicateName() {

        // Step 1: Create a new QuestionSet object with the name "Sample"
        // This is the domain object that we want to insert into the service
        QuestionSet questionSet = new QuestionSet("Sample");

        // Step 2: Create an existing QuestionSetEntity object with the same name "Sample"
        // This simulates an existing entry in the repository
        QuestionSetEntity existingEntity = new QuestionSetEntity("Sample", new ArrayList<>());

        // Step 3: Mock the repository's getQuestionSetByName method to return the existing entity
        // This means that when the service calls this method, it will return the existing entity
        when(repository.getQuestionSetByName("Sample")).thenReturn(existingEntity);

        // Step 4: Call the insert method on the service with the new QuestionSet
        // Since the repository already contains an entity with the name "Sample",
        // the service's insert method should throw an IllegalArgumentException
        service.insert(questionSet); // This should throw an IllegalArgumentException
    }

    @Test
    public void testChangeQuestionSetName() throws InterruptedException {
        // Step 1: Create an existing QuestionSetEntity object with the name "Sample"
        QuestionSetEntity existingEntity = new QuestionSetEntity("Sample", new ArrayList<>());

        // Step 2: Mock the repository's getQuestionSetByName method to return the existing entity
        when(repository.getQuestionSetByName("Sample")).thenReturn(existingEntity);

        // Step 3: Call the changeQuestionSetName method on the service to change the name to "NewSample"
        service.changeQuestionSetName("Sample", "NewSample");

        // Step 4: Verify that the repository's update method was called with the updated entity
        verify(repository).update(any(QuestionSetEntity.class));

        // Step 5: Assert that the name was changed in the entity
        assertEquals("NewSample", existingEntity.getName());
    }

    @Test
    public void testDelete() {
        // Step 1: Create a new QuestionSet object with the name "Sample"
        // This is the domain object that we want to delete through the service
        QuestionSet questionSet = new QuestionSet("Sample");

        // Step 2: Create an existing QuestionSetEntity object with the same name "Sample"
        // This simulates an existing entry in the repository
        QuestionSetEntity entity = new QuestionSetEntity("Sample", new ArrayList<>());

        // Step 3: Mock the repository's getQuestionSetByName method to return the existing entity
        // This means that when the service calls this method, it will return the existing entity
        when(repository.getQuestionSetByName("Sample")).thenReturn(entity);

        // Step 4: Call the delete method on the service with the new QuestionSet
        // This should trigger the repository's delete method with the corresponding entity
        service.delete(questionSet);

        // Step 5: Verify that the repository's delete method was called with any QuestionSetEntity object
        // This ensures that the service's delete method correctly calls the repository's delete method
        verify(repository).delete(any(QuestionSetEntity.class));
    }

    @Test
    public void testAddQuestionToQuestionSet() {
        // Step 1: Create an existing QuestionSetEntity object with the name "Sample"
        QuestionSetEntity entity = new QuestionSetEntity("Sample", new ArrayList<>());

        // Step 2: Mock the repository's getQuestionSetByName method to return the existing entity
        when(repository.getQuestionSetByName("Sample")).thenReturn(entity);

        // Step 3: Create a new Question object to be added
        Question newQuestion = new Question("What is the capital of Spain?", new FreeTextAnswer("Madrid"));

        // Step 4: Call the addQuestionToQuestionSet method on the service
        service.addQuestionToQuestionSet("Sample", newQuestion);

        // Step 5: Verify that the repository's update method was called with the updated entity
        verify(repository).update(any(QuestionSetEntity.class));

        // Step 6: Assert that the question was added to the entity's question list
        assertEquals(1, entity.getQuestions().size());
        assertEquals("What is the capital of Spain?", entity.getQuestions().get(0).getText());
    }

    @Test
    public void testRemoveQuestionFromQuestionSet() {
        // Step 1: Create a list with an existing question
        List<Question> questions = new ArrayList<>();
        Question existingQuestion = new Question("What is the capital of Spain?", new FreeTextAnswer("Madrid"));
        questions.add(existingQuestion);

        // Step 2: Create an existing QuestionSetEntity object with the name "Sample" and the list of questions
        QuestionSetEntity entity = new QuestionSetEntity("Sample", questions);

        // Step 3: Mock the repository's getQuestionSetByName method to return the existing entity
        when(repository.getQuestionSetByName("Sample")).thenReturn(entity);

        // Step 4: Call the removeQuestionFromQuestionSet method on the service
        service.removeQuestionFromQuestionSet("Sample", existingQuestion);

        // Step 5: Verify that the repository's update method was called with the updated entity
        verify(repository).update(any(QuestionSetEntity.class));

        // Step 6: Assert that the question was removed from the entity's question list
        assertEquals(0, entity.getQuestions().size());
    }

    @Test
    public void testGetAllQuestionSets() throws InterruptedException {
        // Step 1: Create a list of QuestionSetEntity objects
        List<QuestionSetEntity> entities = new ArrayList<>();
        entities.add(new QuestionSetEntity("Sample1", new ArrayList<>()));
        entities.add(new QuestionSetEntity("Sample2", new ArrayList<>()));

        // Step 2: Mock the repository's getAllQuestionSets method to return the list of entities wrapped in LiveData
        MutableLiveData<List<QuestionSetEntity>> liveData = new MutableLiveData<>();
        liveData.postValue(entities);
        when(repository.getAllQuestionSets()).thenReturn(liveData);

        // Step 3: Call the getAllQuestionSets method on the service
        LiveData<List<QuestionSet>> resultLiveData = service.getAllQuestionSets();
        List<QuestionSet> result = getOrAwaitValue(resultLiveData);

        // Step 4: Assert that the result is not null and has the correct size
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Sample1", result.get(0).getQuestionSetName());
        assertEquals("Sample2", result.get(1).getQuestionSetName());
    }

    // Helper method to get the value from LiveData
    private <T> T getOrAwaitValue(LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        liveData.observeForever(new androidx.lifecycle.Observer<T>() {
            @Override
            public void onChanged(T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        });
        latch.await(2, TimeUnit.SECONDS);
        return (T) data[0];
    }
}
