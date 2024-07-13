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
import project.study.app.view.viewadpter.QuestionSetAdapter;
import project.study.app.view.viewadpter.QuestionSetAdapter.QuestionSetClickListener;
import project.study.app.view.interfaces.ManualModeView;

/**
 * Activity for the manual mode
 */
public class ManualModeActivity extends BaseActivity implements ManualModeView{
    
    private ManualModePresenter presenter;
    private QuestionSetAdapter adapter;
    private EditText editTextQuestionSetName;
    private Button buttonAddQuestionSet;
    private RecyclerView recyclerView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_mode);

        presenter = new ManualModePresenter( // Set up the presenter for this view
                super.getService(),
                this
        );

        initializeViews();
        initializeListeners();

        // Load all question sets from the presenter:
        presenter.loadAllQuestionSets();
    }

    private void initializeViews() {
        editTextQuestionSetName = findViewById(R.id.editTextQuestionSetName);
        buttonAddQuestionSet = findViewById(R.id.buttonAddQuestionSet);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeListeners() {
        buttonAddQuestionSet.setOnClickListener(v -> onAddQuestionSetClicked());

        adapter = new QuestionSetAdapter( new QuestionSetClickListener() {
            @Override
            public void onDeleteButtonClicked(QuestionSet questionSet) {
                presenter.deleteQuestionSet(questionSet);
            }
            @Override
            public void onStartExaminationButtonClicked(QuestionSet questionSet) {
                presenter.onStartExaminationSessionButtonClicked(questionSet);
            }
            @Override
            public void onModifyButtonClicked(QuestionSet questionSet) {
                presenter.onQuestionSetSelected(questionSet);
            }
        });

        recyclerView.setAdapter(adapter);
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
    
    @Override
    public void displayQuestionSets(List<QuestionSet> questionSets) {
        adapter.setQuestionSets(questionSets);
    }
    
    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToQuestionSetDetails(QuestionSet questionSet) {
        Intent intent = new Intent(this, QuestionSetDetailsActivity.class);
        intent.putExtra("questionSetName", questionSet.getQuestionSetName());
        startActivity(intent);
    }
    
    @Override
    public void navigateToExaminationSessionActivity(QuestionSet questionSet) {
        Intent intent = new Intent(this, ExaminationSessionActivity.class);
        intent.putExtra(
                "questionSetName", 
                questionSet.getQuestionSetName()
        );
        startActivity(intent);
    }
}