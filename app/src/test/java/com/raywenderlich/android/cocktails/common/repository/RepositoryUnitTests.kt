package com.raywenderlich.android.cocktails.common.repository

import android.content.SharedPreferences
import com.raywenderlich.android.cocktails.common.network.CocktailsApi
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class RepositoryUnitTests {

    private lateinit var repository: CocktailsRepository
    private lateinit var api: CocktailsApi
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        api = mock()

        sharedPreferences = mock()

        sharedPreferencesEditor = mock()
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)

        repository = CocktailsRepositoryImpl(api, sharedPreferences)
    }

    @Test
    fun `save score should save to shared preferences`() {
        val score = 100

        repository.saveHighScore(score)

        inOrder(sharedPreferencesEditor) {
            verify(sharedPreferencesEditor)
                .putInt(any(), eq(score))

            verify(sharedPreferencesEditor)
                .apply()
        }
    }

    @Test
    fun `get score should get from shared preferences`() {
        repository.getHighScore()
        verify(sharedPreferences).getInt(any(), any())
    }
}