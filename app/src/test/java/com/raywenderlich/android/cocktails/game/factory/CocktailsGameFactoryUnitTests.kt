package com.raywenderlich.android.cocktails.game.factory

import com.raywenderlich.android.cocktails.common.network.Cocktail
import com.raywenderlich.android.cocktails.common.repository.CocktailsRepository
import com.raywenderlich.android.cocktails.common.repository.RepositoryCallback
import com.raywenderlich.android.cocktails.game.CocktailsGameFactory
import com.raywenderlich.android.cocktails.game.CocktailsGameFactoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class CocktailsGameFactoryUnitTests {

    private lateinit var repository: CocktailsRepository
    private lateinit var factory: CocktailsGameFactory

    private val cocktails = listOf(
        Cocktail("1", "Drink1", "image1"),
        Cocktail("2", "Drink2", "image2"),
        Cocktail("3", "Drink3", "image3"),
        Cocktail("4", "Drink4", "image4")
    )

    @Before
    fun setup() {
        repository = mock()
        factory = CocktailsGameFactoryImpl(repository)
    }

    @Test
    fun `build game should get cocktails from repo`() {
        factory.buildGame(mock())
        verify (repository).getAlcoholic(any())
    }

    @Test
    fun `build game should call on success`() {
        val callback = mock<CocktailsGameFactory.Callback>()
        setUpRepositoryWithCocktails(repository)
        factory.buildGame(callback)
        verify(callback).onSuccess(any())
    }

    private fun setUpRepositoryWithCocktails(repository: CocktailsRepository) {
        doAnswer {
            // 1
            val callback: RepositoryCallback<List<Cocktail>, String> = it.getArgument(0)
            callback.onSuccess(cocktails)
        }.whenever(repository).getAlcoholic(any())
    }

    @Test
    fun `build game should call on error`() {
        val callback = mock<CocktailsGameFactory.Callback>()
        setUpRepositoryWithError (repository)
        factory.buildGame(callback)
        verify (callback).onError()
    }

    private fun setUpRepositoryWithError(
        repository: CocktailsRepository) {
        doAnswer {
            val callback: RepositoryCallback<List<Cocktail>, String> = it.getArgument(0)
            callback.onError ("Error")
        }.whenever(repository).getAlcoholic(any())
    }
}
