package br.com.edsilfer.android.starwarswiki.view.activity

import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.IntegerRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.tyllt.view.contracts.BaseView


/**
 * Created by efernandes on 29/12/16.
 */

abstract class BaseActivity : AppCompatActivity(), BaseView {

    public override fun onStart() {
        super.onStart()
        Log.i("DROID-001", "class ${this.javaClass.name} adding event")
        getPresenter()?.setView(this)
    }

    public override fun onStop() {
        super.onStop()
        Log.i("DROID-001", "class ${this.javaClass.name} removing network event")
        getPresenter()?.dropView()
    }

    open fun getPresenter(): BasePresenter? {
        return null
    }

    fun dimen(@DimenRes resId: Int): Int {
        return resources.getDimension(resId).toInt()
    }

    fun color(@ColorRes resId: Int): Int {
        return resources.getColor(resId)
    }

    fun integer(@IntegerRes resId: Int): Int {
        return resources.getInteger(resId)
    }
}
