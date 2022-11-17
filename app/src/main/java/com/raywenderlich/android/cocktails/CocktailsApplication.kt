package com.raywenderlich.android.cocktails

import android.app.Application
import android.content.Context
import com.raywenderlich.android.cocktails.common.network.CocktailsApi
import com.raywenderlich.android.cocktails.common.repository.CocktailsRepository
import com.raywenderlich.android.cocktails.common.repository.CocktailsRepositoryImpl
import com.raywenderlich.android.cocktails.game.factory.CocktailsGameFactory
import com.raywenderlich.android.cocktails.game.factory.CocktailsGameFactoryImpl

class CocktailsApplication : Application() {

  val repository: CocktailsRepository by lazy {
    CocktailsRepositoryImpl(
        CocktailsApi.create(),
        getSharedPreferences("Cocktails", Context.MODE_PRIVATE)
    )
  }

  val gameFactory: CocktailsGameFactory by lazy {
    CocktailsGameFactoryImpl(repository)
  }
}