package br.com.edsilfer.android.starwarswiki.presenter

import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.FilmsActivityPresenterContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.FilmsActivityViewContract
import br.com.edsilfer.kotlin_support.model.Events
import br.com.edsilfer.kotlin_support.model.ISubscriber
import br.com.tyllt.view.contracts.BaseView


/**
 * Presenter layer for FilmsActivity
 */
open class FilmsActivityPresenter(val mPostman: Postman) : FilmsActivityPresenterContract, BasePresenter(), ISubscriber {

    override fun hasEvents() = true
    protected lateinit var mContext: AppCompatActivity
    protected lateinit var mView: FilmsActivityViewContract

    override fun takeView(_view: BaseView) {
        mView = _view as FilmsActivityViewContract
        mContext = _view.getContext()
    }

    override fun onEventTriggered(event: Events, payload: Any?) {
    }
}
