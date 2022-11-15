package com.raywenderlich.android.cocktails.common.repository

import android.content.SharedPreferences
import com.raywenderlich.android.cocktails.common.network.Cocktail
import com.raywenderlich.android.cocktails.common.network.CocktailsApi
import com.raywenderlich.android.cocktails.common.network.CocktailsContainer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailsRepositoryImpl(

  private val api: CocktailsApi,
  sharedPreferences: SharedPreferences

) : CocktailsRepository {

  private var getAlcoholicCall: Call<CocktailsContainer>? = null

  override fun getAlcoholic(callback: RepositoryCallback<List<Cocktail>, String>) {
    getAlcoholicCall?.cancel()
    getAlcoholicCall = api.getAlcoholic()
    getAlcoholicCall?.enqueue(wrapCallback(callback))
  }

  override fun saveHighScore(score: Int) {
    TODO("Not yet implemented")
  }

  private fun wrapCallback(callback: RepositoryCallback<List<Cocktail>, String>) =
      object : Callback<CocktailsContainer> {
        override fun onResponse(call: Call<CocktailsContainer>?,
                                response: Response<CocktailsContainer>?) {
          if (response != null && response.isSuccessful) {
            val cocktailsContainer = response.body()
            if (cocktailsContainer != null) {
              callback.onSuccess(cocktailsContainer.drinks ?: emptyList())
              return
            }
          }
          callback.onError("Couldn't get cocktails")
        }

        override fun onFailure(call: Call<CocktailsContainer>?, t: Throwable?) {
          callback.onError("Couldn't get cocktails")
        }
      }
}