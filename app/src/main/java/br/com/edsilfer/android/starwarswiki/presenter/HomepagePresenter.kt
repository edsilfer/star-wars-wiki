package br.com.edsilfer.android.starwarswiki.presenter

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.edsilfer.android.starwarswiki.commons.Router.launchQRCodeScanner
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.HomepagePresenterContract
import br.com.edsilfer.android.starwarswiki.view.QRCodeScannerActivity
import br.com.edsilfer.android.starwarswiki.view.contracts.HomepageViewContract
import br.com.tyllt.view.contracts.BaseView


/**
 * Created by ferna on 2/18/2017.
 */

class HomepagePresenter(val mPostman: Postman) : HomepagePresenterContract, BasePresenter() {

    private val TAG = HomepagePresenter::class.simpleName

    override fun hasEvents() = true
    private lateinit var mContext: AppCompatActivity
    private lateinit var mView: HomepageViewContract

    override fun takeView(_view: BaseView) {
        mView = _view as HomepageViewContract
        mContext = _view.getContext()
    }

    /*
    PRESENTER BUSINESS IMPLEMENTATION
     */
    override fun onAddCharacterClick(view: View) {
        launchQRCodeScanner(mContext)
    }

    override fun onQRCodeRead(url: String) {
        Log.i(TAG, "Read QR Code content is $url")
    }
}
