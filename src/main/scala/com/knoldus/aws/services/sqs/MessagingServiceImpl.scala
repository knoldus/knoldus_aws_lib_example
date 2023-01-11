package com.knoldus.aws.services.sqs

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.QueueNameExistsException
import com.knoldus.aws.utils.Constants._
import com.knoldus.common.aws.CredentialsLookup
import com.knoldus.sqs.models.QueueType.QueueType
import com.knoldus.sqs.models.{ Message, Queue, QueueType, SQSConfig }
import com.knoldus.sqs.services.SQSService
import com.knoldus.sqs.services.SQSService.buildAmazonSQSClient

import scala.util.{ Failure, Success, Try }

class MessagingServiceImpl(config: SQSConfig) extends MessagingService with SQSService {

  override val amazonSQSClient: AmazonSQS = {
    val credentials: AWSCredentialsProvider =
      CredentialsLookup.getCredentialsProvider(config.awsConfig.awsAccessKey, config.awsConfig.awsSecretKey)
    buildAmazonSQSClient(config, credentials)
  }

  implicit val sqsService: SQSService = SQSService

  override def createNewQueue(queueName: String, queueType: QueueType): Either[Throwable, Queue] =
    sqsService.createQueue(queueName, queueType) match {
      case Left(ex) => Left(ex)
      case Right(value) => Right(value)
    }

  override def deletingQueue(queue: Queue): Either[Throwable, String] =
    Try(sqsService.deleteQueue(queue)) match {
      case Failure(exception) => Left(exception)
      case Success(_) => Right(QUEUE_DELETED)
    }

  override def listingQueues: Seq[Queue] = sqsService.listQueues

  override def listingQueueNames: Seq[String] = sqsService.listQueuesByName

  override def searchQueueByName(queueName: String): Option[Queue] = sqsService.findQueueByName(queueName)

  override def sendMessageToQueue(
    queue: Queue,
    messageBody: String,
    messageGroupId: Option[String],
    messageAttributes: Option[Map[String, String]],
    delaySeconds: Option[Int]
  ): Either[Throwable, String] =
    Try(
      sqsService.sendMessage(
        queue: Queue,
        messageBody: String,
        messageGroupId: Option[String],
        messageAttributes: Option[Map[String, String]],
        delaySeconds: Option[Int]
      )
    ) match {
      case Failure(exception) => Left(exception)
      case Success(_) => Right(MESSAGE_SENT)
    }

  override def sendMultipleMessagesToQueue(
    queue: Queue,
    messageBodies: Seq[String],
    messageGroupId: Option[String],
    messageAttributes: Option[Map[String, String]],
    delaySeconds: Option[Int]
  ): Either[Throwable, String] =
    Try(
      sqsService.sendMessages(
        queue: Queue,
        messageBodies: Seq[String],
        messageGroupId: Option[String],
        messageAttributes: Option[Map[String, String]],
        delaySeconds: Option[Int]
      )
    ) match {
      case Failure(exception) => Left(exception)
      case Success(_) => Right(MESSAGES_SENT)
    }

  override def receiveMessage(
    queue: Queue,
    maxNumberOfMessages: Int = TEN,
    waitForSeconds: Int = ZERO
  ): Either[Throwable, Seq[Message]] =
    Try(sqsService.receiveMessages(queue, maxNumberOfMessages, waitForSeconds)) match {
      case Failure(exception) => Left(exception)
      case Success(value) => Right(value)
    }

  // override def deleteMessage(queue: Queue, messageId: String): Unit = ???

}
