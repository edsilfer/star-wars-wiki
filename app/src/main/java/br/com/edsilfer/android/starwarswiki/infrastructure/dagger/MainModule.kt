package br.com.edsilfer.android.starwarswiki.infrastructure.dagger

import br.com.edsilfer.android.starwarswiki.infrastructure.Postman
import br.com.edsilfer.android.starwarswiki.presenter.HomepagePresenter
import br.com.edsilfer.android.starwarswiki.presenter.QRCodeScannerPresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.HomepagePresenterContract
import br.com.edsilfer.android.starwarswiki.presenter.contracts.QRCodeScannerPresenterContract
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by edgar on 12-May-16.
 */
@Module
class MainModule {

    @Provides
    @Singleton
    fun providePostman(): Postman {
        return Postman()
    }

    @Provides
    @Singleton
    fun provideHomepagePresenterContract(): HomepagePresenterContract {
        return HomepagePresenter(providePostman())
    }

    @Provides
    @Singleton
    fun provideQRCodeScannerPresenterContract(): QRCodeScannerPresenterContract {
        return QRCodeScannerPresenter(providePostman())
    }

}
