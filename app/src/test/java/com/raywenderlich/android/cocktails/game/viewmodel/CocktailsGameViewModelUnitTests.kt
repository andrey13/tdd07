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

    // Здесь вы проверяете, что вызов initGame для ViewModel вызовет buildGame из фабрики
    @Test
    fun `init should build game`() {
        viewModel.initGame()
        verify(factory).buildGame(any())
    }

    // В обоих тестах вы проверяете, что initGame публикует правильные данные.
    // Когда программа отправляет значение в LiveData, объект вызывает onChanged()
    // со значением. Это функция, которую вы проверяете.
    @Test
    fun `init should Show Loading`() {
        viewModel.initGame()
        verify(loadingObserver).onChanged(eq(true))
    }

    @Test
    fun `init should Hide Error`() {
        viewModel.initGame()
        verify(errorObserver).onChanged(eq(false))
    }

    // Вы также захотите показать окно с ошибкой и перестать показывать окно загрузки,
    // когда  возникнет  проблема  со  сборкой  игры.
    @Test
    fun `init should Show Error when Factory Returns Error`() {
        setUpFactoryWithError()
        viewModel.initGame()
        verify(errorObserver).onChanged(eq(true))
    }

    @Test
    fun `init should Hide Loading when Factory Returns Error`() {
        setUpFactoryWithError()
        viewModel.initGame()
        verify(loadingObserver).onChanged(eq(false))
    }

    // Другой  сценарий,  который  вы  захотите  рассмотреть,  —  это  скрытие  ошибки
    // и загрузка представлений, когда фабрика успешно создает игру.
    // Здесь вы дважды проверяете, что ошибка имеет значение false.
    // Первое значение `false`  устанавливается  перед  вызовом
    // репозитория  для  сборки  игры
    @Test
    fun `init should Hide Error when Factory Returns Success`() {
        setUpFactoryWithSuccessGame(game)
        viewModel.initGame()
        verify(errorObserver, times(2)).onChanged(eq(false))
    }

    // а  второе устанавливается, когда игра не может быть собрана из-за ошибки
    @Test
    fun `init should Hide Loading when Factory Returns Success`() {
        setUpFactoryWithSuccessGame(game)
        viewModel.initGame()
        verify(loadingObserver).onChanged(eq(false))
    }
}