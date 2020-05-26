package caiiiyua.digitalpin

import caiiiyua.digitalpin.common.di.DaggerTestCommonComponent
import caiiiyua.digitalpin.common.di.TestCommonComponent
import caiiiyua.digitalpin.common.di.TestCommonModule
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
open class BaseUnitTest {

    protected lateinit var testComponent: TestCommonComponent

    @Before
    open fun setup() {
        testComponent = DaggerTestCommonComponent.builder().testCommonModule(TestCommonModule()).build()
    }
}
