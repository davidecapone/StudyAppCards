package project.study.app.view;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import project.study.app.R;

@RunWith(AndroidJUnit4.class)
public class ManualModeActivityTest {

    private ActivityScenario<ManualModeActivity> scenario;

    @Before
    public void setUp(){
        scenario = ActivityScenario.launch(ManualModeActivity.class);
    }

    @After
    public void tearDown(){
        scenario.close();
    }

    @Test
    public void testOnCreate() {

        // Check if the views are displayed correctly
        onView(withId(R.id.editTextQuestionSetName)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddQuestionSet)).check(matches(isDisplayed()));
    }
}
