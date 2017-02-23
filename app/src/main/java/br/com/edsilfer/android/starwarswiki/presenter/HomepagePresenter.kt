package br.com.edsilfer.android.starwarswiki.presenter

import android.Manifest
import android.content.Context
import android.os.Bundle
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
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import java.util.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import br.com.edsilfer.kotlin_support.extensions.showErrorPopUp


/**
 * Created by ferna on 2/18/2017.
 */
class HomepagePresenter(val mPostman: Postman) :
        HomepagePresenterContract,
        BasePresenter(),
        ISubscriber,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    companion object {
        val REQUEST_PERMISSION_CAMERA = 98
    }

    private val TAG = HomepagePresenter::class.simpleName

    override fun hasEvents() = true
    private lateinit var mContext: AppCompatActivity
    private lateinit var mView: HomepageViewContract
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLastLocation = Pair(0.toDouble(), 0.toDouble())

    override fun takeView(_view: BaseView) {
        mView = _view as HomepageViewContract
        mContext = _view.getContext()
        registerForEvent(EventCatalog.e001, this)
        registerForEvent(EventCatalog.e002, this)
        createGoogleAPIClient()
        mGoogleApiClient?.connect()
    }

    override fun dropView() {
        super.dropView()
        unregisterForEvent(EventCatalog.e001, this)
        unregisterForEvent(EventCatalog.e002, this)
    }

    /*
    PRESENTER BUSINESS IMPLEMENTATION
     */
    override fun searchByQrCode(view: View) {
        if (mContext.checkPermission(Manifest.permission.CAMERA) && mContext.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            launchQRCodeScanner(mContext)
        } else {
            mContext.runOnUiThread {
                ActivityCompat.requestPermissions(mContext, arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_PERMISSION_CAMERA)
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
        mContext.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        mPostman.searchCharacter(url)
    }

    override fun onPermissionsGranted() {
        onConnected(null)
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
                character.latitude = mLastLocation.first.toString()
                character.longitude = mLastLocation.second.toString()
                CharacterDAO.create(character)
                mView.addCharacter(character)
            } else {
                mContext.showErrorPopUp(R.string.str_error_already_already_exists)
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

    /*
    GOOGLE LOCATIONS SERVICES
     */
    private fun createGoogleAPIClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
        }
    }

    override fun onConnected(p0: Bundle?) {
        run {
            val location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient!!)
            if (location != null) {
                mLastLocation = Pair(location.latitude, location.longitude)
            }
        }
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

}
