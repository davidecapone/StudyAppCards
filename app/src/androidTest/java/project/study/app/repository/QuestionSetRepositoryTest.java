package project.study.app.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
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

import project.study.app.model.QuestionSetRepository;
import project.study.app.model.database.StudyAppDatabase;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.model.entity.QuestionSetEntity;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class QuestionSetRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private StudyAppDatabase db;
    private QuestionSetRepository repository;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, StudyAppDatabase.class)
                .allowMainThreadQueries()
                .build();
        repository = new QuestionSetRepository(context);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    private QuestionSetEntity createQuestionSetEntity(String name, List<Question> questions) {
        return new QuestionSetEntity(name, questions);
    }

    private List<Question> createSampleQuestions() {
        return Arrays.asList(
                new Question("What is the capital of Italy?", new FreeTextAnswer("Rome")),
                new Question("What is the capital of Germany?", new FreeTextAnswer("Berlin"))
        );
    }

    @Test
    public void insertAndGetQuestionSetByName() throws InterruptedException {

        QuestionSetEntity questionSet = new QuestionSetEntity("Sample", createSampleQuestions());
        repository.insert(questionSet);

        QuestionSetEntity retrieved = repository.getQuestionSetByName("Sample");

        assertNotNull(retrieved);
        assertEquals("Sample", retrieved.getName());
        assertEquals(2, retrieved.getQuestions().size());
    }

    @Test
    public void getAllQuestionSets() throws InterruptedException {

        QuestionSetEntity questionSet1 = new QuestionSetEntity("Sample1", createSampleQuestions());
        QuestionSetEntity questionSet2 = new QuestionSetEntity("Sample2", createSampleQuestions());

        repository.insert(questionSet1);
        repository.insert(questionSet2);

        List<QuestionSetEntity> allQuestionSets = repository.getAllQuestionSets();
        assertNotNull(allQuestionSets);
        assertEquals(2, allQuestionSets.size());
    }

    @Test
    public void updateQuestionSet() throws InterruptedException {

        QuestionSetEntity questionSet = new QuestionSetEntity("Sample", createSampleQuestions());
        repository.insert(questionSet);

        QuestionSetEntity retrieved = repository.getQuestionSetByName("Sample");
        assertNotNull(retrieved);

        // Update the name and questions
        retrieved.setName("UpdatedSample");
        retrieved.addQuestion(
                new Question("What is the capital of Portugal?",
                        new MultipleChoiceTextAnswer(
                                Arrays.asList("Lisbon", "Rome"), "Lisbon")
                        )
        );
        repository.update(retrieved);

        QuestionSetEntity updated = repository.getQuestionSetByName("UpdatedSample");
        assertNotNull(updated);
        assertEquals("UpdatedSample", updated.getName());
        assertEquals(3, updated.getQuestions().size());
    }

    @Test
    public void deleteQuestionSet() throws InterruptedException {

        QuestionSetEntity questionSet1 = new QuestionSetEntity("Sample1", createSampleQuestions());
        QuestionSetEntity questionSet2 = new QuestionSetEntity("Sample2", createSampleQuestions());

        repository.insert(questionSet1);
        repository.insert(questionSet2);

        List<QuestionSetEntity> allQuestionSets = repository.getAllQuestionSets();
        assertEquals(2, allQuestionSets.size());

        QuestionSetEntity retrievedSet1 = allQuestionSets.get(0);
        QuestionSetEntity retrievedSet2 = allQuestionSets.get(1);

        repository.delete(retrievedSet2);

        allQuestionSets = repository.getAllQuestionSets();

        assertEquals(1, allQuestionSets.size());
        assertEquals("Sample1", allQuestionSets.get(0).getName());
    }
}
