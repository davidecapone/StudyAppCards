package project.study.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;

public class Statistics {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int numberOfCorrectAnswers;
    private int numberOfIncorrectAnswers;

    public Statistics() {
        numberOfCorrectAnswers = 0;
    }

    public int getNumberOfCorrectAnswer() {
        return numberOfCorrectAnswers;
    }

    public int getNumberOfIncorrectAnswers() {
        return numberOfIncorrectAnswers;
    }

    public void incrementCorrectAnswers() {
        numberOfCorrectAnswers++;
    }

    public void incrementIncorrectAnswers() {
        numberOfIncorrectAnswers++;
    }

    public double calculateProportionCorrect() {
        int totalAnswers = numberOfCorrectAnswers + numberOfIncorrectAnswers;
        return totalAnswers > 0 ? ((double) numberOfCorrectAnswers / totalAnswers)*100 : 0;
    }

}
