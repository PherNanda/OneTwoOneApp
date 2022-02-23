package com.android.app.onetwoone.data.service

import com.android.app.onetwoone.data.PunkRequestGenerator
import com.android.app.onetwoone.data.mapper.BeerMapperService
import com.android.app.onetwoone.data.service.api.BeerApi
import com.android.app.onetwoone.domain.model.Beer
import com.android.app.onetwoone.domain.utils.Result


class BeerService {
    private val api: PunkRequestGenerator = PunkRequestGenerator()
    private val mapper: BeerMapperService = BeerMapperService()

    fun getBeersById(id: Int): Result<List<Beer>> {
        val callResponse = api.createService(BeerApi::class.java).getBeersById(id)
        val response = callResponse.execute()
        if (response != null) {
            if (response.isSuccessful) {
                return Result.Success(response.body()?.map {
                    mapper.transform(it)
                })
            }
            return Result.Failure(Exception(response.message()))
        }
        return Result.Failure(Exception("Bad request/response"))
    }

    fun getBeersList(page: Int, perPage: Int): Result<List<Beer>> {
        val callResponse = api.createService(BeerApi::class.java).getBeersList(page, perPage)
        val response = callResponse.execute()
        if (response != null) {
            if (response.isSuccessful) {
                return Result.Success(response.body()?.map {
                    mapper.transform(it)
                })
            }
            return Result.Failure(Exception(response.message()))
        }
        return Result.Failure(Exception("Bad request/response"))
    }

    fun getSearchBeer(beerName: String, page: Int, perPage: Int): Result<List<Beer>> {
        val callResponse =
            api.createService(BeerApi::class.java).getSearchBeer(beerName, page, perPage)
        val response = callResponse.execute()
        if (response != null) {
            if (response.isSuccessful) {
                return Result.Success(response.body()?.map {
                    mapper.transform(it)
                })
            }
            return Result.Failure(Exception(response.message()))
        }
        return Result.Failure(Exception("Bad request/response"))
    }
}