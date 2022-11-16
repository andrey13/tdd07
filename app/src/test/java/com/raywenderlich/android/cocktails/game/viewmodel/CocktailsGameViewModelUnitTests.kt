package com.raywenderlich.android.cocktails.game.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.raywenderlich.android.cocktails.common.repository.CocktailsRepository
import com.raywenderlich.android.cocktails.game.CocktailsGameFactory
import com.raywenderlich.android.cocktails.game.model.Game
import com.raywenderlich.android.cocktails.game.model.Question
import com.raywenderlich.android.cocktails.game.model.Score
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class CocktailsGameViewModelUnitTests {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: CocktailsRepository
    private lateinit var factory: CocktailsGameFactory
    private lateinit var viewModel: CocktailsGameViewModel
    private lateinit var game: Game
    private lateinit var loadingObserver: Observer<Boolean>
    private lateinit var errorObserver: Observer<Boolean>
    private lateinit var scoreObserver: Observer<Score>
    private lateinit var questionObserver: Observer<Question>

    @Before
    fun setup() {
        // 1
        repository = mock()
        factory = mock()
        viewModel = CocktailsGameViewModel(repository, factory)

        // 2
        game = mock()

        // 3
        loadingObserver = mock()
        errorObserver = mock()
        scoreObserver = mock()
        questionObserver = mock()

        viewModel.getLoading().observeForever(loadingObserver)
        viewModel.getScore().observeForever(scoreObserver)
        viewModel.getQuestion().observeForever(questionObserver)
        viewModel.getError().observeForever(errorObserver)
    }

    private fun setUpFactoryWithSuccessGame(game: Game) {
        doAnswer {
            val callback: CocktailsGameFactory.Callback = it.getArgument(0)
            callback.onSuccess(game)
        }.whenever(factory).buildGame(any())
    }

    private fun setUpFactoryWithError() {
        doAnswer {
            val callback: CocktailsGameFactory.Callback = it.getArgument(0)
            callback.onError()
        }.whenever(factory).buildGame(any())
    }

    @Test
    fun `init should build game`() {
        viewModel.initGame()
        verify(factory).buildGame(any())
    }
}