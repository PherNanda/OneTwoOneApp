package com.android.app.onetwoone.domain.repository

import com.android.app.onetwoone.domain.model.Beer
import com.android.app.onetwoone.domain.utils.Result

interface BeerRepository {

    fun getBeerById(id: Int)
            : Result<List<Beer>>

    fun getBeersPage(page: Int, perPage: Int)
            : Result<List<Beer>>

    fun getBeerSearch(
        beerName: String,
        page: Int,
        perPage: Int
    )
            : Result<List<Beer>>
}