package com.android.app.onetwoone.data.service.model

import com.google.gson.annotations.SerializedName

class BeerModel(
    val id: Int,
    val name: String,
    val description: String,
    val tagline: String,
    @SerializedName("image_url") val imageURL: String,
    val abv: Float,
    val ibu: Float
)