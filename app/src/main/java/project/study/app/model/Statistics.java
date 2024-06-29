package project.study.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Model class representing the statistics for an examination session.
 * This class tracks the number of correct and incorrect answers for a given session.
 */
public class Statistics {

    // Primary key for the statistics entry
    @PrimaryKey(autoGenerate = true)
    private int id;

    // Number of correct answers
    private int numberOfCorrectAnswers;

    // Number of incorrect answers
    private int numberOfIncorrectAnswers;

    /**
     * Default constructor for the Statistics class.
     *
     * Initializes the number of correct answers to 0.
     */
    public Statistics() {
        numberOfCorrectAnswers = 0;
    }

    /**
     * Gets the number of correct answers for the session.
     *
     * @return the number of correct answers
     */
    public int getNumberOfCorrectAnswer() {
        return numberOfCorrectAnswers;
    }

    /**
     * Gets the number of incorrect answers for the session.
     *
     * @return the number of incorrect answers
     */
    public int getNumberOfIncorrectAnswers() {
        return numberOfIncorrectAnswers;
    }

    /**
     * Increments the number of correct answers by 1.
     */
    public void incrementCorrectAnswers() {
        numberOfCorrectAnswers++;
    }

    /**
     * Increments the number of incorrect answers by 1.
     */
    public void incrementIncorrectAnswers() {
        numberOfIncorrectAnswers++;
    }

    /**
     * Calculates the proportion of correct answers for the session.
     *
     * @return the proportion of correct answers as a percentage
     */
    public double calculateProportionCorrect() {
        int totalAnswers = numberOfCorrectAnswers + numberOfIncorrectAnswers;
        return totalAnswers > 0 ? ((double) numberOfCorrectAnswers / totalAnswers)*100 : 0;
    }
}
