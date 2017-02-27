package br.com.edsilfer.android.starwarswiki.util;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import br.com.edsilfer.android.starwarswiki.infrastructure.Postman;

/**
 * REQUIRED CLASS DUE TO DO MOCKITO NOTHING BUG
 */
public class MockedPostman extends Postman {
    @Override
    public void searchCharacter(@NotNull Context context, @NotNull String url) {
        super.searchCharacter(context, url);
    }
}