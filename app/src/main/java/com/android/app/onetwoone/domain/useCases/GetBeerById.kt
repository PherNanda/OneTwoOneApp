package com.android.app.onetwoone.domain.useCases

import com.android.app.onetwoone.domain.repository.BeerRepository
import org.koin.core.KoinComponent


class GetBeerById (private val beerRepository: BeerRepository) : KoinComponent{

    operator fun invoke(id: Int) = beerRepository.getBeerById(id)
}