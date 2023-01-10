package com.knoldus.aws.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.knoldus.aws.models.s3.{ CopyObjectRequest, FileRetrieveRequest, S3Bucket, S3BucketResponse }

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val bucketFormat: RootJsonFormat[S3Bucket] = jsonFormat1(S3Bucket.apply)

  implicit val s3BucketResponseFormat: RootJsonFormat[S3BucketResponse] = jsonFormat2(S3BucketResponse.apply)

  implicit val fileRetrieveRequestFormat: RootJsonFormat[FileRetrieveRequest] = jsonFormat4(FileRetrieveRequest.apply)

  implicit val copyObjectRequestFormat: RootJsonFormat[CopyObjectRequest] = jsonFormat4(CopyObjectRequest.apply)
}
