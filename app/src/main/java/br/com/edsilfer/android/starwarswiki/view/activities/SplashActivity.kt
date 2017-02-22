package br.com.edsilfer.android.starwarswiki.view.activities

import android.os.Bundle
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.commons.Router.launchHomepageActivity
import br.com.edsilfer.android.starwarswiki.commons.util.Utils
import br.com.edsilfer.android.starwarswiki.infrastructure.dagger.Injector
import br.com.edsilfer.kotlin_support.extensions.paintStatusBar
import br.com.tyllt.view.contracts.BaseView
import org.jetbrains.anko.doAsync
import javax.inject.Inject

/**
 * Created by ferna on 2/18/2017.
 */

class SplashActivity : BaseActivity(), BaseView {

    companion object {
        private const val ARG_SPLASH_ENTRY_TIME = 800.toLong()
    }

    override fun getContext() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paintStatusBar(android.R.color.black, true)
        setContentView(R.layout.activity_splash)

        doAsync {
            Thread.sleep(ARG_SPLASH_ENTRY_TIME)
            runOnUiThread {
                launchHomepageActivity(this@SplashActivity)
                finish()
            }
        }
    }
}
