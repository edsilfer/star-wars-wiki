package br.com.edsilfer.android.starwarswiki.presenter

import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.FilmsFragmentPresenterContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.FilmsFragmentViewContract
import br.com.tyllt.view.contracts.BaseView


/**
 * Created by ferna on 2/18/2017.
 */
open class FilmsFragmentPresenter(val mPostman: Postman) : FilmsFragmentPresenterContract, BasePresenter() {

    private val TAG = FilmsFragmentPresenter::class.simpleName

    override fun hasEvents() = true
    protected lateinit var mContext: AppCompatActivity
    protected  lateinit var mView: FilmsFragmentViewContract

    override fun takeView(_view: BaseView) {
        mView = _view as FilmsFragmentViewContract
        mContext = _view.getContext()
    }

    override fun dropView() {
        super.dropView()
    }
}
