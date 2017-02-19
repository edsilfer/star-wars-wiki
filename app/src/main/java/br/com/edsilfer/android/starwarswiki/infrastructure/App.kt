package br.com.edsilfer.android.starwarswiki.infrastructure

import android.app.Application
import android.content.Context
import io.realm.Realm

/**
 * Created by ferna on 2/18/2017.
 */

class App : Application() {

    companion object {
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
    }

}
