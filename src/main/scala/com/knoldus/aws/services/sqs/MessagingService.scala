package com.knoldus.aws.services.sqs

import akka.http.javadsl.server.Route
import com.amazonaws.services.sqs.model.DeleteQueueResult
import com.knoldus.sqs.models.Queue
import com.knoldus.sqs.models.QueueType.QueueType

import scala.concurrent.Future

trait MessagingService {

  def createNewQueue(queueName: String, queueType: QueueType): Either[Throwable, Queue]

  def listingQueues: Seq[Queue]

  def deletingQueue(queue: Queue): Future[DeleteQueueResult]

//  def sendMsgToFIFOQueue
//
//  def sendMsgToStandardQueue
//
//  def sendMultipleMsgToFIFOQueue
//
//  def sendMultipleMsgToStandardQueue
//
//  def receiveMessage
//
//  def receiveMultipleMessages
//
//  def deleteMessage()
//
//  def deleteMultipleMessages()

}
