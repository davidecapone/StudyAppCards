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
    public void insertQuestionSet() {
        QuestionSet questionSet = new QuestionSet("Sample");
        service.insert(questionSet);
        verify(repository).insert(any(QuestionSetEntity.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertDuplicateName() {
        // Create a new QuestionSet object with the name "Sample"
        QuestionSet questionSet = new QuestionSet("Sample");

        // Create an existing QuestionSetEntity object with the same name "Sample"
        QuestionSetEntity existingEntity = new QuestionSetEntity("Sample", new ArrayList<>());

        // Mock the repository's getQuestionSetByName method to return the existing entity wrapped in LiveData
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        liveData.setValue(existingEntity);
        when(repository.getQuestionSetByName("Sample")).thenReturn(liveData);

        // Call the insert method on the service with the new QuestionSet
        service.insert(questionSet); // This should throw an IllegalArgumentException
    }

    @Test
    public void deleteQuestionSet() {
        // Create a new QuestionSet object with the name "Sample"
        QuestionSet questionSet = new QuestionSet("Sample");

        // Create an existing QuestionSetEntity object with the same name "Sample"
        QuestionSetEntity existingEntity = new QuestionSetEntity("Sample", new ArrayList<>());

        // Mock the repository's getQuestionSetByName method to return the existing entity wrapped in LiveData
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        liveData.setValue(existingEntity);
        when(repository.getQuestionSetByName("Sample")).thenReturn(liveData);

        // Call the delete method on the service with the new QuestionSet
        service.delete(questionSet);
        verify(repository).delete(any(QuestionSetEntity.class));
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
