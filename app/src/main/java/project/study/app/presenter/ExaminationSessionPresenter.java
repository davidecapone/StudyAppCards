package project.study.app.presenter;

import project.study.app.model.domain.Question;
import project.study.app.service.QuestionSetService;

import project.study.app.view.ExaminationSessionView;
import project.study.app.service.QuestionSetService;
import project.study.app.model.domain.QuestionSet;

import java.util.List;
import java.util.Collections;

public class ExaminationSessionPresenter {

    private final QuestionSetService service;
    private final ExaminationSessionView view;
    private final QuestionSet currentQuestionSet;
    private int currentQuestionIndex = 0;

    public ExaminationSessionPresenter(ExaminationSessionView view, QuestionSetService service, QuestionSet questionSet) {

        this.view = view;

        this.service = service;

        this.currentQuestionSet = questionSet;

    }

    public void startSession(){
        List<Question> questions = currentQuestionSet.getQuestions();

        Collections.shuffle(questions);

        view.showQuestion(questions.get(currentQuestionIndex));

        currentQuestionIndex++;
    }

    public void nextQuestion(){

        if(currentQuestionIndex == currentQuestionSet.getQuestions().size()){
            endSession();
        } else {
            view.showQuestion(currentQuestionSet.getQuestions().get(currentQuestionIndex));
            currentQuestionIndex++;
        }
    }

    public void endSession(){
        view.navigateToManualModeView();
    }
}
