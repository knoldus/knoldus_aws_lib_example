package com.knoldus.aws.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.knoldus.aws.models.s3.{
  CopyObjectRequest,
  FileRetrieveRequest,
  ObjectDeletionRequest,
  S3Bucket,
  S3BucketResponse
}
import com.knoldus.aws.models.sqs.{
  CreateQueueRequest,
  DeleteQueueRequest,
  MessageResponse,
  ReceiveMessageRequest,
  SendMessageToFifoRequest,
  SendMessagesToStandardRequest
}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val bucketFormat: RootJsonFormat[S3Bucket] = jsonFormat1(S3Bucket.apply)

  implicit val s3BucketResponseFormat: RootJsonFormat[S3BucketResponse] = jsonFormat2(S3BucketResponse.apply)

  implicit val fileRetrieveRequestFormat: RootJsonFormat[FileRetrieveRequest] = jsonFormat4(FileRetrieveRequest.apply)

  implicit val copyObjectRequestFormat: RootJsonFormat[CopyObjectRequest] = jsonFormat4(CopyObjectRequest.apply)

  implicit val objectDeletionRequestFormat: RootJsonFormat[ObjectDeletionRequest] = jsonFormat2(
    ObjectDeletionRequest.apply
  )

  implicit val createQueueRequestFormat: RootJsonFormat[CreateQueueRequest] = jsonFormat2(CreateQueueRequest.apply)

  implicit val deleteQueueRequestFormat: RootJsonFormat[DeleteQueueRequest] = jsonFormat1(DeleteQueueRequest.apply)

  implicit val ReceiveMessageRequestFormat: RootJsonFormat[ReceiveMessageRequest] = jsonFormat1(
    ReceiveMessageRequest.apply
  )

  implicit val MessageResponseFormat: RootJsonFormat[MessageResponse] = jsonFormat2(MessageResponse.apply)

  implicit val SendMessagesToStandardRequestFormat: RootJsonFormat[SendMessagesToStandardRequest] = jsonFormat3(
    SendMessagesToStandardRequest.apply
  )

  implicit val sendMessageToFifoRequestFormat: RootJsonFormat[SendMessageToFifoRequest] = jsonFormat5(
    SendMessageToFifoRequest.apply
  )
}
