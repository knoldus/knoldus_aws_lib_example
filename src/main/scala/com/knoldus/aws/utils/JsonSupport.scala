package com.knoldus.aws.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.knoldus.aws.models.s3.{
  CopyObjectRequest,
  ObjectDeletionRequest,
  RetrieveObjectRequest,
  S3Bucket,
  S3BucketResponse
}
import com.knoldus.aws.models.sqs.{
  CreateQueueRequest,
  DeleteQueueRequest,
  SendMessageToFifoRequest,
  SendMessageToStandardRequest,
  SendMessagesToFifoRequest
}
import com.knoldus.sqs.models.Queue

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val bucketFormat: RootJsonFormat[S3Bucket] = jsonFormat1(S3Bucket.apply)

  implicit val s3BucketResponseFormat: RootJsonFormat[S3BucketResponse] = jsonFormat2(S3BucketResponse.apply)

  implicit val fileRetrieveRequestFormat: RootJsonFormat[RetrieveObjectRequest] = jsonFormat4(
    RetrieveObjectRequest.apply
  )

  implicit val copyObjectRequestFormat: RootJsonFormat[CopyObjectRequest] = jsonFormat4(CopyObjectRequest.apply)

  implicit val objectDeletionRequestFormat: RootJsonFormat[ObjectDeletionRequest] = jsonFormat2(
    ObjectDeletionRequest.apply
  )

  implicit val createQueueRequestFormat: RootJsonFormat[CreateQueueRequest] = jsonFormat2(CreateQueueRequest.apply)

  implicit val deleteQueueRequestFormat: RootJsonFormat[DeleteQueueRequest] = jsonFormat1(DeleteQueueRequest.apply)

  implicit val sendMessageToFifoRequestFormat: RootJsonFormat[SendMessageToFifoRequest] = jsonFormat5(
    SendMessageToFifoRequest.apply
  )

  implicit val sendMessageToStandardRequestFormat: RootJsonFormat[SendMessageToStandardRequest] = jsonFormat3(
    SendMessageToStandardRequest.apply
  )

  implicit val sendMessagesToFifoRequestFormat: RootJsonFormat[SendMessagesToFifoRequest] = jsonFormat5(
    SendMessagesToFifoRequest.apply
  )
}
