package project.study.app.view;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.EditText;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void testGetEditTextQuestionSetName() {

        // Set some text in the EditText
        String inputText = "Sample Question Set Name";
        onView(withId(R.id.editTextQuestionSetName)).perform(replaceText(inputText));

        // Use ActivityScenario to run the assertion on the activity's main thread
        scenario.onActivity(activity -> {

            // Verify that getEditTextQuestionSetName returns the correct value
            EditText editText = activity.getEditTextQuestionSetName();
            assertEquals(inputText, editText.getText().toString());
        });

        // Verify that the EditText contains the correct text
        onView(withId(R.id.editTextQuestionSetName)).check(matches(withText(inputText)));
    }
}
