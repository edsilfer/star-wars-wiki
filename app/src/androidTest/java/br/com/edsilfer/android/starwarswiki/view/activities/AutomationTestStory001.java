package br.com.edsilfer.android.starwarswiki.view.activities;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.edsilfer.android.starwarswiki.R;
import br.com.edsilfer.android.starwarswiki.infrastructure.ConditionWatcher;
import br.com.edsilfer.android.starwarswiki.infrastructure.Instruction;
import br.com.edsilfer.android.starwarswiki.infrastructure.rules.PermissionsRule;
import br.com.edsilfer.android.starwarswiki.infrastructure.TestApplication;
import br.com.edsilfer.android.starwarswiki.view.viewholder.CharacterViewHolder;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Checks.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AutomationTestStory001 {

    @Rule
    public final PermissionsRule permissionsRule = new PermissionsRule(new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION
    });

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void automationTestStory001() throws Exception {
        ConditionWatcher.waitForCondition(new WaitSplashScreen());
        openSearchCharacterByUrl();
        typeCharacterURL();
        toggleFABMenuState();
        ConditionWatcher.waitForCondition(new WaitLoadingDialog());
        onView(withId(R.id.characters)).check(matches(atPosition(0, withText("Luke Skywalker"))));
        showMoviePosters();
        sleep(1000);
        showCharacterOptions();
        sleep(1000);
        deleteCharacter();
    }

    /*
    FIXME: TEST HANGING WHEN FILM ACTIVITY GETS OPEN
     */
    private void showMoviePosters() throws InterruptedException {
       /* onView(allOf(withId(R.id.characters), isDisplayed())).perform(actionOnItemAtPosition(0, click()));
        pressBack();*/
    }

    private void openSearchCharacterByUrl() throws InterruptedException {
        ViewInteraction floatingActionButton = onView(
                allOf(withClassName(is("com.github.clans.fab.FloatingActionButton")),
                        withParent(withId(R.id.fab_menu)),
                        isDisplayed()));
        floatingActionButton.perform(click());

        sleep(1000);

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.search_by_url),
                        withParent(withId(R.id.fab_menu)),
                        isDisplayed()));
        floatingActionButton2.perform(click());
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

    private void typeCharacterURL() throws InterruptedException {
        onView(allOf(withId(R.id.input1), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.input1), isDisplayed())).perform(replaceText("http://swapi.co/api/people/1"), closeSoftKeyboard());
        sleep(1000);
        onView(allOf(withId(R.id.okay), isDisplayed())).perform(click());
    }

    private void toggleFABMenuState() {
        onView(withId(R.id.fab_menu)).perform(click());
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                CharacterViewHolder viewHolder = (CharacterViewHolder) view.findViewHolderForAdapterPosition(position);
                return viewHolder != null && itemMatcher.matches(viewHolder.getName());
            }
        };
    }

    /*
    INSTRUCTIONS
     */
    public class WaitSplashScreen extends Instruction {
        @Override
        public String getDescription() {
            return "Splash Screen shouldn't be in view hierarchy";
        }

        @Override
        public boolean checkCondition() {
            Activity activity = ((TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
            return activity != null && activity.findViewById(R.id.splash_logo) == null;
        }
    }

    public class WaitLoadingDialog extends Instruction {
        @Override
        public String getDescription() {
            return "Fancy Loading Dialog shouldn't be in view hierarchy";
        }

        @Override
        public boolean checkCondition() {
            Activity activity = ((TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
            View root = activity.findViewById(R.id.characters);
            return root.findViewById(R.id.wrapper) != null;
        }
    }
}
