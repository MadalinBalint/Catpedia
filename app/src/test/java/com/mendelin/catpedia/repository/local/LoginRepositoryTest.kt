package com.mendelin.catpedia.repository.local

import android.content.Context
import com.google.gson.GsonBuilder
import com.mendelin.catpedia.BuildConfig
import com.mendelin.catpedia.repository.storage.JsonStorage
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {
    /* System under test */
    lateinit var repository: LoginRepository

    @Before
    fun initEach() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val storage = JsonStorage(gson)
        val response = MockedLoginResponse(storage)
        repository = LoginRepository(response)
    }

    @Test
    fun mockedUserLogin_correctCredentials_returnsResponse() {
        val context = mock(Context::class.java)

        val testObserver = repository
            .mockedUserLogin(context, BuildConfig.MOCKED_USER_NAME, BuildConfig.MOCKED_USER_PASSWORD)
            .test()
        testObserver.await()

        testObserver
            .assertNoErrors()
            .assertValue {
                it.status == true && it.message == "Success"
            }
    }

    @Test
    fun mockedUserLogin_wrongCredentials_throwsException() {
        val context = mock(Context::class.java)

        val testObserver = repository
            .mockedUserLogin(context, "", "")
            .test()
        testObserver.await()

        testObserver
            .assertError(Throwable::class.java)
    }
}