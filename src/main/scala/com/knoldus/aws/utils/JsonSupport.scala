package com.knoldus.aws.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.knoldus.aws.models.s3.{ FileRetrieveRequest, S3Bucket, S3BucketResponse }

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val bucketFormat: RootJsonFormat[S3Bucket] = jsonFormat1(S3Bucket.apply)

  implicit val s3BucketResponseFormat: RootJsonFormat[S3BucketResponse] = jsonFormat2(S3BucketResponse.apply)

  implicit val fileRetrieveRequestFormat: RootJsonFormat[FileRetrieveRequest] = jsonFormat3(FileRetrieveRequest.apply)
}
