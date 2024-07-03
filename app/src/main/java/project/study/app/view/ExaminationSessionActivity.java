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

    // Presenter
    private ExaminationSessionPresenter presenter;

    // Views
    private TextView textViewQuestion;
    private EditText editTextAnswer;
    private RadioGroup radioGroupAnswers;
    private Button buttonSubmitAnswer;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_session);

        // Set up the presenter:
        presenter = new ExaminationSessionPresenter(super.getService(), this);

        // Get the question set name from the intent
        String questionSetName = getIntent().getStringExtra("questionSetName");

        initializeViews();

        // Set up button click listener to submit the answer
        buttonSubmitAnswer.setOnClickListener(v -> submitAnswer());

        // Start the examination session
        presenter.startExamination(questionSetName);
    }

    private void initializeViews() {
        textViewQuestion = findViewById(R.id.textViewQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        radioGroupAnswers = findViewById(R.id.radioGroupAnswers);
        buttonSubmitAnswer = findViewById(R.id.buttonSubmitAnswer);
    }

    /**
     * Extracted method to handle answer submission.
     */
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

    /**
     * Display the question.
     *
     * @param question The question to display
     */
    @Override
    public void displayQuestion(Question question) {

        // set the question text
        textViewQuestion.setText(question.getText());

        if (question.getAnswer() instanceof FreeTextAnswer) {

            // display the edit text and hide the radio group:
            editTextAnswer.setVisibility(View.VISIBLE);
            radioGroupAnswers.setVisibility(View.GONE);
            // clear the answer for this edit text:
            editTextAnswer.setText("");

        } else if (question.getAnswer() instanceof MultipleChoiceTextAnswer) {

            // hide the edit text, show the radio group:
            editTextAnswer.setVisibility(View.GONE);
            radioGroupAnswers.setVisibility(View.VISIBLE);
            radioGroupAnswers.removeAllViews();

            List<String> options = ((MultipleChoiceTextAnswer) question.getAnswer()).getOptions();

            // for each option display a single radio button:
            for (String option : options) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(option.trim());
                radioGroupAnswers.addView(radioButton);
            }
        }
    }

    /**
     * Show feedback for a correct answer.
     */
    @Override
    public void showCorrectAnswerFeedback() {
        showMessage("Correct!");
    }

    /**
     * Show feedback for an incorrect answer.
     */
    @Override
    public void showIncorrectAnswerFeedback() {
        showMessage("Incorrect!");
    }

    /**
     * Show a message.
     *
     * @param message The message to show
     */
    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Navigate to the manual mode.
     */
    @Override
    public void navigateToManualMode() {
        Intent intent = new Intent(this, ManualModeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish the current activity
    }
}