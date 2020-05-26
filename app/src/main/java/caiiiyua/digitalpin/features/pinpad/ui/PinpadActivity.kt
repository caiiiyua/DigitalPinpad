package caiiiyua.digitalpin.features.pinpad.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import caiiiyua.digitalpin.R

class PinpadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    PinpadFragment.newInstance()
                )
                .commitNow()
        }
    }
}
