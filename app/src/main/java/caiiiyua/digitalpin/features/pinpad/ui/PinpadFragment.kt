package caiiiyua.digitalpin.features.pinpad.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import caiiiyua.digitalpin.R
import caiiiyua.digitalpin.common.di.injector
import caiiiyua.digitalpin.features.pinpad.viewmodel.PinpadViewModel

class PinpadFragment : Fragment() {

    companion object {
        fun newInstance() = PinpadFragment()
    }

    private val viewModel: PinpadViewModel by lazy {
        injector.pinpadViewModelFactory().create(PinpadViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
