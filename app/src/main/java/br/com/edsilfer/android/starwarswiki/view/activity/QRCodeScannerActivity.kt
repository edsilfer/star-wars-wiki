package br.com.edsilfer.android.starwarswiki.view.activity

import android.os.Bundle
import br.com.edsilfer.android.starwarswiki.infrastructure.dagger.Injector
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.QRCodeScannerPresenterContract
import br.com.edsilfer.android.starwarswiki.view.activity.contracts.QRCodeScannerViewContract
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import javax.inject.Inject

/**
 * Created by ferna on 2/19/2017.
 */

class QRCodeScannerActivity : BaseActivity(), QRCodeScannerViewContract, ZXingScannerView.ResultHandler {

    private val TAG = QRCodeScannerActivity::class.java.simpleName

    @Inject
    lateinit var mPresenter: QRCodeScannerPresenterContract
    private lateinit var mScannerView: ZXingScannerView

    override fun getContext() = this
    override fun getPresenter() = mPresenter as BasePresenter

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        Injector.getInstance().inject(this)
        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)
    }

    public override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        mPresenter.onQRCodeScanResult(rawResult.text)
    }

}
