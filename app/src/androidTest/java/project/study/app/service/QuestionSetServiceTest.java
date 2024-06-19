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
}
