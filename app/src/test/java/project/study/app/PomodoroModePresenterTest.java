package project.study.app;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Timer;

import project.study.app.presenter.PomodoroModePresenter;
import project.study.app.view.interfaces.PomodoroModeView;

public class PomodoroModePresenterTest {

    @Mock
    private PomodoroModeView view;
    private PomodoroModePresenter presenter;

    @Before
    public void set_up() {
        MockitoAnnotations.initMocks(this);
        presenter = new PomodoroModePresenter(view);
    }

    @Test
    public void testStartPomodoroMode() {
        // Act
        presenter.startPomodoroMode();

        // Assert
        InOrder inOrder = inOrder(view);
        inOrder.verify(view).showStudySession();
        inOrder.verify(view).showBreakSession();
        inOrder.verify(view).showInsertQuestionsSession();
        inOrder.verify(view).showExaminationSession();
        inOrder.verify(view).showCompletionMessage();
    }
}
