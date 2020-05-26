package caiiiyua.digitalpin.common.di

import android.app.Activity
import androidx.fragment.app.Fragment
import caiiiyua.digitalpin.common.di.component.ApplicationComponent

interface DaggerComponentProvider {

    val component: ApplicationComponent
}

val Activity.injector get() = (application as DaggerComponentProvider).component
val Fragment.injector get() = (activity?.application as DaggerComponentProvider).component