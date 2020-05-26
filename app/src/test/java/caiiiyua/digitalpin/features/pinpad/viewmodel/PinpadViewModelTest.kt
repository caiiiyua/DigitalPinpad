package caiiiyua.digitalpin.features.pinpad.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import caiiiyua.digitalpin.BaseUnitTest
import caiiiyua.digitalpin.common.usecase.GeneratePinBlockUsecase
import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class PinpadViewModelTest : BaseUnitTest() {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    @Inject lateinit var mockGeneratePinBlockUsecase: GeneratePinBlockUsecase

    private lateinit var viewModel: PinpadViewModel

    @Before
    override fun setup() {
        super.setup()
        testComponent.inject(this)
        viewModel = PinpadViewModel((mockGeneratePinBlockUsecase))
    }

    @Test
    fun testInvalidPincode() {
        val mockedObserver = getMockedPinpadViewStateObserver()
        viewModel.getPinpadViewState().observeForever(mockedObserver)

        viewModel.validatePin("123")

        val slot = slot<PinpadViewModel.PinpadViewState>()
        verify { mockedObserver.onChanged(capture(slot)) }
        assertEquals(PinpadViewModel.PinpadViewState.INVALID_PIN, slot.captured)
    }

    @Test
    fun testValidPincode() {
        val mockedObserver = getMockedPinpadViewStateObserver()
        viewModel.getPinpadViewState().observeForever(mockedObserver)

        viewModel.validatePin("123456")

        val slot = slot<PinpadViewModel.PinpadViewState>()
        verify { mockedObserver.onChanged(capture(slot)) }
        assertEquals(PinpadViewModel.PinpadViewState.VALID_PIN, slot.captured)
    }

    @Test
    fun testValidPinBlock() {
        val mockedObserver = spyk(Observer<String> {})
        viewModel.getPinBlock().observeForever(mockedObserver)

        every { mockGeneratePinBlockUsecase.getPinBlock(any()) } returns "361216759CFA8889"

        viewModel.computePin("123456")

        val slot = slot<String>()
        verify { mockedObserver.onChanged(capture(slot)) }
        assertEquals("361216759CFA8889", slot.captured)
    }

    @Test
    fun testInvalidPinBlock() {
        val mockedObserver = spyk(Observer<String> {})
        viewModel.getPinBlock().observeForever(mockedObserver)

        every { mockGeneratePinBlockUsecase.getPinBlock(any()) } returns ""

        viewModel.computePin("123456")

        val slot = slot<String>()
        verify { mockedObserver.onChanged(capture(slot)) }
        assertEquals(true, slot.captured.isNullOrEmpty())
    }

    private fun getMockedPinpadViewStateObserver(): Observer<PinpadViewModel.PinpadViewState> {
        return spyk(Observer {})
    }
}