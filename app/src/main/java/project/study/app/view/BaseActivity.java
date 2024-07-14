package project.study.app.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import project.study.app.model.entity.QuestionSetEntity;
import project.study.app.repository.RepositoryFactory;
import project.study.app.repository.interfaces.Repository;
import project.study.app.service.ServiceImplementation;
import project.study.app.service.interfaces.Service;

public class BaseActivity extends AppCompatActivity {
    private Service service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dependencies();
    }

    /**
     * Inject the dependencies for the child-activity.
     */
    private void dependencies() {
        Repository<QuestionSetEntity,String> repository = RepositoryFactory.create(this);
        this.service = new ServiceImplementation(
                repository
        );
    }

    public Service getService() {
        return this.service;
    }
}
