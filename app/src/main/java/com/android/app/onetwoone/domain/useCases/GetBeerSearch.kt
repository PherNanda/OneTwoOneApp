package com.android.app.onetwoone.domain.useCases

import com.android.app.onetwoone.domain.repository.BeerRepository
import org.koin.core.KoinComponent

class GetBeerSearch(private val beerRepository: BeerRepository) : KoinComponent {

    operator fun invoke(beerName: String, page: Int, perPage: Int) =
        beerRepository.getBeerSearch(beerName, page, perPage)
}