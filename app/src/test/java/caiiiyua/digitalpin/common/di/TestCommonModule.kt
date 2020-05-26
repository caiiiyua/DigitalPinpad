package caiiiyua.digitalpin.common.di

import caiiiyua.digitalpin.common.usecase.GeneratePinBlockUsecase
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class TestCommonModule {
    @Singleton
    @Provides
    fun provideGeneratePinBlockUsecase(): GeneratePinBlockUsecase {
        return mockk()
    }
}