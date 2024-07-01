package project.study.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import project.study.app.presenter.ExaminationSessionPresenter;
import project.study.app.repository.RepositoryFactory;
import project.study.app.repository.interfaces.Repository;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.service.interfaces.QuestionSetService;

public class BaseActivity extends AppCompatActivity {

    private QuestionSetService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependecies();
    }

    private void injectDependecies() {
        // Initialize the repository, service, and presenter
        Repository repository = RepositoryFactory.create(this);
        this.service = new QuestionSetServiceImplementation(repository);
    }

    public QuestionSetService getService() {
        return this.service;
    }
}
