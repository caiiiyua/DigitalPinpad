package caiiiyua.digitalpin.features.pinpad.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import caiiiyua.digitalpin.R
import caiiiyua.digitalpin.common.di.injector
import caiiiyua.digitalpin.features.pinpad.viewmodel.PinpadViewModel
import kotlinx.android.synthetic.main.pinpad_fragment.*

class PinpadFragment : Fragment() {

    companion object {
        fun newInstance() = PinpadFragment()
    }

    private val viewModel: PinpadViewModel by lazy {
        ViewModelProvider(this, injector.pinpadViewModelFactory()).get(PinpadViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.pinpad_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    private fun initData() {
        viewModel.getPinpadViewState().observe(viewLifecycleOwner, Observer { pinpadState ->
            when (pinpadState) {
                PinpadViewModel.PinpadViewState.INVALID_PIN -> {
                    pinpad_enter.error = getString(R.string.error_pincode)
                }
                else -> pinpad_enter.error = null
            }
        })

        viewModel.getPinBlock().observe(viewLifecycleOwner, Observer { pinBlock ->
            pin_block.text = pinBlock
        })
    }

    private fun initView() {

        pinpad_enter.addTextChangedListener(
            onTextChanged = { text, start, count, after ->
                pin_block.text = ""
                viewModel.validatePin(text.toString())
            }
        )

        pinpad_enter.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val pincode = v.text.toString()
                    viewModel.computePin(pincode)
                    true
                }
                else -> false
            }
        }
    }
}
