package br.com.edsilfer.android.starwarswiki.view.activities

import android.os.Bundle
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.commons.Router.ARG_CHARACTER_ID
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.infrastructure.dagger.Injector
import br.com.edsilfer.android.starwarswiki.infrastructure.database.CharacterDAO
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.FilmsActivityPresenterContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.FilmsActivityViewContract
import br.com.edsilfer.android.starwarswiki.view.adapter.FilmsAdapter
import kotlinx.android.synthetic.main.activity_film.*
import javax.inject.Inject


/**
 * View layer for FilmsActivity
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
        retrieveCharacter()
        initViewPager()
        setLatLonLabels()
    }

    private fun setLatLonLabels() {
        latitude.text = getString(R.string.str_commons_latitude, mCharacter.latitude)
        longitude.text = getString(R.string.str_commons_longitude, mCharacter.longitude)
    }

    private fun retrieveCharacter() {
        try {
            mCharacter = CharacterDAO.read(intent.extras.getLong(ARG_CHARACTER_ID))!!
        } catch (e: Exception) {
            throw IllegalArgumentException(getString(R.string.str_error_invalid_character_object))
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
