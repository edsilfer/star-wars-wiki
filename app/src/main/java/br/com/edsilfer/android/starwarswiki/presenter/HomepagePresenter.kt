package br.com.edsilfer.android.starwarswiki.presenter

import android.Manifest
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.commons.Router
import br.com.edsilfer.android.starwarswiki.commons.Router.launchGitHubLink
import br.com.edsilfer.android.starwarswiki.commons.Router.launchQRCodeScanner
import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.edsilfer.android.starwarswiki.model.ResponseWrapper
import br.com.edsilfer.android.starwarswiki.model.enum.EventCatalog
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.HomepagePresenterContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.HomepageViewContract
import br.com.edsilfer.kotlin_support.extensions.checkPermission
import br.com.edsilfer.kotlin_support.model.Events
import br.com.edsilfer.kotlin_support.model.ISubscriber
import br.com.edsilfer.kotlin_support.service.NotificationCenter.RegistrationManager.registerForEvent
import br.com.edsilfer.kotlin_support.service.NotificationCenter.RegistrationManager.unregisterForEvent
import br.com.edsilfer.android.starwarswiki.infrastructure.database.CharacterDAO
import br.com.tyllt.view.contracts.BaseView
import java.util.*


/**
 * Created by ferna on 2/18/2017.
 */
class HomepagePresenter(val mPostman: Postman) : HomepagePresenterContract, BasePresenter(), ISubscriber {

    companion object {
        val REQUEST_PERMISSION_CAMERA = 98
    }

    private val TAG = HomepagePresenter::class.simpleName

    override fun hasEvents() = true
    private lateinit var mContext: AppCompatActivity
    private lateinit var mView: HomepageViewContract
    private var mCharacter: Character? = null

    override fun takeView(_view: BaseView) {
        mView = _view as HomepageViewContract
        mContext = _view.getContext()
        registerForEvent(EventCatalog.e001, this)
        registerForEvent(EventCatalog.e002, this)
    }

    override fun dropView() {
        super.dropView()
        unregisterForEvent(EventCatalog.e001, this)
        unregisterForEvent(EventCatalog.e002, this)
    }

    /*
    PRESENTER BUSINESS IMPLEMENTATION
     */
    override fun searchByQrcode(view: View) {
        if (mContext.checkPermission(Manifest.permission.CAMERA)) {
            launchQRCodeScanner(mContext)
        } else {
            mContext.runOnUiThread {
                ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION_CAMERA)
            }
        }
    }

    override fun searchByUrl(view: View) {
        mView.showGetUrlPopUp()
    }

    override fun onUrlType(url: String) {
        onQRCodeRead(url)
    }

    override fun onQRCodeRead(url: String) {
        Log.i(TAG, "Read QR Code content is $url.")
        mView.showLoading()
        mPostman.searchCharacter(url)
    }

    override fun onCameraPermissionGranted() {
        launchQRCodeScanner(mContext)
    }

    override fun onForkMeClick() {
        launchGitHubLink(mContext)
    }

    override fun onCharacterClick(character: Character) {
        Router.launchFilmsActivity(mContext, character.id)
    }

    override fun deleteCharacter(character: Character) {
        CharacterDAO.delete(character.id)
        mView.removeCharacter(character)
    }

    /*
    NETWORK HANDLING
     */
    override fun onEventTriggered(event: Events, payload: Any?) {
        val wrapper = payload as ResponseWrapper
        if (wrapper.success) {
            when (event) {
                EventCatalog.e001 -> handleCharacterRead(wrapper)
            }
        } else {
            mView.hideLoading()
        }
    }

    private fun handleCharacterRead(wrapper: ResponseWrapper) {
        mView.hideLoading()
        if (wrapper.payload != null) {
            val character = wrapper.payload as Character
            if (!doesCharacterHasAlreadyBeenScanned(character)) {
                Log.i(TAG, "Received complete character: $mCharacter")
                CharacterDAO.create(character)
                mView.addCharacter(character)
            }
        }
    }

    private fun doesCharacterHasAlreadyBeenScanned(character: Character): Boolean {
        val chars = CharacterDAO.list()
        for (c in chars) {
            if (c.url == character.url) {
                return true
            }
        }
        return false
    }

}
