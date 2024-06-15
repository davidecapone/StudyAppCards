package project.study.app.presenter;

import project.study.app.model.domain.QuestionSet;
import project.study.app.persistence.FakeRepository;

import java.util.List;

public class ManualModePresenter {

    private FakeRepository repository;

    public ManualModePresenter() {
        this.repository = new FakeRepository();
    }

    public List<QuestionSet> getAllQuestionSets() {
        return repository.getAllQuestionSets();
    }
}
