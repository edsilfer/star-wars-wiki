package br.com.edsilfer.android.starwarswiki.view.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.edsilfer.android.starwarswiki.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void splashActivityTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withClassName(is("com.github.clans.fab.FloatingActionButton")),
                        withParent(withId(R.id.fab_menu)),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.search_by_url),
                        withParent(withId(R.id.fab_menu)),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input1), isDisplayed()));
        appCompatEditText.perform(replaceText("http://swapi.co/api/people/1"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.okay), withText("OKAY"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.characters), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.toolbar_wrapper)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

    }

}
