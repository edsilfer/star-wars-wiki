package br.com.edsilfer.android.starwarswiki.infrastructure.dagger

/**
 * Created by User on 08/01/2017.
 */
class Injector {
    companion object {
        val mainModule = MainModule()

        val component = DaggerMainComponent
                .builder()
                .mainModule(mainModule)
                .build()

        fun getInstance(): MainComponent {
            return component
        }
    }
}