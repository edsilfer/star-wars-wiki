package br.com.edsilfer.android.starwarswiki.view.fragments

import android.support.v4.app.Fragment
import android.util.Log
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.tyllt.view.contracts.BaseView

abstract class BaseFragment : Fragment(), BaseView {

    override fun onStart() {
        super.onStart()
        Log.i("DROID-001", "class ${this.javaClass.name} adding event")
        getPresenter()?.setView(this)
    }

    override fun onStop() {
        super.onStop()
        Log.i("DROID-001", "class ${this.javaClass.name} removing network event")
        getPresenter()?.dropView()
    }

    open fun getPresenter(): BasePresenter? {
        return null
    }

}

