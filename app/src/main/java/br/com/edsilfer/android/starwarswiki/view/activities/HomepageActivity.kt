package br.com.edsilfer.android.starwarswiki.view.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.commons.Router.REQUEST_QRCODE_READER
import br.com.edsilfer.android.starwarswiki.commons.Router.launchGitHubLink
import br.com.edsilfer.android.starwarswiki.databinding.ActivityHomepageBinding
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.infrastructure.dagger.Injector
import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.edsilfer.android.starwarswiki.presenter.HomepagePresenter.Companion.REQUEST_PERMISSION_CAMERA
import br.com.edsilfer.android.starwarswiki.presenter.QRCodeScannerPresenter.Companion.ARG_RESULT_URL
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.HomepagePresenterContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.HomepageViewContract
import br.com.edsilfer.android.starwarswiki.view.adapter.CharacterAdapter
import br.com.edsilfer.android.starwarswiki.view.dialogs.FancyLoadingDialog
import br.com.edsilfer.kotlin_support.extensions.showErrorPopUp
import br.com.edsilfer.kotlin_support.extensions.showInputDialog
import br.com.edsilfer.kotlin_support.extensions.showUnderConstructionPopUp
import br.com.tyllt.infrastructure.database.CharacterDAO
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.rsc_homepage_main_content.*
import javax.inject.Inject


/**
 * Created by ferna on 2/18/2017.
 */

class HomepageActivity : BaseActivity(), HomepageViewContract, NavigationView.OnNavigationItemSelectedListener {

    private val TAG = HomepageActivity::class.simpleName

    @Inject
    lateinit var mPostman: Postman

    @Inject
    lateinit var mPresenter: HomepagePresenterContract

    override fun getContext() = this
    override fun getPresenter() = mPresenter as BasePresenter
    private var mDialog: FancyLoadingDialog? = null
    private var mIsStateAlreadySaved = false
    private var mPendingShowDialog = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        Injector.getInstance().inject(this)
        initDataBinding()
        initToolbar()
        loadCachedCharacter()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        mIsStateAlreadySaved = false
        if (mPendingShowDialog) {
            mPendingShowDialog = false
            showLoading()
        }
    }

    override fun onPause() {
        super.onPause()
        mIsStateAlreadySaved = true;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_QRCODE_READER -> mPresenter.onQRCodeRead(data.getStringExtra(ARG_RESULT_URL))
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA -> mPresenter.onCameraPermissionGranted()
        }
    }

    private fun initDataBinding() {
        val binding: ActivityHomepageBinding = DataBindingUtil.setContentView(this, R.layout.activity_homepage)
        binding.presenter = mPresenter
    }

    override fun showGetUrlPopUp() {
        showInputDialog(
                title = "Search Character",
                noInputs = 1,
                hint1 = "http://swapi.co/api/people/?",
                onAccept = {
                    values ->
                    mPresenter.onUrlType(values[0])
                }
        )
    }

    private fun initToolbar() {
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        initDrawerMenu()
    }

    private fun initDrawerMenu() {
        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        val headerLayout = navigationView.getHeaderView(0)
        val forkMe = headerLayout.findViewById(R.id.fork_me)
        forkMe.setOnClickListener {
            launchGitHubLink(this)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.rate) {
            launchGitHubLink(this)
        } else if (id == R.id.report_bug) {
            showUnderConstructionPopUp()
        } else if (id == R.id.about) {
            LibsBuilder()
                    .withActivityTitle(getString(R.string.app_name))
                    .withAboutIconShown(true)
                    .withAboutVersionShown(true)
                    .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                    .start(this)
        } else if (id == R.id.exit) {
            finish()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /*
    CONTRACT IMPLEMENTATION
     */
    override fun showErrorMessage(message: Int) {
        hideLoading()
        showErrorPopUp(message)
    }

    override fun addCharacter(character: Character) {
        runOnUiThread {
            val adapter = characters.adapter as CharacterAdapter
            adapter.addItem(character)
            val charactersObjects = adapter.mData
            characters.visibility = RecyclerView.VISIBLE
            collection_loading_feedback.showFeedback(characters, charactersObjects)
        }
    }

    override fun removeCharacter(character: Character) {
        val adapter = characters.adapter as CharacterAdapter
        adapter.removeItem(character)
    }

    override fun loadCachedCharacter() {
        runOnUiThread {
            val charactersObjects = CharacterDAO.list().toMutableList()
            characters.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            characters.adapter = CharacterAdapter(this, charactersObjects)
            collection_loading_feedback.showFeedback(characters, charactersObjects)
        }
    }

    override fun showLoading() {
        runOnUiThread {
            if (mIsStateAlreadySaved) {
                mPendingShowDialog = true
            } else {
                mDialog = FancyLoadingDialog()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.add(android.R.id.content, mDialog).addToBackStack(null).commit()
            }
        }
    }

    override fun hideLoading() {
        mDialog?.dismiss()
    }

    override fun onCharacterClick(character: Character) {
        mPresenter.onCharacterClick(character)
    }

    override fun deleteCharacter(character: Character) {
        mPresenter.deleteCharacter(character)
    }

}
