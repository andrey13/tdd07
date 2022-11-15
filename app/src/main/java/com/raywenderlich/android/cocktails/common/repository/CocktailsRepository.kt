package com.raywenderlich.android.cocktails.common.repository

import com.raywenderlich.android.cocktails.common.network.Cocktail

interface CocktailsRepository {
    fun getAlcoholic(callback: RepositoryCallback<List<Cocktail>, String>)
    fun saveHighScore(score: Int)
}

interface RepositoryCallback<T, E> {
    fun onSuccess(t: T)
    fun onError(e: E)
}