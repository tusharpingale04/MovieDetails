package com.tushar.mdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tushar.mdetails.network.MoviesApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNull
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MovieServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MoviesApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(MoviesApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getMoviesListTest() = runBlocking {
        enqueueResponse("movies.json")
        val movies = service.getLatestMovies(1).body()?.results

        Assert.assertThat(movies, IsNull.notNullValue())
        Assert.assertThat(movies!!.size, CoreMatchers.`is`(20))
        Assert.assertThat(movies[0].originalTitle, CoreMatchers.`is`("Rogue"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }

}