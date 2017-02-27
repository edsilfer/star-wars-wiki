package br.com.edsilfer.android.starwarswiki.presenter

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.QRCodeScannerPresenterContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.QRCodeScannerViewContract
import br.com.tyllt.view.contracts.BaseView


/**
 * Presenter Layer for QR Code scanner
 */
open class QRCodeScannerPresenter(val mPostman: Postman) : QRCodeScannerPresenterContract, BasePresenter() {

    companion object {
        val ARG_RESULT_URL = "ARG_RESULT_URL"
    }

    override fun hasEvents() = true
    protected lateinit var mContext: AppCompatActivity
    protected lateinit var mView: QRCodeScannerViewContract

    override fun takeView(_view: BaseView) {
        mView = _view as QRCodeScannerViewContract
        mContext = _view.getContext()
    }

    /*
    PRESENTER BUSINESS IMPLEMENTATION
     */
    override fun onQRCodeScanResult(url: String) {
        val intent = Intent()
        intent.putExtra(ARG_RESULT_URL, url)
        mContext.setResult(Activity.RESULT_OK, intent)
        mContext.finish()
    }
}
