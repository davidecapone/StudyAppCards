package project.study.app;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import project.study.app.model.Statistics;

/**
 * Unit tests for the Statistics class.
 * This class tests the functionality of incrementing correct and incorrect answers,
 * as well as calculating the proportion of correct answers.
 */
public class StatisticsTest {
    private Statistics stats;

    @Before
    public void set_up() {
        // Initialize a Statistics instance before each test
        stats = new Statistics();
    }

    /**
     * Test the incrementCorrectAnswers method.
     * Verifies that the number of correct answers is incremented correctly.
     */
    @Test
    public void testIncrementCorrectAnswers() {
        stats.incrementCorrectAnswers();
        assertEquals(1, stats.getNumberOfCorrectAnswer());
    }
    /**
     * Test the incrementIncorrectAnswers method.
     * Verifies that the number of incorrect answers is incremented correctly.
     */
    @Test
    public void testIncrementIncorrectAnswers() {
        stats.incrementIncorrectAnswers();
        assertEquals(1, stats.getNumberOfIncorrectAnswers());
    }

    /**
     * Test the calculateProportionCorrect method.
     * Verifies that the proportion of correct answers is calculated correctly.
     */
    @Test
    public void testCalculateProportionCorrect() {
        stats.incrementCorrectAnswers();
        stats.incrementCorrectAnswers();
        stats.incrementIncorrectAnswers();
        double proportionCorrect = stats.calculateProportionCorrect();
        assertEquals((2.0 / 3.0) * 100, proportionCorrect, 0.001);
    }
}
