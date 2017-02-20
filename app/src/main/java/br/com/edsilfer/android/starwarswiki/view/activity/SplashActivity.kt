package br.com.edsilfer.android.starwarswiki.view.activity

import android.os.Bundle
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.commons.Router.launchHomepageActivity
import br.com.edsilfer.kotlin_support.extensions.paintStatusBar
import br.com.tyllt.view.contracts.BaseView
import org.jetbrains.anko.doAsync

/**
 * Created by ferna on 2/18/2017.
 */

class SplashActivity : BaseActivity(), BaseView {
    override fun getContext() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paintStatusBar(android.R.color.transparent, true)
        setContentView(R.layout.activity_splash)

        doAsync {
            Thread.sleep(800)
            runOnUiThread {
                launchHomepageActivity(this@SplashActivity)
                finish()
            }
        }
    }
}
