package com.android.app.onetwoone.domain.useCases

import com.android.app.onetwoone.domain.repository.BeerRepository
import org.koin.core.KoinComponent

class GetBeerPage(private val beerRepository: BeerRepository) : KoinComponent {

    operator fun invoke(page: Int, perPage: Int) = beerRepository.getBeersPage(page, perPage)
}