package com.mendelin.catpedia

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.GsonBuilder
import com.mendelin.catpedia.repository.local.LoginRepository
import com.mendelin.catpedia.repository.local.MockedLoginResponse
import com.mendelin.catpedia.repository.storage.JsonStorage
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginRepositoryTest {
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
        val context = InstrumentationRegistry.getInstrumentation().targetContext

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
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val testObserver = repository
            .mockedUserLogin(context, "", "")
            .test()
        testObserver.await()

        testObserver
            .assertError(Throwable::class.java)
    }
}