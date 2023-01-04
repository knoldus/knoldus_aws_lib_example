package com.knoldus.aws.examples.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ ExceptionHandler, Route }
import akka.http.scaladsl.server.Directives._
import com.knoldus.aws.examples.models.Question
import com.knoldus.aws.examples.services.QuestionService
import com.knoldus.aws.examples.utils.JsonSupport
import com.typesafe.scalalogging.LazyLogging

class QuestionAPIImpl(questionService: QuestionService) extends QuestionAPI with LazyLogging with JsonSupport {
  val routes: Route = submitQuestion ~ getQuestion ~ updateQuestion() ~ deleteQuestion()

  implicit val noSuchElementExceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: NoSuchElementException =>
      complete(StatusCodes.NotFound, e.getMessage)
  }

  override def submitQuestion: Route =
    path("questions" / "submit") {
      pathEnd {
        (post & entity(as[Question])) { questionSubmissionRequest =>
          logger.info("Making request for question submission")
          val response = questionService.submitQuestion(questionSubmissionRequest)
          complete(response)
        }
      }
    }

  override def getQuestion: Route =
    path("questions" / Segment / Segment) { (category, questionId) =>
      pathEnd {
        get {
          handleExceptions(noSuchElementExceptionHandler) {
            logger.info(s"Making request for getting the question with category: $category and id: $questionId")
            val response = questionService.getQuestion(questionId, category)
            complete(response)
          }
        }
      }
    }

  override def updateQuestion(): Route = ???

  override def deleteQuestion(): Route = ???
}
