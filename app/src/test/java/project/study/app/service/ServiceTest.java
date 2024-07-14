package project.study.app.service;

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
import project.study.app.repository.interfaces.Repository;
import project.study.app.service.interfaces.Callback;
import project.study.app.service.interfaces.SingleItemCallback;

/**
 * A test class for the QuestionSetServiceImplementation.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ServiceTest {
    private final String SAMPLE_QUESTION_SET_NAME;
    @Mock
    private Repository<QuestionSetEntity, String> repository;

    // Inject the QuestionSetServiceImplementation.
    @InjectMocks
    private ServiceImplementation service;

    // Create a rule to execute LiveData tasks synchronously.
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public ServiceTest () {
        SAMPLE_QUESTION_SET_NAME = "Test Set";
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void triggerObserver(LiveData<?> liveData) {
        liveData.observeForever(o -> {}); // Dummy observer to trigger LiveData
    }

    /**
     * Tests the insertion of a new QuestionSet.
     */
    @Test
    public void testInsertQuestionSetSuccess() {
        QuestionSet questionSet = new QuestionSet(SAMPLE_QUESTION_SET_NAME);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);

        when(repository.getEntityByName(SAMPLE_QUESTION_SET_NAME)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        service.insert(questionSet, callback);
        triggerObserver(liveData);

        // Assertions:
        verify(repository).insert(any(QuestionSetEntity.class));
        verify(callback).onSuccess();
    }

    /**
     * Tests the insertion when the QuestionSet already exists.
     * The Service should throws an IllegalArgumentException
     */
    @Test
    public void testInsertQuestionSetAlreadyExists() {
        QuestionSet questionSet = new QuestionSet(SAMPLE_QUESTION_SET_NAME);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(
                new QuestionSetEntity(SAMPLE_QUESTION_SET_NAME, null)
        );

        when(repository.getEntityByName(SAMPLE_QUESTION_SET_NAME)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        service.insert(questionSet, callback);
        triggerObserver(liveData);

        // Assertions:
        verify(repository, never()).insert(any(QuestionSetEntity.class));
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    /**
     * Tests the deletion of a QuestionSet.
     */
    @Test
    public void testDeleteQuestionSetSuccess() {
        QuestionSet questionSet = new QuestionSet(SAMPLE_QUESTION_SET_NAME);
        QuestionSetEntity existingEntity = new QuestionSetEntity(SAMPLE_QUESTION_SET_NAME, null);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(existingEntity);

        when(repository.getEntityByName(SAMPLE_QUESTION_SET_NAME)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        service.delete(questionSet, callback);
        triggerObserver(liveData);

        // Assertions:
        verify(repository).delete(existingEntity);
        verify(callback).onSuccess();
    }

    /**
     * Tests the deletion of a QuestionSet when that QuestionSet does not exist.
     * The Service should throws an IllegalArgumentException
     */
    @Test
    public void testDeleteQuestionSetDoesNotExist() {
        QuestionSet questionSet = new QuestionSet(SAMPLE_QUESTION_SET_NAME);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);

        when(repository.getEntityByName(SAMPLE_QUESTION_SET_NAME)).thenReturn(liveData);

        Callback callback = mock(Callback.class);
        service.delete(questionSet, callback);
        triggerObserver(liveData);

        // Assertions:
        verify(repository, never()).delete(any(QuestionSetEntity.class));
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    /**
     * Tests the update of a QuestionSet.
     */
    @Test
    public void testUpdateQuestionSetSuccess() {
        QuestionSet questionSet = new QuestionSet(SAMPLE_QUESTION_SET_NAME);
        QuestionSetEntity existingEntity = new QuestionSetEntity(SAMPLE_QUESTION_SET_NAME, null);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(existingEntity);

        when(repository.getEntityByName(SAMPLE_QUESTION_SET_NAME)).thenReturn(liveData);

        Callback callback = mock(Callback.class);
        service.update(questionSet, callback);
        triggerObserver(liveData);

        // Assertions:
        verify(repository).update(existingEntity);
        verify(callback).onSuccess();
    }

    /**
     * Tests the update of a QuestionSet when that QuestionSet does not exist.
     * The Service should throws an IllegalArgumentException
     */
    @Test
    public void testUpdateQuestionSetDoesNotExist() {
        QuestionSet questionSet = new QuestionSet(SAMPLE_QUESTION_SET_NAME);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);

        when(repository.getEntityByName(SAMPLE_QUESTION_SET_NAME)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        service.update(questionSet, callback);
        triggerObserver(liveData);

        // Assertions:
        verify(repository, never()).update(any(QuestionSetEntity.class));
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    /**
     * Tests the retrieving of all QuestionSet from the repository.
     */
    @Test
    public void testGetAllQuestionSets() {
        MutableLiveData<List<QuestionSetEntity>> liveData = new MutableLiveData<>();
        List<QuestionSetEntity> entities = Arrays.asList(
                new QuestionSetEntity("1", null),
                new QuestionSetEntity("2", null)
        );
        liveData.setValue(entities);

        when(repository.getAllEntities()).thenReturn(liveData);

        LiveData<List<QuestionSet>> result = service.getAllQuestionSets();

        // Assertions:
        result.observeForever(questionSets -> {
            assertEquals(2, questionSets.size());
            assertEquals("1", questionSets.get(0).getQuestionSetName());
            assertEquals("2", questionSets.get(1).getQuestionSetName());
        });
    }

    /**
     * Tests the retrieving of a QuestionSet from the repository by name.
     */
    @Test
    public void testGetQuestionSetByNameSuccess() {
        QuestionSetEntity entity = new QuestionSetEntity(SAMPLE_QUESTION_SET_NAME, null);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(entity);

        when(repository.getEntityByName(SAMPLE_QUESTION_SET_NAME)).thenReturn(liveData);

        SingleItemCallback<QuestionSet> callback = mock(SingleItemCallback.class);
        service.getQuestionSetByName(SAMPLE_QUESTION_SET_NAME, callback);
        triggerObserver(liveData);

        // Assertions:
        verify(callback).onSuccess(any(QuestionSet.class));
    }

    /**
     * Tests the retrieving of a QuestionSet by its name when there are no Question Sets with that name.
     * The Service should throws an IllegalArgumentException
     */
    @Test
    public void testGetQuestionSetByNameDoesNotExist() {
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);

        when(repository.getEntityByName(SAMPLE_QUESTION_SET_NAME)).thenReturn(liveData);

        SingleItemCallback<QuestionSet> callback = mock(SingleItemCallback.class);
        service.getQuestionSetByName(SAMPLE_QUESTION_SET_NAME, callback);
        triggerObserver(liveData);

        // Assertions:
        verify(callback).onError(any(IllegalArgumentException.class));
    }
}