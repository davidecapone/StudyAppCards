package project.study.app.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import project.study.app.R;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;
import project.study.app.presenter.QuestionSetDetailsPresenter;
import project.study.app.view.interfaces.QuestionSetDetailsView;
import project.study.app.view.viewadpter.QuestionAdapter;

/**
 * Activity for the question set details
 */
public class QuestionSetDetailsActivity extends BaseActivity implements QuestionSetDetailsView {

    private QuestionSetDetailsPresenter presenter;
    private QuestionAdapter adapter;
    private EditText editTextQuestion;
    private Spinner spinnerAnswerType;
    private EditText editTextFreeTextAnswer;
    private EditText editTextMultipleChoiceOptions;
    private EditText editTextMultipleChoiceAnswer;
    private TextView textViewQuestionSetName;
    private RecyclerView recyclerView;
    private Button buttonAddQuestion;
    String currentQuestionSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // Call the super class onCreate to complete the creation of activity like
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_set_details);

        // Set up the presenter:
        presenter = new QuestionSetDetailsPresenter(
                super.getService(),
                this
        );

        // Get the question set name from the intent:
        currentQuestionSet = getIntent().getStringExtra("questionSetName");

        initializeViews();

        // Set up the Spinner to handle answer type selection
        spinnerAnswerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Callback method to be invoked when an item in this view has been selected.
             *
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param position The position of the view in the adapter
             * @param id The row id of the item that is selected
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Get the selected answer type
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

            /**
             * Callback method to be invoked when the selection disappears from this view.
             * The selection can disappear for instance when touch is activated or when the adapter becomes empty.
             *
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                editTextFreeTextAnswer.setVisibility(View.GONE);
                editTextMultipleChoiceOptions.setVisibility(View.GONE);
                editTextMultipleChoiceAnswer.setVisibility(View.GONE);
            }
        });

        // Initialize the adapter
        adapter = new QuestionAdapter();
        recyclerView.setAdapter(adapter);

        // Set the click listener for the add question button
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
        presenter.loadQuestionSet(currentQuestionSet);
    }

    private void initializeViews() {
        textViewQuestionSetName = findViewById(R.id.textViewQuestionSetName);
        textViewQuestionSetName.setText(currentQuestionSet);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        spinnerAnswerType = findViewById(R.id.spinnerAnswerType);
        editTextFreeTextAnswer = findViewById(R.id.editTextFreeTextAnswer);
        editTextMultipleChoiceOptions = findViewById(R.id.editTextMultipleChoiceOptions);
        editTextMultipleChoiceAnswer = findViewById(R.id.editTextMultipleChoiceAnswer);
        buttonAddQuestion = findViewById(R.id.buttonAddQuestion);
        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void displayQuestions(List<Question> questions) {
        adapter.setQuestions(questions);
    }

    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}