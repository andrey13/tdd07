package com.raywenderlich.android.cocktails.game.model

class Question(

  val correctOption: String,
  val incorrectOption: String

  ) {

  var answeredOption: String? = null
    private set

  val isAnsweredCorrectly: Boolean
      get() = correctOption == answeredOption

  fun answer(option: String): Boolean {
    if (option != correctOption && option != incorrectOption)
      throw IllegalArgumentException("Not a valid option")

    answeredOption = option

    return isAnsweredCorrectly
  }

  fun getOptions(sort: (List<String>) -> List<String> = { it.shuffled() })
      = sort(listOf(correctOption, incorrectOption))
}