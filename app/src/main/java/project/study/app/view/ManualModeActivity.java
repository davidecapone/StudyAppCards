package project.study.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.study.app.R;
import project.study.app.model.domain.QuestionSet;
import project.study.app.presenter.ManualModePresenter;
import project.study.app.repository.QuestionSetRepository;
import project.study.app.repository.RepositoryFactory;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.view.QuestionSetAdapter.QuestionSetClickListener;
import project.study.app.view.interfaces.ManualModeView;

/**
 * Activity for the manual mode
 */
public class ManualModeActivity extends AppCompatActivity implements ManualModeView {

    // Presenter
    private ManualModePresenter presenter;

    // Views
    private QuestionSetAdapter adapter;
    private EditText editTextQuestionSetName;

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
        setContentView(R.layout.activity_manual_mode);

        QuestionSetRepository repository = RepositoryFactory.create(this);

        QuestionSetServiceImplementation service = new QuestionSetServiceImplementation(repository);
        presenter = new ManualModePresenter(service, this);

        editTextQuestionSetName = findViewById(R.id.editTextQuestionSetName);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter
        adapter = new QuestionSetAdapter(new QuestionSetClickListener() {

            /**
             * Called when a question set is clicked.
             *
             * @param questionSet The question set
             */
            @Override
            public void onQuestionSetClicked(QuestionSet questionSet) {
                presenter.onQuestionSetSelected(questionSet);
            }

            /**
             * Called when the delete button is clicked.
             *
             * @param questionSet The question set
             */
            @Override
            public void onDeleteButtonClicked(QuestionSet questionSet) {
                presenter.deleteQuestionSet(questionSet);
            }

            /**
             * Called when the start examination button is clicked.
             *
             * @param questionSet The question set
             */
            @Override
            public void onStartExaminationButtonClicked(QuestionSet questionSet) {
                presenter.onStartExaminationSessionButtonClicked(questionSet);
            }

            /**
             * Called when the modify button is clicked.
             *
             * @param questionSet The question set
             */
            @Override
            public void onModifyButtonClicked(QuestionSet questionSet) {
                presenter.onQuestionSetSelected(questionSet);
            }
        });

        // Set the adapter
        recyclerView.setAdapter(adapter);

        // Initialize the add question set button
        Button buttonAddQuestionSet = findViewById(R.id.buttonAddQuestionSet);

        // Set the click listener for the add question set button
        buttonAddQuestionSet.setOnClickListener(v -> {

            // Get the question set name from the edit text
            String questionSetName = editTextQuestionSetName.getText().toString().trim();

            if (!questionSetName.isEmpty()) {

                presenter.addNewQuestionSet(questionSetName);

            } else {

                Toast.makeText(ManualModeActivity.this, "Please enter a question set name", Toast.LENGTH_SHORT).show();
            }
        });

        presenter.loadAllQuestionSets();
    }

    /**
     * Get the edit text for the question set name.
     *
     * @return The edit text for the question set name
     */
    public EditText getEditTextQuestionSetName() {
        return editTextQuestionSetName;
    }

    /**
     * Display the question sets.
     *
     * @param questionSets The question sets to display
     */
    @Override
    public void displayQuestionSets(List<QuestionSet> questionSets) {
        adapter.setQuestionSets(questionSets);
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
     * Navigate to the question set details.
     *
     * @param questionSet The question set
     */
    @Override
    public void navigateToQuestionSetDetails(QuestionSet questionSet) {
        Intent intent = new Intent(this, QuestionSetDetailsActivity.class);
        intent.putExtra("questionSetName", questionSet.getQuestionSetName());
        startActivity(intent);
    }

    /**
     * Navigate to the examination session.
     *
     * @param questionSet The question set
     */
    @Override
    public void navigateToExaminationSession(QuestionSet questionSet) {
        Intent intent = new Intent(this, ExaminationSessionActivity.class);
        intent.putExtra("questionSetName", questionSet.getQuestionSetName());
        startActivity(intent);
    }
}