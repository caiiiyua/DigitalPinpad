package caiiiyua.digitalpin.common.di.module

import caiiiyua.digitalpin.common.usecase.GeneratePinBlockUsecase
import caiiiyua.digitalpin.common.usecase.GeneratePinBlockUsecaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommonModule {

    @Singleton
    @Provides
    fun provideGeneratePinBlockUsecase(): GeneratePinBlockUsecase {
        return GeneratePinBlockUsecaseImpl()
    }
}