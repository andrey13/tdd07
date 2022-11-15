package com.raywenderlich.android.cocktails.game.model

import com.raywenderlich.android.cocktails.game.Score
import org.junit.Assert
import org.junit.Test

class ScoreUnitTests {

    //Unit test
    @Test
    fun `when Incrementing Score should Increment Current Score`() {
        val score = Score()
        score.increment()
        Assert.assertEquals(
            "Current score should have been 1",
            1,
            score.current)
    }

    //Unit test
    @Test
    fun `when Incrementing Score above High Score should Also Increment High Score`() {
        val score = Score()
        score.increment()
        Assert.assertEquals(1, score.highest)
    }

    //Unit test
    @Test
    fun `when Incrementing Score below High Score should Not Increment High Score`() {
        val score = Score(10)
        score.increment()
        Assert.assertEquals(10, score.highest)
    }

}