package com.android.app.onetwoone.data.service.api

import com.android.app.onetwoone.data.service.model.BeerModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {

    @GET("v2/beers")
    fun getBeersById(
        @Query("ids") id: Int
    ): Call<List<BeerModel>>

    @GET("v2/beers")
    fun getBeersList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<List<BeerModel>>

    @GET("v2/beers")
    fun getSearchBeer(
        @Query("beer_name") beerName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<List<BeerModel>>
}