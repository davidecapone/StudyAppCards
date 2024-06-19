package project.study.app.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import project.study.app.model.domain.QuestionSet;
import project.study.app.repository.QuestionSetRepositoryImplementation;
import project.study.app.repository.QuestionSetRepository;
import project.study.app.model.entity.QuestionSetEntity;

public class QuestionSetServiceTest {

    private QuestionSetService service;
    private QuestionSetRepository repository;

    @Before
    public void set_up() {
        repository = mock(QuestionSetRepositoryImplementation.class);
        service = new QuestionSetService(repository);
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

        QuestionSet questionSet = new QuestionSet("Sample");

        QuestionSetEntity existingEntity = new QuestionSetEntity("Sample", new ArrayList<>());

        when(repository.getQuestionSetByName("Sample")).thenReturn(existingEntity);

        service.insert(questionSet); // This should throw an IllegalArgumentException
    }
}
