package caiiiyua.digitalpin.features.pinpad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import caiiiyua.digitalpin.common.usecase.GeneratePinBlockUsecase
import javax.inject.Inject

class PinpadViewModel @Inject constructor(private val generatePinBlockUsecase: GeneratePinBlockUsecase) : ViewModel() {

    companion object {
        const val MIN_PIN_CODES = 4
        const val MAX_PIN_CODES = 12
    }

    enum class PinpadViewState {
        IDLE,
        INVALID_PIN,
        VALID_PIN,
    }

    private val pinpadViewState = MutableLiveData<PinpadViewState>()

    private val pinBlock = MutableLiveData<String>()

    init {
        pinpadViewState.value = PinpadViewState.IDLE
    }

    fun getPinpadViewState(): LiveData<PinpadViewState> {
        return pinpadViewState
    }

    fun getPinBlock(): LiveData<String> {
        return pinBlock
    }

    fun validatePin(pincode: String) {
         val ret = checkPincodeLength(pincode)
                 && pincode.none { !it.isDigit() }
        pinpadViewState.value = if (ret) PinpadViewState.VALID_PIN else PinpadViewState.INVALID_PIN
    }

    private fun checkPincodeLength(pincode: String) = pincode.length in MIN_PIN_CODES..MAX_PIN_CODES

    fun computePin(pincode: String) {
        pinBlock.value = generatePinBlockUsecase.getPinBlock(pincode)
    }
}
