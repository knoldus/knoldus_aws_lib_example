package com.knoldus.aws.examples.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{ ExceptionHandler, Route }
import akka.http.scaladsl.server.Directives._
import com.knoldus.aws.examples.models.{ Question, QuestionUpdate }
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
    path("questions" / Segment / Segment) { (questionId, category) =>
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

  override def updateQuestion(): Route =
    path("questions" / "update" / Segment / Segment) { (questionId, category) =>
      pathEnd {
        (put & entity(as[QuestionUpdate])) { questionUpdateRequest =>
          handleExceptions(noSuchElementExceptionHandler) {
            logger.info("Making request to update the question")
            val response = questionService.updateQuestion(questionId, category, questionUpdateRequest)
            complete(response)
          }
        }
      }
    }

  override def deleteQuestion(): Route = ???
}
