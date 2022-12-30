package com.knoldus.dynamodb.service

import com.knoldus.dynamodb.domain.{ Question, QuestionUpdate }
import scala.concurrent.Future

trait QuestionService {
  def submitQuestion(question: Question): Future[Option[String]]

  def getQuestion(id: String, category: String): Future[Option[Question]]

  def updateQuestion(id: String, category: String, update: QuestionUpdate): Future[Option[String]]

  def deleteQuestion(id: String, category: String): Future[Unit]
}
