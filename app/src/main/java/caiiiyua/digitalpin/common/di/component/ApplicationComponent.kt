package caiiiyua.digitalpin.common.di.component

import android.content.Context
import caiiiyua.digitalpin.common.di.module.ApplicationModule
import caiiiyua.digitalpin.common.di.module.CommonModule
import caiiiyua.digitalpin.features.pinpad.di.PinpadComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        CommonModule::class
    ]
)
interface ApplicationComponent :
    CommonComponent,
    PinpadComponent {
    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent
        @BindsInstance fun applicationContext(applicationContext: Context): Builder
    }
}