package br.com.edsilfer.android.starwarswiki.infrastructure;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import io.realm.Realm;

/**
 * Adds the ability to trace current activity on Foreground during the test.
 *
 * Created by F1sherKK on 14/04/16.
 */
public class TestApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        Realm.init(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }
}