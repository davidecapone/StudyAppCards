package project.study.app;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
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
import project.study.app.service.Callback;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.service.SingleItemCallback;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class QuestionSetServiceTest {

    @Mock
    private QuestionSetRepository repository;

    @InjectMocks
    private QuestionSetServiceImplementation service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertQuestionSetSuccess() {
        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        liveData.setValue(null);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.insert(questionSet, callback);
        liveData.setValue(null);  // Trigger observer

        // Assert
        verify(repository).insert(any(QuestionSetEntity.class));
        verify(callback).onSuccess();
    }

    @Test
    public void testInsertQuestionSetAlreadyExists() {
        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        QuestionSetEntity existingEntity = new QuestionSetEntity(name, null);
        liveData.setValue(existingEntity);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.insert(questionSet, callback);
        liveData.setValue(existingEntity);  // Trigger observer

        // Assert
        verify(repository, never()).insert(any(QuestionSetEntity.class));
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    @Test
    public void testDeleteQuestionSetSuccess() {
        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        QuestionSetEntity existingEntity = new QuestionSetEntity(name, null);
        liveData.setValue(existingEntity);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.delete(questionSet, callback);
        liveData.setValue(existingEntity);  // Trigger observer

        // Assert
        verify(repository).delete(existingEntity);
        verify(callback).onSuccess();
    }

    @Test
    public void testDeleteQuestionSetDoesNotExist() {
        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        liveData.setValue(null);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.delete(questionSet, callback);
        liveData.setValue(null);  // Trigger observer

        // Assert
        verify(repository, never()).delete(any(QuestionSetEntity.class));
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    @Test
    public void testUpdateQuestionSetSuccess() {
        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        QuestionSetEntity existingEntity = new QuestionSetEntity(name, null);
        liveData.setValue(existingEntity);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.update(questionSet, callback);
        liveData.setValue(existingEntity);  // Trigger observer

        // Assert
        verify(repository).update(existingEntity);
        verify(callback).onSuccess();
    }

    @Test
    public void testUpdateQuestionSetDoesNotExist() {
        // Arrange
        String name = "Test Set";
        QuestionSet questionSet = new QuestionSet(name);
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        liveData.setValue(null);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        Callback callback = mock(Callback.class);

        // Act
        service.update(questionSet, callback);
        liveData.setValue(null);  // Trigger observer

        // Assert
        verify(repository, never()).update(any(QuestionSetEntity.class));
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    @Test
    public void testGetAllQuestionSets() {
        // Arrange
        MutableLiveData<List<QuestionSetEntity>> liveData = new MutableLiveData<>();
        List<QuestionSetEntity> entities = Arrays.asList(new QuestionSetEntity("1", null), new QuestionSetEntity("2", null));
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

    @Test
    public void testGetQuestionSetByNameSuccess() {
        // Arrange
        String name = "Test Set";
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        QuestionSetEntity entity = new QuestionSetEntity(name, null);
        liveData.setValue(entity);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        SingleItemCallback<QuestionSet> callback = mock(SingleItemCallback.class);

        // Act
        service.getQuestionSetByName(name, callback);
        liveData.setValue(entity);  // Trigger observer

        // Assert
        verify(callback).onSuccess(any(QuestionSet.class));
    }

    @Test
    public void testGetQuestionSetByNameDoesNotExist() {
        // Arrange
        String name = "Test Set";
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>();
        liveData.setValue(null);

        when(repository.getQuestionSetByName(name)).thenReturn(liveData);

        SingleItemCallback<QuestionSet> callback = mock(SingleItemCallback.class);

        // Act
        service.getQuestionSetByName(name, callback);
        liveData.setValue(null);  // Trigger observer

        // Assert
        verify(callback).onError(any(IllegalArgumentException.class));
    }
}