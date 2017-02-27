package br.com.edsilfer.android.starwarswiki.view.activities;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.edsilfer.android.starwarswiki.R;
import br.com.edsilfer.android.starwarswiki.util.Utils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AutomationTestStory001 {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void automationTestStory001() {
        toggleFABMenuState();
        openSearchCharacterByURLDialog();
        grantCameraAndAccessCoarseLocationPermissions();
        typeCharacterURL();
        toggleFABMenuState();
        showCharacterOptions();
        deleteCharacter();
    }

    private void showCharacterOptions() {
        onView(allOf(withId(R.id.wrapper), withParent(withId(R.id.characters)), isDisplayed())).perform(longClick());
    }

    private void deleteCharacter() {
        onView(allOf(withId(R.id.md_contentRecyclerView),
                withParent(withId(R.id.md_contentListViewFrame)),
                isDisplayed())
        ).perform(actionOnItemAtPosition(0, click()));
    }

    private void typeCharacterURL() {
        onView(withId(R.id.input1)).perform(click());
        onView(withId(R.id.input1)).perform(replaceText("http://swapi.co/api/people/1"), closeSoftKeyboard());
        onView(withId(R.id.okay)).perform(click());
    }

    private void grantCameraAndAccessCoarseLocationPermissions() {
        Utils.allowPermissionsIfNeeded("android.permission.CAMERA");
        Utils.allowPermissionsIfNeeded("android.permission.ACCESS_COARSE_LOCATION");
    }

    private void openSearchCharacterByURLDialog() {
        onView(allOf(withClassName(is("com.github.clans.fab.FloatingActionButton")),
                withParent(withId(R.id.fab_menu)),
                isDisplayed())).perform(click());
    }

    private void toggleFABMenuState() {
        onView(withId(R.id.fab_menu)).perform(click());
    }

}
