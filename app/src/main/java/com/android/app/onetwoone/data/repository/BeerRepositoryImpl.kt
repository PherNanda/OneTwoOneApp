package com.android.app.onetwoone.data.repository

import com.android.app.onetwoone.data.service.BeerService
import com.android.app.onetwoone.domain.model.Beer
import com.android.app.onetwoone.domain.repository.BeerRepository
import com.android.app.onetwoone.domain.utils.Result

class BeerRepositoryImpl(
    private val beerService: BeerService
) : BeerRepository {

    override fun getBeerById(id: Int): Result<List<Beer>> {
        return beerService.getBeersById(id)
    }

    override fun getBeersPage(page: Int, perPage: Int): Result<List<Beer>> {
        return beerService.getBeersList(page, perPage)
    }

    override fun getBeerSearch(beerName: String, page: Int, perPage: Int): Result<List<Beer>> {
        return beerService.getSearchBeer(beerName, page, perPage)
    }
}