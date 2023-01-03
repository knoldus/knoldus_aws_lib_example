package com.knoldus.aws.examples.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.knoldus.aws.examples.services.QuestionService

class QuestionAPIImpl(questionService: QuestionService) extends QuestionAPI {
  val questionAPIRoutes: Route = submitQuestion ~ getQuestion ~ updateQuestion() ~ deleteQuestion()

  override def submitQuestion: Route = ???

  override def getQuestion: Route = ???

  override def updateQuestion(): Route = ???

  override def deleteQuestion(): Route = ???
}
