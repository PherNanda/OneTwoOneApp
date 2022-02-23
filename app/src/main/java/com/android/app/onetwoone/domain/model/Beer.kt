@file:Suppress("SpellCheckingInspection")

package com.android.app.onetwoone.domain.model

const val NOT_FOUND = "NOT FOUND"
const val NO_IMAGE = "NO IMAGE"
const val DEFAULT_ID = 0
const val DEFAULT_FLOAT = 0f

class Beer(
    val id: Int = DEFAULT_ID,
    val name: String = NOT_FOUND,
    val description: String = NOT_FOUND,
    val tagline: String = NOT_FOUND,
    val imageURL: String = NO_IMAGE,
    val abv: Float = DEFAULT_FLOAT,
    val ibu: Float = DEFAULT_FLOAT
)