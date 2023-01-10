package com.knoldus.aws.models.s3

import play.api.libs.json.{ Format, JsSuccess, JsValue, Json }

import java.io.File

case class FileUploadRequest(filePath: File)

case class FileRetrieveRequest(bucketName: String, fileName: String, key: String, versionId: Option[String])

object FileRetrieveRequest {

  implicit val format: Format[FileRetrieveRequest] = Json.format

  def apply(json: JsValue): Either[String, FileRetrieveRequest] =
    json.validate[FileRetrieveRequest] match {
      case JsSuccess(user, _) => Right(user)
      case e => Left(s"Failed to deserialize Question $e")
    }
}

case class CopyObjectRequest(
  sourceBucketName: String,
  sourceKey: String,
  destinationBucketName: String,
  destinationKey: String
)

object CopyObjectRequest {

  implicit val format: Format[CopyObjectRequest] = Json.format

  def apply(json: JsValue): Either[String, CopyObjectRequest] =
    json.validate[CopyObjectRequest] match {
      case JsSuccess(user, _) => Right(user)
      case e => Left(s"Failed to deserialize Question $e")
    }
}

case class ObjectDeletionRequest(bucketName: String, key: String)

object ObjectDeletionRequest {

  implicit val format: Format[ObjectDeletionRequest] = Json.format

  def apply(json: JsValue): Either[String, ObjectDeletionRequest] =
    json.validate[ObjectDeletionRequest] match {
      case JsSuccess(user, _) => Right(user)
      case e => Left(s"Failed to deserialize Question $e")
    }
}
