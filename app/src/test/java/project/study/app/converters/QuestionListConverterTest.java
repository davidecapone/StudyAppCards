package project.study.app.converters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import project.study.app.model.converters.QuestionListConverter;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.Question;

/**
 * A test class for the QuestionListConverter.
 */
public class QuestionListConverterTest {

    /**
     * Test the conversion from a JSON string to a list of questions.
     */
    @Test
    public void testFromString() {

        String json = "[{\"questionText\":\"What is the capital of Italy?\",\"answerType\":\"FreeTextAnswer\",\"correctAnswer\":\"Rome\"},{\"questionText\":\"What is the capital of Germany?\",\"answerType\":\"FreeTextAnswer\",\"correctAnswer\":\"Berlin\"}]";
        List<Question> questions = QuestionListConverter.toList(json);

        assertEquals(2, questions.size());
        assertEquals("What is the capital of Italy?", questions.get(0).getText());
        assertEquals("Rome", ((FreeTextAnswer) questions.get(0).getAnswer()).getCorrectAnswer());
        assertEquals("What is the capital of Germany?", questions.get(1).getText());
        assertEquals("Berlin", ((FreeTextAnswer) questions.get(1).getAnswer()).getCorrectAnswer());
    }

    /**
     * Test the conversion from a list of questions to a JSON string.
     */
    @Test
    public void testFromList() {

        Question question1 = new Question("What is the capital of Italy?", new FreeTextAnswer("Rome"));
        Question question2 = new Question("What is the capital of Germany?", new FreeTextAnswer("Berlin"));

        List<Question> questions = Arrays.asList(question1, question2);
        String json = QuestionListConverter.fromList(questions);

        assertEquals("[{\"questionText\":\"What is the capital of Italy?\",\"answerType\":\"FreeTextAnswer\",\"correctAnswer\":\"Rome\"},{\"questionText\":\"What is the capital of Germany?\",\"answerType\":\"FreeTextAnswer\",\"correctAnswer\":\"Berlin\"}]", json);
    }
}