package com.raywenderlich.android.cocktails.game.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class QuestionUnitTests {
  private lateinit var question: Question

  @Before
  fun setup() {
    question = Question("CORRECT", "INCORRECT")
  }

  //Unit test
  @Test
  fun `when creating question should not have answeredOption`() {
    Assert.assertNull(question.answeredOption)
  }

  //Unit test
  @Test
  fun `when answering should have answeredOption`() {
    question.answer("INCORRECT")

    Assert.assertEquals("INCORRECT", question.answeredOption)
  }

  //Unit test
  @Test
  fun `when answering with correct option should return true`() {
    val result = question.answer("CORRECT")

    Assert.assertTrue(result)
  }

  //Unit test
  @Test
  fun `when answering with incorrect option should return false`() {
    val result = question.answer("INCORRECT")

    Assert.assertFalse(result)
  }

  //Unit test
  @Test(expected = IllegalArgumentException::class)
  fun `when answering with invalid option should throw exception`() {
    question.answer("INVALID")
  }

  //Unit test
  @Test
  fun `when creating question should return options with custom sort`() {
    val options = question.getOptions { it.reversed() }

    Assert.assertEquals(listOf("INCORRECT", "CORRECT"), options)
  }
}