package com.knoldus.aws.routes.sqs

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, HttpResponse, StatusCodes }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ ExceptionHandler, Route }
import akka.util.ByteString
import com.knoldus.aws.models.sqs.{ CreateQueueRequest, DeleteQueueRequest, SendMessageToFifoRequest }
import com.knoldus.aws.services.sqs.MessagingServiceImpl
import com.knoldus.aws.utils.Constants._
import com.knoldus.aws.utils.JsonSupport
import com.typesafe.scalalogging.LazyLogging

class MessagingAPIImpl(messagingServiceImpl: MessagingServiceImpl)
    extends MessagingAPI
    with JsonSupport
    with LazyLogging {

//  val routes: Route =
//    createQueue ~ listQueues ~ deleteQueue() ~ sendMsgToFIFOQueue ~ sendMsgToStandardQueue ~
//      sendMultipleMsgToFIFOQueue ~ sendMultipleMsgToStandardQueue ~ receiveMessage ~
//      receiveMultipleMessages ~ deleteMessage() ~ deleteMultipleMessages()

  implicit val noSuchElementExceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: NoSuchElementException =>
      complete(StatusCodes.NotFound, e.getMessage)
  }

  override def createQueue: Route =
    path("queue" / "create") {
      pathEnd {
        (post & entity(as[CreateQueueRequest])) { createQueueRequest =>
          logger.info("Making request for queue creation")
          val bucket = messagingServiceImpl.createNewQueue(createQueueRequest.queueName, createQueueRequest.queueType)
          bucket match {
            case Left(ex) =>
              complete(
                HttpResponse(
                  StatusCodes.InternalServerError,
                  entity = HttpEntity(ContentTypes.`application/json`, ByteString(s"Exception ${ex.getMessage}"))
                )
              )
            case Right(queue) =>
              complete(
                HttpResponse(
                  StatusCodes.OK,
                  entity = HttpEntity(ContentTypes.`application/json`, ByteString(queue.toString))
                )
              )
          }
        }
      }
    }

  override def listQueues: Route =
    path("queue" / "allQueues") {
      pathEnd {
        get {
          handleExceptions(noSuchElementExceptionHandler) {
            logger.info(s"Making request for getting all the S3 buckets.")
            val queueSeq = messagingServiceImpl.listingQueueNames
            if (queueSeq.isEmpty)
              complete(
                HttpResponse(
                  StatusCodes.NoContent,
                  entity = HttpEntity(ContentTypes.`application/json`, NO_QUEUES_FOUND)
                )
              )
            else
              complete(
                HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`application/json`, queueSeq.toString()))
              )
          }
        }
      }
    }

  override def deleteQueue(): Route =
    path("queue" / "delete") {
      pathEnd {
        (delete & entity(as[DeleteQueueRequest])) { deleteQueueRequest =>
          logger.info("Making request for S3 bucket deletion")
          messagingServiceImpl.searchQueueByName(deleteQueueRequest.queueName) match {
            case None =>
              complete(
                HttpResponse(
                  StatusCodes.InternalServerError,
                  entity = HttpEntity(ContentTypes.`application/json`, NO_QUEUES_FOUND)
                )
              )
            case Some(queue) =>
              messagingServiceImpl.deletingQueue(queue) match {
                case Left(ex) =>
                  complete(
                    HttpResponse(
                      StatusCodes.InternalServerError,
                      entity = HttpEntity(
                        ContentTypes.`application/json`,
                        s"The specified queue could not be deleted. : ${ex.getMessage}"
                      )
                    )
                  )
                case Right(_) =>
                  complete(
                    HttpResponse(StatusCodes.OK, entity = HttpEntity(ContentTypes.`application/json`, QUEUE_DELETED))
                  )
              }
          }
        }
      }
    }

//    override def sendMsgToFIFOQueue: Route = path("queue" / "fifo" / "sendMessage") {
//      pathEnd {
//        (post & entity(as[SendMessageToFifoRequest])) { sendMessageToFifoRequest =>
//          logger.info("Making request for sending message to fifo queue")
//          val bucket = messagingServiceImpl.createNewQueue(createQueueRequest.queueName, createQueueRequest.queueType)
//          bucket match {
//            case Left(ex) =>
//              complete(
//                HttpResponse(
//                  StatusCodes.InternalServerError,
//                  entity =
//                    HttpEntity(ContentTypes.`application/json`, ByteString(s"Exception ${ex.getMessage}"))
//                )
//              )
//            case Right(queue) =>
//              complete(
//                HttpResponse(
//                  StatusCodes.OK,
//                  entity = HttpEntity(ContentTypes.`application/json`, ByteString(queue.toString))
//                )
//              )
//          }
//        }
//      }
//    }
  //
  //    override def sendMsgToStandardQueue: Route = ???
  //
  //    override def sendMultipleMsgToFIFOQueue: Route = ???
  //
  //    override def sendMultipleMsgToStandardQueue: Route = ???
  //
  //    override def receiveMessage: Route = ???
  //
  //    override def receiveMultipleMessages: Route = ???
  //
  //    override def deleteMessage(): Route = ???
  //
  //    override def deleteMultipleMessages(): Route = ???
}
