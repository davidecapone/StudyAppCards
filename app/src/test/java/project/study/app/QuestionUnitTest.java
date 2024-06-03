package project.study.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionUnitTest {
    @Test
    public void question_isCorrect() {
        Question question = new Question("Paris is the capital of France", "True");
        assertEquals(question.getAnswer(), "True");
    }
}
