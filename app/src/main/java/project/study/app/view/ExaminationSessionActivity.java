package project.study.app.view;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import project.study.app.R;
import project.study.app.presenter.ExaminationSessionPresenter;
import project.study.app.repository.QuestionSetRepository;
import project.study.app.repository.RepositoryFactory;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.view.interfaces.ExaminationSessionView;

import project.study.app.model.domain.Question;

public class ExaminationSessionActivity extends AppCompatActivity implements ExaminationSessionView {

    private ExaminationSessionPresenter presenter;
    private TextView textViewQuestion;
    private EditText editTextAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_session);

        // Initialize the repository, service, and presenter
        QuestionSetRepository repository = RepositoryFactory.create(this);
        QuestionSetServiceImplementation service = new QuestionSetServiceImplementation(repository);
        presenter = new ExaminationSessionPresenter(service, this);

        // Get the question set name from the intent
        String questionSetName = getIntent().getStringExtra("questionSetName");

        // Initialize views
        textViewQuestion = findViewById(R.id.textViewQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        Button buttonSubmitAnswer = findViewById(R.id.buttonSubmitAnswer);

        buttonSubmitAnswer.setOnClickListener(v -> {
            String answer = editTextAnswer.getText().toString().trim();
            if (!answer.isEmpty()) {
                presenter.checkAnswer(answer);
            } else {
                showMessage("Please enter an answer");
            }
        });

        // Start the examination session
        presenter.startExamination(questionSetName);
    }

    @Override
    public void displayQuestion(Question question) {
        textViewQuestion.setText(question.getText());
        editTextAnswer.setText("");
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToManualMode() {
        Intent intent = new Intent(this, ManualModeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish the current activity
    }
}
