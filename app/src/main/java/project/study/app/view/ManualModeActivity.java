package project.study.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.study.app.R;
import project.study.app.model.domain.QuestionSet;
import project.study.app.presenter.ManualModePresenter;
import project.study.app.view.QuestionSetAdapter.QuestionSetClickListener;
import project.study.app.view.interfaces.ManualModeView;

/**
 * Activity for the manual mode
 */
public class ManualModeActivity extends BaseActivity implements ManualModeView{

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

        // Set up the presenter:
        presenter = new ManualModePresenter(super.getService(), this);

        editTextQuestionSetName = findViewById(R.id.editTextQuestionSetName);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter
        adapter = new QuestionSetAdapter(new QuestionSetClickListener() {
            @Override
            public void onDeleteButtonClicked(QuestionSet questionSet) {
                // Called when the delete button is clicked.
                presenter.deleteQuestionSet(questionSet);
            }
            @Override
            public void onStartExaminationButtonClicked(QuestionSet questionSet) {
                // Called when the start examination button is clicked.
                presenter.onStartExaminationSessionButtonClicked(questionSet);
            }
            @Override
            public void onModifyButtonClicked(QuestionSet questionSet) {
                // Called when the modify button is clicked.
                presenter.onQuestionSetSelected(questionSet);
            }
        });

        recyclerView.setAdapter(adapter);

        // Initialize the add question set button
        Button buttonAddQuestionSet = findViewById(R.id.buttonAddQuestionSet);

        // Set the click listener for the add question set button
        buttonAddQuestionSet.setOnClickListener(v -> onAddQuestionSetClicked());

        // Load all question sets from the presenter:
        presenter.loadAllQuestionSets();
    }

    private void onAddQuestionSetClicked() {
        // Get the question set name from the edit text
        String questionSetName = editTextQuestionSetName.getText().toString().trim();

        if (!questionSetName.isEmpty()) {
            presenter.addNewQuestionSet(questionSetName);
        } else {
            showMessage("Please enter a question set name");
        }
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