package br.com.edsilfer.android.starwarswiki.infrastructure.rules;

import android.os.Build;
import android.support.test.InstrumentationRegistry;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Adds the ability to grant permission before test starts
 *
 * Credits to http://stackoverflow.com/a/41637477/2068693
 */
public class PermissionsRule implements TestRule {

    private final String[] permissions;

    public PermissionsRule(String[] permissions) {
        this.permissions = permissions;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                allowPermissions();

                base.evaluate();

                revokePermissions();
            }
        };
    }

    private void allowPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand(
                        "pm grant " + InstrumentationRegistry.getTargetContext().getPackageName()
                                + " " + permission);
            }
        }
    }

    private void revokePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand(
                        "pm revoke " + InstrumentationRegistry.getTargetContext().getPackageName()
                                + " " + permission);
            }
        }
    }
}