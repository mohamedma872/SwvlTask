package  com.swvl.list.model


import android.os.Build
import com.egabi.core.constants.Constants
import com.egabi.core.testing.DependencyProvider
import com.swvl.androidtask.commons.data.remote.FlickerService
import com.swvl.androidtask.utils.Util
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

/**
 * Tests for [ListRemoteData]
 */

@RunWith(RobolectricTestRunner::class)
class ListRemoteDataTest {


    private lateinit var flickerService: FlickerService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        flickerService = DependencyProvider
            .getRetrofit(mockWebServer.url(Constants.Flicker_API_URL))
            .create(FlickerService::class.java)

    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.P])
    fun findMoviePictureListByTitle() {
        val movieTitle = "the"
        val pictures = flickerService.searchForPhotos(
            Util.method,
            Util.api_key,
            "url_l",
            Util.format,
            Util.nojsoncallback, movieTitle
        ).test().run {

            assertNoErrors()
            assertValueCount(1)
            Assert.assertNotEquals(values()[0].photos.pages, 0)
        }

    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }
}