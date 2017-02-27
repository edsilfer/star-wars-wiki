package br.com.edsilfer.android.starwarswiki.view.activities

import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.IntegerRes
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.tyllt.view.contracts.BaseView


/**
 * Contains common methods for all application's activities
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {

    private val TAG = BaseActivity::class.simpleName

    public override fun onStart() {
        super.onStart()
        getPresenter()?.setView(this)
    }

    public override fun onStop() {
        super.onStop()
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
