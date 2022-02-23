package com.android.app.onetwoone.ui.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.android.app.onetwoone.OTwoOApplication
import com.android.app.onetwoone.di.sharedPreferences
import com.android.app.onetwoone.di.useCasesModule
import com.android.app.onetwoone.domain.model.Beer
import com.android.app.onetwoone.domain.useCases.GetBeerPage
import com.android.app.onetwoone.domain.useCases.GetBeerById
import com.android.app.onetwoone.domain.useCases.GetBeerSearch
import com.android.app.onetwoone.domain.utils.Result
import com.android.app.onetwoone.ui.utils.Data
import com.android.app.onetwoone.ui.utils.SharedPreferencesConfig
import com.android.app.onetwoone.ui.utils.Status
import com.google.common.truth.Truth
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


private const val VALID_ID = 33
private const val INVALID_ID = -1
private const val PAGE_VALID = 1
private const val PER_PAGE_VALID = 80
private const val PER_PAGE_INVALID = 100
private const val NAME_SEARCH_VALID = "e"

class BeerViewModelTest : AutoCloseKoinTest() {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var subject: BeerViewModel

    @Mock
    lateinit var beerValidResult: Result.Success<List<Beer>>

    @Mock
    lateinit var beerInvalidResult: Result.Failure

    @Mock
    lateinit var beers: List<Beer>

    @Mock
    lateinit var exception: Exception

    private val getBeerPageUseCase: GetBeerPage by inject()
    private val getBeerByIdUseCase: GetBeerById by inject()
    private val getBeerSearchUseCase: GetBeerSearch by inject()
    private val sharedPreferencesConfig: SharedPreferencesConfig by inject()
    private val context: Context by inject()


    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        startKoin {
            androidContext(OTwoOApplication())
            modules(
                listOf(
                    useCasesModule, sharedPreferences
                )
            )
        }

        declareMock<GetBeerPage>()
        declareMock<GetBeerById>()
        declareMock<GetBeerSearch>()
        MockitoAnnotations.initMocks(this)
        subject = BeerViewModel(
            sharedPreferencesConfig,
            getBeerByIdUseCase,
            getBeerPageUseCase,
            getBeerSearchUseCase
        )
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        stopKoin()
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `when getBeersById is successful`() {
        Mockito.`when`(getBeerByIdUseCase.invoke(VALID_ID)).thenReturn(beerValidResult)
        Mockito.`when`(beerValidResult.data).thenReturn(beers)
        runBlocking {
            subject.onClickToBeerDetails(VALID_ID, context).join()
        }
        val liveDataUnderTest = subject.mainStateDetail.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(Data(responseType = Status.SUCCESSFUL, data = beerValidResult.data))
    }

    @Test
    fun `when getBeersById is failure`() {
        Mockito.`when`(getBeerByIdUseCase.invoke(INVALID_ID)).thenReturn(beerInvalidResult)
        Mockito.`when`(beerInvalidResult.exception).thenReturn(exception)
        runBlocking {
            subject.onClickToBeerDetails(INVALID_ID, context).join()
        }
        val liveDataUnderTest = subject.mainStateDetail.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(Data(responseType = Status.ERROR, data = null, error = beerInvalidResult.exception))
    }

    @Test
    fun `when getBeerList is successful`() {
        Mockito.`when`(getBeerPageUseCase.invoke(PAGE_VALID, PER_PAGE_VALID)).thenReturn(beerValidResult)
        Mockito.`when`(beerValidResult.data).thenReturn(beers)
        runBlocking {
            subject.onStartHome(PAGE_VALID, PER_PAGE_VALID).join()
        }
        val liveDataUnderTest = subject.mainStateList.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(Data(responseType = Status.SUCCESSFUL, data = beerValidResult.data))
    }

    @Test
    fun `when getBeerList is failure`() {
        Mockito.`when`(getBeerPageUseCase.invoke(PAGE_VALID, PER_PAGE_INVALID)).thenReturn(beerInvalidResult)
        Mockito.`when`(beerInvalidResult.exception).thenReturn(exception)
        runBlocking {
            subject.onStartHome(PAGE_VALID, PER_PAGE_INVALID).join()
        }
        val liveDataUnderTest = subject.mainStateList.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(Data(responseType = Status.ERROR, data = null, error = beerInvalidResult.exception))
    }

    @Test
    fun `when getSearchBeer is successful`() {
        Mockito.`when`(getBeerSearchUseCase.invoke(NAME_SEARCH_VALID, PAGE_VALID, PER_PAGE_VALID)).thenReturn(beerValidResult)
        Mockito.`when`(beerValidResult.data).thenReturn(beers)
        runBlocking {
            subject.onSearchBeer(NAME_SEARCH_VALID, PAGE_VALID, PER_PAGE_VALID).join()
        }
        val liveDataUnderTest = subject.mainStateList.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(Data(responseType = Status.SUCCESSFUL, data = beerValidResult.data))
    }

    @Test
    fun `when getSearchBeer is failure`() {
        Mockito.`when`(getBeerSearchUseCase.invoke(NAME_SEARCH_VALID, PAGE_VALID, PER_PAGE_INVALID)).thenReturn(beerInvalidResult)
        Mockito.`when`(beerInvalidResult.exception).thenReturn(exception)
        runBlocking {
            subject.onSearchBeer(NAME_SEARCH_VALID, PAGE_VALID, PER_PAGE_INVALID).join()
        }
        val liveDataUnderTest = subject.mainStateList.testObserver()
        Truth.assertThat(liveDataUnderTest.observedValues[0])
            .isEqualTo(Data(responseType = Status.ERROR, data = null, error = beerInvalidResult.exception))
    }

    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    private fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
        observeForever(it)
    }
}