package project.study.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.study.app.R;
import project.study.app.presenter.ExaminationSessionPresenter;
import project.study.app.view.interfaces.ExaminationSessionView;

import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;

/**
 * Activity for the examination session
 */
public class ExaminationSessionActivity extends BaseActivity implements ExaminationSessionView {

    private ExaminationSessionPresenter presenter;
    private TextView textViewQuestion;
    private EditText editTextAnswer;
    private RadioGroup radioGroupAnswers;
    private Button buttonSubmitAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_session);

        presenter = new ExaminationSessionPresenter( // Set up the presenter for this view
                super.getService(),
                this
        );

        // Get the question set name from the intent
        String questionSetName = getIntent().getStringExtra("questionSetName");

        initializeViews();
        initializeListeners();

        presenter.startExamination(questionSetName);
    }

    private void initializeViews() {
        textViewQuestion = findViewById(R.id.textViewQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        radioGroupAnswers = findViewById(R.id.radioGroupAnswers);
        buttonSubmitAnswer = findViewById(R.id.buttonSubmitAnswer);
    }

    private void initializeListeners() {
        buttonSubmitAnswer.setOnClickListener( // Set up the listener for submitting answer
                v -> submitAnswer()
        );
    }

    private void submitAnswer() {
        if (editTextAnswer.getVisibility() == View.VISIBLE) {
            String answer = editTextAnswer.getText().toString().trim();
            if (!answer.isEmpty()) {
                presenter.validateAnswer(answer);
            } else {
                showMessage("Please enter an answer");
            }
        } else if (radioGroupAnswers.getVisibility() == View.VISIBLE) {
            int selectedId = radioGroupAnswers.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                String answer = selectedRadioButton.getText().toString();
                presenter.validateAnswer(answer);
            } else {
                showMessage("Please select an answer");
            }
        }
    }

    @Override
    public void displayQuestion(Question question) {

        textViewQuestion.setText(question.getText()); // Set the question text

        if (question.getAnswer() instanceof FreeTextAnswer) {

            editTextAnswer.setVisibility(View.VISIBLE); // display the edit text
            radioGroupAnswers.setVisibility(View.GONE); // .. and and hide the radio group:
            editTextAnswer.setText("");

        } else if (question.getAnswer() instanceof MultipleChoiceTextAnswer) {

            editTextAnswer.setVisibility(View.GONE); // hide the edit text
            radioGroupAnswers.setVisibility(View.VISIBLE); // .. and show the radio group:
            radioGroupAnswers.removeAllViews();

            List<String> options = ((MultipleChoiceTextAnswer) question.getAnswer()).getOptions();

            for (String option : options) { // for each option display a single radio button
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(option.trim());
                radioGroupAnswers.addView(radioButton);
            }
        }
    }

    @Override
    public void showCorrectAnswerFeedback() {
        showMessage("Correct!");
    }

    @Override
    public void showIncorrectAnswerFeedback() {
        showMessage("Incorrect!");
    }

    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToManualModeActivity () {
        Intent intent = new Intent(this, ManualModeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}