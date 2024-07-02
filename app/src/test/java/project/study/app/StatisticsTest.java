package project.study.app;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import project.study.app.model.Statistics;

/**
 * Unit tests for the Statistics class.
 */
public class StatisticsTest {
    private Statistics stats;

    @Before
    public void set_up() {
        stats = new Statistics();
    }

    /**
     * Test the getNumberOfCorrectAnswer method.
     */
    @Test
    public void testIncrementCorrectAnswers() {
        stats.incrementCorrectAnswers();
        assertEquals(1, stats.getNumberOfCorrectAnswer());
    }
    /**
     * Test the getNumberOfIncorrectAnswers method.
     */
    @Test
    public void testIncrementIncorrectAnswers() {
        stats.incrementIncorrectAnswers();
        assertEquals(1, stats.getNumberOfIncorrectAnswers());
    }
    /**
     * Test the calculateProportionCorrect method.
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
