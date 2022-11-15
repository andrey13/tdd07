package com.raywenderlich.android.cocktails.game.model

import com.raywenderlich.android.cocktails.game.Score
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*

class GameUnitTests {

    // Integation test
    @Test
    fun `when Getting Next Question should Return It`() {
        val question1 = Question("CORRECT", "INCORRECT")
        val questions = listOf(question1)
        val game = Game(questions)

        val nextQuestion = game.nextQuestion()

        Assert.assertSame(question1, nextQuestion)
    }

    // Integation test
    @Test
    fun `when Getting Next Question with out More Questions should Return Null`() {
        val question1 = Question("CORRECT", "INCORRECT")
        val questions = listOf(question1)
        val game = Game(questions)

        game.nextQuestion()
        val nextQuestion = game.nextQuestion()

        Assert.assertNull(nextQuestion)
    }

    @Test
    fun `when answering should delegate to question`() {

        val question = mock<Question>()
        val game = Game(listOf(question))

        game.answer(question, "OPTION")

        verify(question, times(1)).answer(eq("OPTION"))
    }


    @Test
    fun `when answering correctly should increment current score`() {

        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(true)

        val score = mock<Score>()

        val game = Game(listOf(question), score)

        game.answer(question, "OPTION")

        verify(score).increment()
    }

    @Test
    fun `when answering incorrectly should not increment current score`() {

        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(false)

        val score = mock<Score>()

        val game = Game(listOf(question), score)

        game.answer(question, "OPTION")

        verify(score, never()).increment()
    }
}