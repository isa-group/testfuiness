package esadrcanfer.us.alumno.autotesting.tests;


import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import esadrcanfer.us.alumno.autotesting.MainActivity;
import esadrcanfer.us.alumno.autotesting.R;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SimpleTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void simpleTest() {
        ViewInteraction materialButton = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.button_first), ViewMatchers.withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        ViewMatchers.isDisplayed()));
        materialButton.perform(ViewActions.click());

        ViewInteraction floatingActionButton = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(android.R.id.content),
                                        0),
                                2),
                        ViewMatchers.isDisplayed()));
        floatingActionButton.perform(ViewActions.click());

        ViewInteraction materialButton2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.button_second), ViewMatchers.withText("Previous"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        ViewMatchers.isDisplayed()));
        materialButton2.perform(ViewActions.click());
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
