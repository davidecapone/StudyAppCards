package project.study.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.study.app.R;
import project.study.app.model.domain.QuestionSet;
import project.study.app.presenter.ManualModePresenter;
import project.study.app.repository.QuestionSetRepository;
import project.study.app.repository.QuestionSetRepositoryImplementation;
import project.study.app.repository.RepositoryFactory;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.view.QuestionSetAdapter.QuestionSetClickListener;

public class ManualModeActivity extends AppCompatActivity implements ManualModeView {

    private ManualModePresenter presenter;
    private QuestionSetAdapter adapter;
    private EditText editTextQuestionSetName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_mode);

        QuestionSetRepository repository = RepositoryFactory.create(this);


        QuestionSetServiceImplementation service = new QuestionSetServiceImplementation(repository);
        presenter = new ManualModePresenter(service, this);

        editTextQuestionSetName = findViewById(R.id.editTextQuestionSetName);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new QuestionSetAdapter(new QuestionSetClickListener() {
            @Override
            public void onQuestionSetClicked(QuestionSet questionSet) {
                presenter.onQuestionSetSelected(questionSet);
            }

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

        Button buttonAddQuestionSet = findViewById(R.id.buttonAddQuestionSet);

        buttonAddQuestionSet.setOnClickListener(v -> {
            String questionSetName = editTextQuestionSetName.getText().toString().trim();
            if (!questionSetName.isEmpty()) {
                presenter.addNewQuestionSet(questionSetName);
            } else {
                Toast.makeText(ManualModeActivity.this, "Please enter a question set name", Toast.LENGTH_SHORT).show();
            }
        });

        presenter.loadAllQuestionSets();
    }

    public EditText getEditTextQuestionSetName() {
        return editTextQuestionSetName;
    }

    @Override
    public void displayQuestionSets(List<QuestionSet> questionSets) {
        adapter.setQuestionSets(questionSets);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToQuestionSetDetails(QuestionSet questionSet) {
        Intent intent = new Intent(this, QuestionSetDetailsActivity.class);
        intent.putExtra("questionSetName", questionSet.getQuestionSetName());
        startActivity(intent);
    }

    @Override
    public void navigateToExaminationSession(QuestionSet questionSet) {
        // Implement navigation to examination session activity
    }

    // For testing purposes
    public int getContentView() {
        return findViewById(android.R.id.content).getId();
    }
}