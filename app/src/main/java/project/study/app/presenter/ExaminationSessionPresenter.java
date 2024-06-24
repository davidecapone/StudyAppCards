package project.study.app.presenter;

import project.study.app.model.domain.Question;
import project.study.app.service.QuestionSetService;

import project.study.app.service.SingleItemCallback;
import project.study.app.view.ExaminationSessionView;
import project.study.app.service.QuestionSetService;
import project.study.app.model.domain.QuestionSet;

import java.util.List;
import java.util.Collections;

public class ExaminationSessionPresenter {

    private final QuestionSetService service;
    private final ExaminationSessionView view;
    private List<Question> questions;
    private int currentQuestionIndex;

    public ExaminationSessionPresenter(QuestionSetService service, ExaminationSessionView view) {
        this.service = service;
        this.view = view;
    }

    public void startExamination(String questionSetName) {
        service.getQuestionSetByName(questionSetName, new SingleItemCallback<QuestionSet>() {
            @Override
            public void onSuccess(QuestionSet item) {
                questions = item.getQuestions();
                currentQuestionIndex = 0;

                if (!questions.isEmpty()) {
                    view.displayQuestion(questions.get(currentQuestionIndex));
                } else {
                    view.showMessage("This question set is empty!");
                }
            }

            @Override
            public void onError(Exception e) {
                view.showMessage("Error loading question set: " + e.getMessage());
            }
        });
    }

    public void checkAnswer(String answer) {

        Question currentQuestion = questions.get(currentQuestionIndex);

        if (currentQuestion.getAnswer().getCorrectAnswer().equals(answer)) {
            view.showCorrectAnswerFeedback();
        } else {
            view.showIncorrectAnswerFeedback();
        }

        // move to next question
        moveToNextQuestion();
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            view.displayQuestion(questions.get(currentQuestionIndex));
        } else {
            view.showMessage("Examination session completed.");
        }
    }
}
