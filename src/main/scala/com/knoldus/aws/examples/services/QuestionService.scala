package com.knoldus.aws.examples.services

import com.knoldus.aws.examples.models.{ Question, QuestionUpdate }
import scala.concurrent.Future

trait QuestionService {
  def submitQuestion(question: Question): Future[Option[String]]

  def getQuestion(id: String, category: String): Future[Option[Question]]

  def updateQuestion(id: String, category: String, update: QuestionUpdate): Future[Option[String]]

  def deleteQuestion(id: String, category: String): Future[Unit]
}
