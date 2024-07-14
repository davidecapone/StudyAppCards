package project.study.app.presenter;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import project.study.app.util.Timer;
import project.study.app.util.TimerCallback;
import project.study.app.view.interfaces.PomodoroModeView;

/**
 * Test class for the PomodoroModePresenter
 */
public class PomodoroModePresenterTest {
    @Mock
    private PomodoroModeView view;
    @Mock
    private Timer timer;
    private PomodoroModePresenter presenter;

    @Before
    public void set_up() {
        MockitoAnnotations.initMocks(this);
        presenter = new PomodoroModePresenter(view, timer);
    }
    /**
     * Test that the startPomodoroMode method triggers the sequence of sessions
     */
    @Test
    public void testSequences() {
        // Mock the timer to call the onFinish method immediately
        doAnswer(invocation -> {
            TimerCallback callback = invocation.getArgument(2);
            callback.onFinish();
            return null;
        }).when(timer).start(anyLong(), anyLong(), eq(presenter));

        // Create an inOrder verifier to ensure the methods are called in the correct sequence
        InOrder inOrder = inOrder(view, timer);
        presenter.startPomodoroMode(); // This should trigger the sequence of sessions

        // Verify that the study session is shown and the timer is started for the study duration
        inOrder.verify(view).showSession(PomodoroSessions.Session.STUDY);
        inOrder.verify(timer).start(30 * 1000L, 1000L, presenter);

        // Verify that the break session is shown and the timer is started for the break duration
        inOrder.verify(view).showSession(PomodoroSessions.Session.BREAK);
        inOrder.verify(timer).start(10 * 1000L, 1000L, presenter);

        // Verify that the insert questions session is shown and the timer is started for the insert questions duration
        inOrder.verify(view).showSession(PomodoroSessions.Session.INSERT_QUESTIONS);
        inOrder.verify(timer).start(10 * 1000L, 1000L, presenter);

        // Verify that the examination session is shown and the timer is started for the examination duration
        inOrder.verify(view).showSession(PomodoroSessions.Session.EXAMINATION);
        inOrder.verify(timer).start(30 * 1000L, 1000L, presenter);

        // Verify that the completion message is shown after all sessions are completed
        inOrder.verify(view).showCompletionMessage();
    }
}
