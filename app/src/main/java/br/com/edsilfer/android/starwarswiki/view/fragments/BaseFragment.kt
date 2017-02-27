package br.com.edsilfer.android.starwarswiki.view.fragments

import android.support.v4.app.Fragment
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.tyllt.view.contracts.BaseView

abstract class BaseFragment : Fragment(), BaseView {

    private val TAG = BaseFragment::class.simpleName

    override fun onStart() {
        super.onStart()
        getPresenter()?.setView(this)
    }

    override fun onStop() {
        super.onStop()
        getPresenter()?.dropView()
    }

    open fun getPresenter(): BasePresenter? {
        return null
    }

}

