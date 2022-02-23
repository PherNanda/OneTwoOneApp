package com.android.app.onetwoone.ui.utils

import android.content.Context
import android.content.SharedPreferences
import com.android.app.onetwoone.domain.model.Beer

class SharedPreferencesConfig(private val context: Context) {

    private val sharedPrefFile = "com.example.android.PREFERENCE_FILE"
    private var preferences: SharedPreferences? = null

    fun saveCurrentBeerData(beer: Beer) {
        preferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val preferenceEditor = preferences?.edit()
        preferenceEditor?.putString("beerName", beer.name)
        preferenceEditor?.putString("beerTagline", beer.tagline)
        preferenceEditor?.putString("beerImage", beer.imageURL)
        preferenceEditor?.putString("beerDescription", beer.description)
        preferenceEditor?.putFloat("beerABV", beer.abv)
        preferenceEditor?.putFloat("beerIBU", beer.ibu)
        preferenceEditor?.apply()
    }
}