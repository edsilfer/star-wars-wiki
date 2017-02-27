package br.com.edsilfer.android.starwarswiki.infrastructure

import android.app.Application
import android.content.Context
import io.realm.Realm

/**
 * Created by ferna on 2/18/2017.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}
