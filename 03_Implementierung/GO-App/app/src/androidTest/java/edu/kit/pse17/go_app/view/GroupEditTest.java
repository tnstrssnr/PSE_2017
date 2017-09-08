package edu.kit.pse17.go_app.view;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.kit.pse17.go_app.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GroupEditTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityTestRule = new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void groupEditTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.group_recycler),
                        withParent(allOf(withId(R.id.grop_list_constraint),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.edit_group_name), withText("espressoedit"), isDisplayed()));
        textView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageView = onView(
                allOf(withId(R.id.edit), isDisplayed()));
        imageView.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.edit_group_name), withText("espressoedit"), isDisplayed()));
        editText.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.edit_group_name), withText("espressoedit"), isDisplayed()));
        editText2.perform(replaceText("espressoedited"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.perform_add_group), withText("Save"), isDisplayed()));
        button.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.edit_group_name), withText("espressoedited"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.toolbar2),
                                                childAtPosition(
                                                        childAtPosition(
                                                                allOf(withId(android.R.id.content),
                                                                        childAtPosition(
                                                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                                                0)),
                                                                0),
                                                        0)),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("espressoedited")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.edit), isDisplayed()));
        imageView2.perform(click());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.edit_group_name), withText("espressoedited"), isDisplayed()));
        editText3.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.edit_group_name), withText("espressoedited"), isDisplayed()));
        editText4.perform(replaceText("espressoedit"), closeSoftKeyboard());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.perform_add_group), withText("Save"), isDisplayed()));
        button2.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.edit_group_name), withText("espressoedit"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.toolbar2),
                                                childAtPosition(
                                                        childAtPosition(
                                                                allOf(withId(android.R.id.content),
                                                                        childAtPosition(
                                                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                                                0)),
                                                                0),
                                                        0)),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("espressoedit")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
