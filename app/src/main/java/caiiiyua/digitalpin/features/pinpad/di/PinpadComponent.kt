package caiiiyua.digitalpin.features.pinpad.di

import caiiiyua.digitalpin.common.di.ViewModelFactory
import caiiiyua.digitalpin.features.pinpad.viewmodel.PinpadViewModel

interface PinpadComponent {
    fun pinpadViewModelFactory(): ViewModelFactory<PinpadViewModel>
}