package project.study.app.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import project.study.app.R;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.presenter.QuestionSetDetailsPresenter;
import project.study.app.repository.QuestionSetRepository;
import project.study.app.repository.RepositoryFactory;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.view.interfaces.QuestionSetDetailsView;

public class QuestionSetDetailsActivity extends AppCompatActivity implements QuestionSetDetailsView {

    private QuestionSetDetailsPresenter presenter;
    private QuestionAdapter adapter;
    private EditText editTextQuestion;
    private Spinner spinnerAnswerType;
    private EditText editTextFreeTextAnswer;
    private EditText editTextMultipleChoiceOptions;
    private EditText editTextMultipleChoiceAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_set_details);

        // Initialize the repository, service, and presenter
        QuestionSetRepository repository = RepositoryFactory.create(this);
        QuestionSetServiceImplementation service = new QuestionSetServiceImplementation(repository);
        presenter = new QuestionSetDetailsPresenter(service, this);

        // Get the question set name from the intent
        String questionSetName = getIntent().getStringExtra("questionSetName");

        // Initialize views
        TextView textViewQuestionSetName = findViewById(R.id.textViewQuestionSetName);
        textViewQuestionSetName.setText(questionSetName);

        editTextQuestion = findViewById(R.id.editTextQuestion);
        spinnerAnswerType = findViewById(R.id.spinnerAnswerType);
        editTextFreeTextAnswer = findViewById(R.id.editTextFreeTextAnswer);
        editTextMultipleChoiceOptions = findViewById(R.id.editTextMultipleChoiceOptions);
        editTextMultipleChoiceAnswer = findViewById(R.id.editTextMultipleChoiceAnswer);

        // Set up the Spinner to handle answer type selection
        spinnerAnswerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAnswerType = parent.getItemAtPosition(position).toString();
                if (selectedAnswerType.equals("Free Text")) {
                    editTextFreeTextAnswer.setVisibility(View.VISIBLE);
                    editTextMultipleChoiceOptions.setVisibility(View.GONE);
                    editTextMultipleChoiceAnswer.setVisibility(View.GONE);
                } else {
                    editTextFreeTextAnswer.setVisibility(View.GONE);
                    editTextMultipleChoiceOptions.setVisibility(View.VISIBLE);
                    editTextMultipleChoiceAnswer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editTextFreeTextAnswer.setVisibility(View.GONE);
                editTextMultipleChoiceOptions.setVisibility(View.GONE);
                editTextMultipleChoiceAnswer.setVisibility(View.GONE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new QuestionAdapter();
        recyclerView.setAdapter(adapter);

        Button buttonAddQuestion = findViewById(R.id.buttonAddQuestion);
        buttonAddQuestion.setOnClickListener(v -> {
            String questionText = editTextQuestion.getText().toString().trim();
            if (!questionText.isEmpty()) {
                Question newQuestion;
                String selectedAnswerType = spinnerAnswerType.getSelectedItem().toString();
                if (selectedAnswerType.equals("Free Text")) {
                    String freeTextAnswer = editTextFreeTextAnswer.getText().toString().trim();
                    newQuestion = new Question(questionText, new FreeTextAnswer(freeTextAnswer));
                } else {
                    String multipleChoiceOptions = editTextMultipleChoiceOptions.getText().toString().trim();
                    String multipleChoiceAnswer = editTextMultipleChoiceAnswer.getText().toString().trim();
                    List<String> options = Arrays.asList(multipleChoiceOptions.split(","));
                    newQuestion = new Question(questionText, new MultipleChoiceTextAnswer(options, multipleChoiceAnswer));
                }
                presenter.addQuestion(newQuestion);
            } else {
                showMessage("Please enter a question");
            }
        });

        // Load questions for the specified question set
        presenter.loadQuestionSet(questionSetName);
    }

    @Override
    public void displayQuestions(List<Question> questions) {
        adapter.setQuestions(questions);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}