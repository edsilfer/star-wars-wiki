package br.com.edsilfer.android.starwarswiki.infrastructure.dagger

import br.com.edsilfer.android.starwarswiki.view.activities.FilmsActivity
import br.com.edsilfer.android.starwarswiki.view.activities.HomepageActivity
import br.com.edsilfer.android.starwarswiki.view.activities.QRCodeScannerActivity
import br.com.edsilfer.android.starwarswiki.view.activities.SplashActivity
import br.com.edsilfer.android.starwarswiki.view.fragments.FilmsFragment
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

    fun inject(filmsActivity: FilmsActivity)

    fun inject(filmsFragment: FilmsFragment)


}



