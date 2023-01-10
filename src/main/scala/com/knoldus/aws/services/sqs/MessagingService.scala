package com.knoldus.aws.services.sqs

import akka.http.javadsl.server.Route
import com.amazonaws.services.sqs.model.DeleteQueueResult
import com.knoldus.sqs.models.{ Message, Queue }
import com.knoldus.sqs.models.QueueType.QueueType

import scala.concurrent.Future

trait MessagingService {

  def createNewQueue(queueName: String, queueType: QueueType): Either[Throwable, Queue]

  def listingQueues: Seq[Queue]

  def deletingQueue(queue: Queue): Either[Throwable, String]

  def sendMessageToQueue(
    queue: Queue,
    messageBody: String,
    messageGroupId: Option[String] = None,
    messageAttributes: Option[Map[String, String]] = None,
    delaySeconds: Option[Int] = None
  ): Either[Throwable, String]

  def sendMultipleMessagesToQueue(
    queue: Queue,
    messageBodies: Seq[String],
    messageGroupId: Option[String] = None,
    messageAttributes: Option[Map[String, String]] = None,
    delaySeconds: Option[Int] = None
  ): Either[Throwable, String]

  def receiveMessage(queue: Queue, maxNumberOfMessages: Int, waitForSeconds: Int): Either[Throwable, Seq[Message]]

  //def deleteMessage(queue: Queue, messageId: String)
  //
  //  def deleteMultipleMessages()

}
