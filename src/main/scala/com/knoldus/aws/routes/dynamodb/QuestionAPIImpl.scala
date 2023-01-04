package com.knoldus.aws.routes.dynamodb

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.knoldus.aws.services.dynamodb.QuestionService

class QuestionAPIImpl(questionService: QuestionService) extends QuestionAPI {
  val routes: Route = submitQuestion ~ getQuestion ~ updateQuestion() ~ deleteQuestion()

  override def submitQuestion: Route = ???

  override def getQuestion: Route = ???

  override def updateQuestion(): Route = ???

  override def deleteQuestion(): Route = ???
}
