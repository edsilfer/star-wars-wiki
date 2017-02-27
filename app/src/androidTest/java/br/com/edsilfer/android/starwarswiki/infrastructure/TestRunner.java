package br.com.edsilfer.android.starwarswiki.infrastructure;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Class that redirects application flows to add TestApplication as the main Application class
 * <p>
 * Credits to F1sherKK on 14/04/16. https://github.com/AzimoLabs/ConditionWatcher
 */
public class TestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, TestApplication.class.getName(), context);
    }
}