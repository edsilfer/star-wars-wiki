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
 * Created by ferna on 2/18/2017.
 */
class FilmsActivityPresenter(val mPostman: Postman) : FilmsActivityPresenterContract, BasePresenter(), ISubscriber {

    override fun hasEvents() = true
    private lateinit var mContext: AppCompatActivity
    private lateinit var mView: FilmsActivityViewContract

    override fun takeView(_view: BaseView) {
        mView = _view as FilmsActivityViewContract
        mContext = _view.getContext()
    }

    override fun onEventTriggered(event: Events, payload: Any?) {
    }
}
