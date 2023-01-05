package com.knoldus.aws.examples.services

import com.knoldus.aws.examples.models.{ Question, QuestionTable, QuestionUpdate }
import com.knoldus.aws.examples.utils.Constants
import com.typesafe.scalalogging.LazyLogging
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class QuestionServiceImpl(questionTable: QuestionTable) extends QuestionService with LazyLogging {

  override def submitQuestion(question: Question): Future[Option[String]] =
    Future {
      questionTable.put(question.record) match {
        case Right(id) =>
          logger.info("Question submitted successfully")
          Some(Constants.submitQuestionResponse(id))
        case Left(message) =>
          logger.error(s"Failed to submitted the question: $message")
          None
      }
    }

  override def getQuestion(id: String, category: String): Future[Option[Question]] =
    Future {
      logger.info(s"Getting the question with id: $id and category: $category")
      questionTable.retrieve(category, id) match {
        case Some(record) =>
          logger.info("Question found")
          Question(Json.parse(record.json)) match {
            case Left(message) =>
              logger.error(message)
              None
            case Right(question) => Some(question)
          }
        case None =>
          logger.info("Question not found")
          throw new NoSuchElementException(Constants.noQuestionFoundResponse)
      }
    }

  override def updateQuestion(id: String, category: String, questionUpdate: QuestionUpdate): Future[Option[String]] =
    Future {
      def updateQuestionEntity(): Question = {
        val updatedTitle = questionUpdate.title
        val updatedDescription = questionUpdate.description
        Question(id, updatedTitle, category, updatedDescription)
      }
      logger.info(s"Updating the question with id: $id and category: $category")
      questionTable.update(category, id, updateQuestionEntity().record) match {
        case Right(_) =>
          Some(Constants.updateQuestionResponse)
        case Left(message) =>
          logger.error(s"Failed to update the question, reason: $message")
          throw new NoSuchElementException(Constants.noQuestionFoundResponse)
      }
    }

  override def deleteQuestion(id: String, category: String): Future[Unit] =
    Future {
      logger.info(s"Deleting the question with id: $id and category: $category")
      questionTable.delete(category, id)
    }
}
