package com.android.app.onetwoone.data.mapper

import com.android.app.onetwoone.data.service.model.BeerModel
import com.android.app.onetwoone.domain.model.Beer

class BeerMapperService : BaseMapperRepository<BeerModel, Beer> {

    override fun transform(type: BeerModel): Beer =
        Beer(
            type.id,
            type.name,
            type.description,
            type.tagline,
            type.imageURL,
            type.abv,
            type.ibu
        )

    override fun transformToRepository(type: Beer): BeerModel =
        BeerModel(
            type.id,
            type.name,
            type.description,
            type.tagline,
            type.imageURL,
            type.abv,
            type.ibu
        )
}