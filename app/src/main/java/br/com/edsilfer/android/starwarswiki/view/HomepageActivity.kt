package br.com.edsilfer.android.starwarswiki.view

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.commons.Router.REQUEST_QRCODE_READER
import br.com.edsilfer.android.starwarswiki.databinding.ActivityHomepageBinding
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.infrastructure.dagger.Injector
import br.com.edsilfer.android.starwarswiki.presenter.QRCodeScannerPresenter.Companion.ARG_RESULT_URL
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.HomepagePresenterContract
import br.com.edsilfer.android.starwarswiki.view.contracts.HomepageViewContract
import br.com.edsilfer.kotlin_support.extensions.paintStatusBar
import br.com.edsilfer.kotlin_support.extensions.showErrorPopUp
import javax.inject.Inject


/**
 * Created by ferna on 2/18/2017.
 */

class HomepageActivity : BaseActivity(), HomepageViewContract {

    @Inject
    lateinit var mPostman: Postman

    @Inject
    lateinit var mPresenter: HomepagePresenterContract

    override fun getContext() = this
    override fun getPresenter() = mPresenter as BasePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paintStatusBar(android.R.color.transparent, true)
        setContentView(R.layout.activity_homepage)
        Injector.getInstance().inject(this)
        initDataBinding()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_QRCODE_READER -> mPresenter.onQRCodeRead(data.getStringExtra(ARG_RESULT_URL))
            }
        }
    }

    private fun initDataBinding() {
        val binding: ActivityHomepageBinding = DataBindingUtil.setContentView(this, R.layout.activity_homepage)
        binding.presenter = mPresenter
    }

    /*
    CONTRACT IMPLEMENTATION
     */
    override fun showErrorMessage(message: Int) = showErrorPopUp(message)

}
