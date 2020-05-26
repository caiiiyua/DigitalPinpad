package caiiiyua.digitalpin

import android.app.Application
import caiiiyua.digitalpin.common.di.DaggerComponentProvider
import caiiiyua.digitalpin.common.di.component.ApplicationComponent
import caiiiyua.digitalpin.common.di.component.DaggerApplicationComponent

class DigitalPinApplication : Application(), DaggerComponentProvider {
    override val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationContext(this)
            .build()
    }
}