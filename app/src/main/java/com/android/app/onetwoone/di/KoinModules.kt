package com.android.app.onetwoone.di

import android.content.Context
import android.content.SharedPreferences
import com.android.app.onetwoone.data.repository.BeerRepositoryImpl
import com.android.app.onetwoone.data.service.BeerService
import com.android.app.onetwoone.domain.repository.BeerRepository
import com.android.app.onetwoone.domain.useCases.GetBeerPage
import com.android.app.onetwoone.domain.useCases.GetBeerById
import com.android.app.onetwoone.domain.useCases.GetBeerSearch
import com.android.app.onetwoone.ui.utils.SharedPreferencesConfig
import com.android.app.onetwoone.ui.viewmodel.BeerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoriesModule = module {
    single { BeerService() }
    single<BeerRepository> { BeerRepositoryImpl(get()) }
}

val viewModelModule = module {
    single { BeerViewModel(get(), get(), get(), get()) }
}

val useCasesModule = module {
    single { GetBeerById(get()) }
    single { GetBeerPage(get()) }
    single { GetBeerSearch(get()) }
}

val sharedPreferences = module {

    single { SharedPreferencesConfig(androidContext())}

    single {
        getSharedPrefs(androidContext(), "com.example.android.PREFERENCE_FILE")
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(androidContext(), "com.example.android.PREFERENCE_FILE").edit()
    }
}

fun getSharedPrefs(context: Context, fileName: String): SharedPreferences {
    return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
}