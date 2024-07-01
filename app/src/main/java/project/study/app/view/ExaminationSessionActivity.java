package project.study.app.view;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import project.study.app.BaseActivity;
import project.study.app.R;
import project.study.app.presenter.ExaminationSessionPresenter;
import project.study.app.repository.interfaces.Repository;
import project.study.app.repository.RepositoryFactory;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.view.interfaces.ExaminationSessionView;

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

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // Call the super class onCreate to complete the creation of activity like
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_session);

        // Set up the presenter:
        presenter = new ExaminationSessionPresenter(super.getService(), this);

        // Get the question set name from the intent
        String questionSetName = getIntent().getStringExtra("questionSetName");

        // Initialize views
        textViewQuestion = findViewById(R.id.textViewQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        Button buttonSubmitAnswer = findViewById(R.id.buttonSubmitAnswer);

        buttonSubmitAnswer.setOnClickListener(v -> {
            String answer = editTextAnswer.getText().toString().trim();
            if (!answer.isEmpty()) {
                presenter.validateAnswer(answer);
            } else {
                showMessage("Please enter an answer");
            }
        });

        // Start the examination session
        presenter.startExamination(questionSetName);
    }

    /**
     * Display the question.
     *
     * @param question The question to display
     */
    @Override
    public void displayQuestion(Question question) {
        textViewQuestion.setText(question.getText());
        editTextAnswer.setText("");
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
