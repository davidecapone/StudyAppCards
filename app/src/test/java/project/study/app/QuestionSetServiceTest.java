package project.study.app;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import project.study.app.model.domain.QuestionSet;
import project.study.app.model.entity.QuestionSetEntity;
import project.study.app.repository.QuestionSetRepository;
import project.study.app.service.interfaces.Callback;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.service.interfaces.SingleItemCallback;

/**
 * A test class for the QuestionSetServiceImplementation.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class QuestionSetServiceTest {

    // Mock the QuestionSetRepository.
    @Mock
    private QuestionSetRepository repository;

    // Inject the QuestionSetServiceImplementation.
    @InjectMocks
    private QuestionSetServiceImplementation service;

    // Create a rule to execute LiveData tasks synchronously.
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Triggers the observer of a LiveData.
     *
     * @param liveData The LiveData to trigger.
     */
    private void triggerObserver(LiveData<?> liveData) {
        liveData.observeForever(o -> {}); // Dummy observer to trigger LiveData
    }

    /**
     * Tests the insert method of the QuestionSetServiceImplementation.
     */
    @Test
    public void testInsertQuestionSetSuccess() {

        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.insert(questionSet, callback);
        triggerObserver(liveData);

        // Assert
        verify(repository).insert(any(QuestionSetEntity.class));
        verify(callback).onSuccess();
    }

    /**
     * Tests the insert method of the QuestionSetServiceImplementation when the QuestionSet already exists.
     */
    @Test
    public void testInsertQuestionSetAlreadyExists() {

        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(new QuestionSetEntity(name, null));

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.insert(questionSet, callback);
        triggerObserver(liveData);

        // Assert
        verify(repository, never()).insert(any(QuestionSetEntity.class));
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    /**
     * Tests the delete method of the QuestionSetServiceImplementation.
     */
    @Test
    public void testDeleteQuestionSetSuccess() {

        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        QuestionSetEntity existingEntity = new QuestionSetEntity(name, null);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(existingEntity);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.delete(questionSet, callback);
        triggerObserver(liveData);

        // Assert
        verify(repository).delete(existingEntity);
        verify(callback).onSuccess();
    }

    /**
     * Tests the delete method of the QuestionSetServiceImplementation when the QuestionSet does not exist.
     */
    @Test
    public void testDeleteQuestionSetDoesNotExist() {

        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.delete(questionSet, callback);
        triggerObserver(liveData);

        // Assert
        verify(repository, never()).delete(any(QuestionSetEntity.class));
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    /**
     * Tests the update method of the QuestionSetServiceImplementation.
     */
    @Test
    public void testUpdateQuestionSetSuccess() {

        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        QuestionSetEntity existingEntity = new QuestionSetEntity(name, null);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(existingEntity);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.update(questionSet, callback);
        triggerObserver(liveData);

        // Assert
        verify(repository).update(existingEntity);
        verify(callback).onSuccess();
    }

    /**
     * Tests the update method of the QuestionSetServiceImplementation when the QuestionSet does not exist.
     */
    @Test
    public void testUpdateQuestionSetDoesNotExist() {

        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.update(questionSet, callback);
        triggerObserver(liveData);

        // Assert
        verify(repository, never()).update(any(QuestionSetEntity.class));
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    /**
     * Tests the getAllQuestionSets method of the QuestionSetServiceImplementation.
     */
    @Test
    public void testGetAllQuestionSets() {

        // Arrange
        MutableLiveData<List<QuestionSetEntity>> liveData = new MutableLiveData<>();
        List<QuestionSetEntity> entities = Arrays.asList(
                new QuestionSetEntity("1", null),
                new QuestionSetEntity("2", null)
        );
        liveData.setValue(entities);

        when(repository.getAllQuestionSets()).thenReturn(liveData);

        // Act
        LiveData<List<QuestionSet>> result = service.getAllQuestionSets();

        // Assert
        result.observeForever(questionSets -> {
            assertEquals(2, questionSets.size());
            assertEquals("1", questionSets.get(0).getQuestionSetName());
            assertEquals("2", questionSets.get(1).getQuestionSetName());
        });
    }

    /**
     * Tests the getQuestionSetByName method of the QuestionSetServiceImplementation.
     */
    @Test
    public void testGetQuestionSetByNameSuccess() {

        // Arrange
        String name = "Test Set";
        QuestionSetEntity entity = new QuestionSetEntity(name, null);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(entity);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        SingleItemCallback<QuestionSet> callback = mock(SingleItemCallback.class);

        // Act
        service.getQuestionSetByName(name, callback);
        triggerObserver(liveData);

        // Assert
        verify(callback).onSuccess(any(QuestionSet.class));
    }

    /**
     * Tests the getQuestionSetByName method of the QuestionSetServiceImplementation when the QuestionSet does not exist.
     */
    @Test
    public void testGetQuestionSetByNameDoesNotExist() {
        
        // Arrange
        String name = "Test Set";
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        SingleItemCallback<QuestionSet> callback = mock(SingleItemCallback.class);

        // Act
        service.getQuestionSetByName(name, callback);
        triggerObserver(liveData);

        // Assert
        verify(callback).onError(any(IllegalArgumentException.class));
    }
}