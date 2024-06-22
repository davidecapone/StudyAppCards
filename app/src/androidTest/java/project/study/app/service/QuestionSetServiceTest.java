package project.study.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import project.study.app.model.domain.Question;
import project.study.app.model.domain.QuestionSet;
import project.study.app.model.entity.QuestionSetEntity;
import project.study.app.repository.QuestionSetRepository;

@RunWith(MockitoJUnitRunner.class)
public class QuestionSetServiceTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private QuestionSetRepository repository;

    @InjectMocks
    private QuestionSetServiceImplementation service;

    private QuestionSet questionSet;

    @Before
    public void setUp() {
        questionSet = new QuestionSet("Sample");
        questionSet.setQuestions(new ArrayList<>());
    }

    @Test
    public void testInsert_Success() {
        // Arrange
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);
        when(repository.getQuestionSetByName(anyString())).thenReturn(liveData);
        doAnswer(invocation -> {
            liveData.postValue(null);
            return null;
        }).when(repository).insert(any(QuestionSetEntity.class));
        Callback callback = mock(Callback.class);

        // Act
        service.insert(questionSet, callback);

        // Assert
        verify(repository).insert(any(QuestionSetEntity.class));
        verify(callback).onSuccess();
    }

    @Test
    public void testInsert_DuplicateName() {
        // Arrange
        QuestionSetEntity existingEntity = new QuestionSetEntity("Sample", new ArrayList<>());
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(existingEntity);
        when(repository.getQuestionSetByName(anyString())).thenReturn(liveData);
        Callback callback = mock(Callback.class);

        // Act
        service.insert(questionSet, callback);

        // Assert
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    @Test
    public void testUpdate_Success() {
        // Arrange
        QuestionSetEntity existingEntity = new QuestionSetEntity("Sample", new ArrayList<>());
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(existingEntity);
        when(repository.getQuestionSetByName(anyString())).thenReturn(liveData);
        Callback callback = mock(Callback.class);

        // Act
        service.update(questionSet, callback);

        // Assert
        verify(repository).update(any(QuestionSetEntity.class));
        verify(callback).onSuccess();
    }

    @Test
    public void testUpdate_NotExist() {
        // Arrange
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);
        when(repository.getQuestionSetByName(anyString())).thenReturn(liveData);
        Callback callback = mock(Callback.class);

        // Act
        service.update(questionSet, callback);

        // Assert
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    @Test
    public void testDelete_Success() {
        // Arrange
        QuestionSetEntity existingEntity = new QuestionSetEntity("Sample", new ArrayList<>());
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(existingEntity);
        when(repository.getQuestionSetByName(anyString())).thenReturn(liveData);
        Callback callback = mock(Callback.class);

        // Act
        service.delete(questionSet, callback);

        // Assert
        verify(repository).delete(any(QuestionSetEntity.class));
        verify(callback).onSuccess();
    }

    @Test
    public void testDelete_NotExist() {
        // Arrange
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);
        when(repository.getQuestionSetByName(anyString())).thenReturn(liveData);
        Callback callback = mock(Callback.class);

        // Act
        service.delete(questionSet, callback);

        // Assert
        verify(callback).onError(any(IllegalArgumentException.class));
    }

    @Test
    public void testGetAllQuestionSets() {
        // Arrange
        List<QuestionSetEntity> entities = new ArrayList<>();
        entities.add(new QuestionSetEntity("Sample1", new ArrayList<>()));
        entities.add(new QuestionSetEntity("Sample2", new ArrayList<>()));
        MutableLiveData<List<QuestionSetEntity>> liveData = new MutableLiveData<>(entities);
        when(repository.getAllQuestionSets()).thenReturn(liveData);

        // Act
        LiveData<List<QuestionSet>> result = service.getAllQuestionSets();

        // Assert
        assertNotNull(result);
        result.observeForever(questionSets -> {
            assertEquals(2, questionSets.size());
            assertEquals("Sample1", questionSets.get(0).getQuestionSetName());
            assertEquals("Sample2", questionSets.get(1).getQuestionSetName());
        });
    }

    @Test
    public void testGetQuestionSetByName_Success() {
        // Arrange
        QuestionSetEntity existingEntity = new QuestionSetEntity("Sample", new ArrayList<>());
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(existingEntity);
        when(repository.getQuestionSetByName(anyString())).thenReturn(liveData);
        SingleItemCallback<QuestionSet> callback = mock(SingleItemCallback.class);

        // Act
        service.getQuestionSetByName("Sample", callback);

        // Assert
        verify(callback).onSuccess(any(QuestionSet.class));
    }

    @Test
    public void testGetQuestionSetByName_NotExist() {
        // Arrange
        MutableLiveData<QuestionSetEntity> liveData = new MutableLiveData<>(null);
        when(repository.getQuestionSetByName(anyString())).thenReturn(liveData);
        SingleItemCallback<QuestionSet> callback = mock(SingleItemCallback.class);

        // Act
        service.getQuestionSetByName("Sample", callback);

        // Assert
        verify(callback).onError(any(IllegalArgumentException.class));
    }
}