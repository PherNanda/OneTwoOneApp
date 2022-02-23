package com.android.app.onetwoone.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.app.onetwoone.domain.model.Beer
import com.android.app.onetwoone.domain.useCases.GetBeerPage
import com.android.app.onetwoone.domain.useCases.GetBeerById
import com.android.app.onetwoone.domain.useCases.GetBeerSearch
import com.android.app.onetwoone.domain.utils.Result
import com.android.app.onetwoone.ui.utils.Data
import com.android.app.onetwoone.ui.utils.SharedPreferencesConfig
import com.android.app.onetwoone.ui.utils.Status
import com.android.app.onetwoone.ui.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BeerViewModel(
    private val sharedPreferencesConfig: SharedPreferencesConfig,
    val getBeerById: GetBeerById,
    val getBeerPage: GetBeerPage,
    val getBeerSearch: GetBeerSearch
) : BaseViewModel() {

    private var mutableMainStateList = MutableLiveData<Data<List<Beer>>>()
    val mainStateList: LiveData<Data<List<Beer>>>
        get() {
            return mutableMainStateList
        }

    private var mutableMainStateDetail = MutableLiveData<Data<List<Beer>>>()
    val mainStateDetail: LiveData<Data<List<Beer>>>
        get() {
            return mutableMainStateDetail
        }

    fun onStartHome(page: Int, perPage: Int) = launch {
        mutableMainStateList.value = Data(responseType = Status.LOADING)
        when (val result = withContext(Dispatchers.IO) { getBeerPage(page, perPage) }) {
            is Result.Failure -> {
                mutableMainStateList.value =
                    Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success -> {
                mutableMainStateList.value =
                    Data(responseType = Status.SUCCESSFUL, data = result.data)
            }
        }
    }

    fun onClickToBeerDetails(id: Int, context: Context) = launch {
        mutableMainStateDetail.value = Data(responseType = Status.LOADING)
        when (val result = withContext(Dispatchers.IO) { getBeerById(id) }) {
            is Result.Failure -> {
                mutableMainStateDetail.value =
                    Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success -> {
                mutableMainStateDetail.value =
                    Data(responseType = Status.SUCCESSFUL, data = result.data)
                result.data?.get(0)
                    ?.let { sharedPreferencesConfig.saveCurrentBeerData(it) }
            }
        }
    }

    fun onSearchBeer(beerName: String, page: Int, perPage: Int) = launch {
        mutableMainStateList.value = Data(responseType = Status.LOADING)
        when (val result = withContext(Dispatchers.IO) { getBeerSearch(beerName, page, perPage) }) {
            is Result.Failure -> {
                mutableMainStateList.value =
                    Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success -> {
                mutableMainStateList.value =
                    Data(responseType = Status.SUCCESSFUL, data = result.data)
            }
        }
    }
}