package br.com.edsilfer.android.starwarswiki.view.activities

import android.os.Bundle
import android.util.Log
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.commons.Router.ARG_CHARACTER_ID
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.infrastructure.dagger.Injector
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.FilmsActivityPresenterContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.FilmsActivityViewContract
import br.com.edsilfer.android.starwarswiki.view.adapter.FilmsAdapter
import br.com.edsilfer.android.starwarswiki.infrastructure.database.CharacterDAO
import kotlinx.android.synthetic.main.activity_film.*
import javax.inject.Inject


/**
 * Created by ferna on 2/18/2017.
 */

class FilmsActivity : BaseActivity(), FilmsActivityViewContract {

    private val TAG = FilmsActivity::class.simpleName

    @Inject
    lateinit var mPostman: Postman

    @Inject
    lateinit var mPresenter: FilmsActivityPresenterContract

    override fun getContext() = this
    override fun getPresenter() = mPresenter as BasePresenter
    private var mCharacter = br.com.edsilfer.android.starwarswiki.model.Character()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)
        Injector.getInstance().inject(this)
        initToolbar()
        retrieveUrls()
        initViewPager()

        latitude.text = "lat: ${mCharacter.latitude}"
        longitude.text = "lon: ${mCharacter.longitude}"
    }

    private fun retrieveUrls() {
        try {
            mCharacter = CharacterDAO.read(intent.extras.getLong(ARG_CHARACTER_ID))!!
        } catch (e: Exception) {
            throw IllegalArgumentException("FilmsActivity requests a Character object")
        }
    }

    private fun initToolbar() {
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    private fun initViewPager() {
        films.adapter = FilmsAdapter(supportFragmentManager, mCharacter.films)
    }
}
