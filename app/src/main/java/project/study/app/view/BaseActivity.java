package project.study.app.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import project.study.app.repository.RepositoryFactory;
import project.study.app.repository.interfaces.Repository;
import project.study.app.service.QuestionSetServiceImplementation;
import project.study.app.service.interfaces.QuestionSetService;

public class BaseActivity extends AppCompatActivity {
    private QuestionSetService service;

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
        injectDependecies();
    }
    /**
     * Inject the dependencies for the activity.
     */
    private void injectDependecies() {
        Repository repository = RepositoryFactory.create(this);
        this.service = new QuestionSetServiceImplementation(repository);
    }

    /**
     * Get the QuestionSetService.
     *
     * @return the QuestionSetService
     */
    public QuestionSetService getService() {
        return this.service;
    }
}
