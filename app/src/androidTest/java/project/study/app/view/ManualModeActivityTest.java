package project.study.app.view;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;

import android.widget.EditText;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import project.study.app.R;
import project.study.app.model.domain.QuestionSet;

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

    @Test
    public void testDisplayQuestionSets() {

        // Create a list of QuestionSet objects to display
        List<QuestionSet> questionSets = Arrays.asList(
                new QuestionSet("Question Set 1"),
                new QuestionSet("Question Set 2"),
                new QuestionSet("Question Set 3")
        );

        // Use ActivityScenario to run the displayQuestionSets method
        scenario.onActivity(activity -> {
            activity.displayQuestionSets(questionSets);
        });

        // Check if the RecyclerView has the correct items
        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(withText("Question Set 1"))))
                .check(matches(hasDescendant(withText("Question Set 2"))))
                .check(matches(hasDescendant(withText("Question Set 3"))));
    }

    @Test
    public void testShowMessage() {

        // There is a problem with the asynchronous calls, maybe this can be tested by adding callbacks
    }

    @Test
    public void testNavigateToQuestionSetDetails() {

        Intents.init();

        // Create a QuestionSet object to pass
        QuestionSet questionSet = new QuestionSet("Sample Question Set");

        // Run the navigateToQuestionSetDetails method on the main thread
        scenario.onActivity(activity -> {
            activity.navigateToQuestionSetDetails(questionSet);
        });

        // Verify that the correct intent was sent
        intended(hasComponent(QuestionSetDetailsActivity.class.getName()));
        intended(hasExtra("questionSetName", equalTo("Sample Question Set")));
    }
}
