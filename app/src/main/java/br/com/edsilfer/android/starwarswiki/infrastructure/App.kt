package br.com.edsilfer.android.starwarswiki.infrastructure

import android.app.Application
import android.content.Context
import br.com.edsilfer.android.searchimages.infrastructure.ConfigurationManager
import br.com.edsilfer.android.starwarswiki.commons.util.Utils
import io.realm.Realm

/**
 * Created by ferna on 2/18/2017.
 */

class App : Application() {

    companion object {
        private const val ARG_APPLICATION_KEY = "gcs.application.key"
        private const val ARG_API_ID = "gcs.api.key"

        private var mApp: App? = null
        fun getContext(): Context {
            if (mApp == null) {
                return Application()
            }
            return mApp!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApp = this
        Realm.init(this)

        /*
       Prepares postman to from Google Custom Search API
        */
        ConfigurationManager.setAPIKey(Utils.readProperty(ARG_API_ID))
        ConfigurationManager.setApplicationKey(Utils.readProperty(ARG_APPLICATION_KEY))
    }

}
