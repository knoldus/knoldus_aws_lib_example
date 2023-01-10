package com.knoldus.aws.utils

object Constants {

  val submitQuestionResponse: String => String = id => s"""{"id": $id}"""
  val updateQuestionResponse = """{"message": "Question updated successfully"}"""
  val noQuestionFoundResponse = """{"message": "Question not found", "statusCode": "404"}"""
}
