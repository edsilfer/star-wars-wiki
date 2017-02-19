package br.com.edsilfer.android.starwarswiki.presenter

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.edsilfer.android.starwarswiki.commons.Router.launchQRCodeScanner
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.model.ResponseWrapper
import br.com.edsilfer.android.starwarswiki.model.enum.EventCatalog
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.HomepagePresenterContract
import br.com.edsilfer.android.starwarswiki.view.contracts.HomepageViewContract
import br.com.edsilfer.kotlin_support.model.Events
import br.com.edsilfer.kotlin_support.model.ISubscriber
import br.com.edsilfer.kotlin_support.service.NotificationCenter.RegistrationManager.registerForEvent
import br.com.edsilfer.kotlin_support.service.NotificationCenter.RegistrationManager.unregisterForEvent
import br.com.tyllt.view.contracts.BaseView


/**
 * Created by ferna on 2/18/2017.
 */

class HomepagePresenter(val mPostman: Postman) : HomepagePresenterContract, BasePresenter(), ISubscriber {


    private val TAG = HomepagePresenter::class.simpleName

    override fun hasEvents() = true
    private lateinit var mContext: AppCompatActivity
    private lateinit var mView: HomepageViewContract

    override fun takeView(_view: BaseView) {
        mView = _view as HomepageViewContract
        mContext = _view.getContext()
        registerForEvent(EventCatalog.e001, this)
    }

    override fun dropView() {
        super.dropView()
        unregisterForEvent(EventCatalog.e001, this)
    }

    /*
    PRESENTER BUSINESS IMPLEMENTATION
     */
    override fun onAddCharacterClick(view: View) {
        launchQRCodeScanner(mContext)
    }

    override fun onQRCodeRead(url: String) {
        Log.i(TAG, "Read QR Code content is $url.")
        mPostman.readUrl(url)
    }

    /*
    NETWORK HANDLING
     */
    override fun onEventTriggered(event: Events, payload: Any?) {
        if (payload != null) {
            val wrapper = payload as ResponseWrapper
            if (wrapper.success) {
                Log.i(TAG, "Received payload response: ${wrapper.character}")
            }
        }
    }
}
