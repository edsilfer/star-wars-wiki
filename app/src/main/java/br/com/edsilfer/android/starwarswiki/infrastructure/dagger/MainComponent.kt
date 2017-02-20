package br.com.edsilfer.android.starwarswiki.infrastructure.dagger

import br.com.edsilfer.android.starwarswiki.view.activity.HomepageActivity
import br.com.edsilfer.android.starwarswiki.view.activity.QRCodeScannerActivity
import br.com.edsilfer.android.starwarswiki.view.activity.SplashActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by edgar on 09-May-16.
 */
@Singleton
@Component(modules = arrayOf(MainModule::class))
interface MainComponent {

    fun inject(splashActivity: SplashActivity)

    fun inject(homepageActivity: HomepageActivity)

    fun inject(qrCodeScannerActivity: QRCodeScannerActivity)

}



