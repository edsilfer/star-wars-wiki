package br.com.edsilfer.android.starwarswiki.presenter

import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.model.ResponseWrapper
import br.com.edsilfer.android.starwarswiki.model.dictionary.MovieDictionary
import br.com.edsilfer.android.starwarswiki.model.dictionary.TMDBWrapperResponseDictionary
import br.com.edsilfer.android.starwarswiki.model.enum.EventCatalog
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.FilmsActivityPresenterContract
import br.com.edsilfer.android.starwarswiki.presenter.contracts.FilmsFragmentPresenterContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.FilmsActivityViewContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.FilmsFragmentViewContract
import br.com.edsilfer.kotlin_support.model.Events
import br.com.edsilfer.kotlin_support.model.ISubscriber
import br.com.edsilfer.kotlin_support.service.NotificationCenter
import br.com.edsilfer.kotlin_support.service.NotificationCenter.RegistrationManager.registerForEvent
import br.com.edsilfer.kotlin_support.service.NotificationCenter.RegistrationManager.unregisterForEvent
import br.com.tyllt.view.contracts.BaseView


/**
 * Created by ferna on 2/18/2017.
 */
class FilmsFragmentPresenter(val mPostman: Postman) : FilmsFragmentPresenterContract, BasePresenter() {

    private val TAG = FilmsFragmentPresenter::class.simpleName

    override fun hasEvents() = true
    private lateinit var mContext: AppCompatActivity
    private lateinit var mView: FilmsFragmentViewContract

    override fun takeView(_view: BaseView) {
        mView = _view as FilmsFragmentViewContract
        mContext = _view.getContext()
    }

    override fun dropView() {
        super.dropView()
    }
}
