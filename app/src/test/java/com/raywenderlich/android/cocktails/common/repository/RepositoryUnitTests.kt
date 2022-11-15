package com.raywenderlich.android.cocktails.common.repository

import android.content.SharedPreferences
import com.raywenderlich.android.cocktails.common.network.CocktailsApi
import org.junit.Test
import org.mockito.kotlin.*

class RepositoryUnitTests {

    @Test
    fun saveScore_shouldSaveToSharedPreferences() {

        val api: CocktailsApi = mock()
        val sharedPreferencesEditor: SharedPreferences.Editor = mock()
        val sharedPreferences: SharedPreferences = mock()
        whenever(sharedPreferences.edit())
            .thenReturn(sharedPreferencesEditor)

        val repository = CocktailsRepositoryImpl(api, sharedPreferences)
        val score = 100

        repository.saveHighScore(score)

        inOrder(sharedPreferencesEditor) {
            verify(sharedPreferencesEditor).putInt(any(), eq(score))
            verify(sharedPreferencesEditor).apply()
        }
    }
}