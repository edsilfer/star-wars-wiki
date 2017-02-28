package br.com.edsilfer.android.starwarswiki.infrastructure;

import android.os.Bundle;

/**
 * Sets a logic to decide if test should keep waiting or not
 *
 * Credits to F1sherKK on 14/04/16. https://github.com/AzimoLabs/ConditionWatcher
 */
public abstract class Instruction {

    private Bundle dataContainer = new Bundle();

    public final void setData(Bundle dataContainer) {
        this.dataContainer = dataContainer;
    }

    public final Bundle getDataContainer() {
        return dataContainer;
    }

    public abstract String getDescription();

    public abstract boolean checkCondition();
}