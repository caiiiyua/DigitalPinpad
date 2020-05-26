package caiiiyua.digitalpin.common.di

import caiiiyua.digitalpin.features.pinpad.viewmodel.PinpadViewModelTest
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestCommonModule::class])
interface TestCommonComponent {
    fun inject(testcase: PinpadViewModelTest)

    @Component.Builder
    interface Builder {
        fun build(): TestCommonComponent
        @BindsInstance
        fun testCommonModule(testCommonModule: TestCommonModule): Builder
    }
}