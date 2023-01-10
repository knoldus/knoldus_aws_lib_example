package com.knoldus.aws.services.sqs

import akka.http.javadsl.server.Route
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.DeleteQueueResult
import com.knoldus.common.aws.CredentialsLookup
import com.knoldus.sqs.models.QueueType.QueueType
import com.knoldus.sqs.models.{ Queue, SQSConfig }
import com.knoldus.sqs.services.SQSService
import com.knoldus.sqs.services.SQSService.buildAmazonSQSClient

import scala.concurrent.Future

class MessagingServiceImpl(config: SQSConfig) extends MessagingService with SQSService {

  override val amazonSQSClient: AmazonSQS = {
    val credentials: AWSCredentialsProvider =
      CredentialsLookup.getCredentialsProvider(config.awsConfig.awsAccessKey, config.awsConfig.awsSecretKey)
    buildAmazonSQSClient(config, credentials)
  }

  implicit val sqsService: SQSService = SQSService

  override def createNewQueue(queueName: String, queueType: QueueType): Either[Throwable, Queue] =
    sqsService.createQueue(queueName, queueType)

  override def deletingQueue(queue: Queue): Future[DeleteQueueResult] =
    sqsService.deleteQueue(queue)

  override def listingQueues: Seq[Queue] = sqsService.listQueues
}
