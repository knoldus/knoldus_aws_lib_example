package com.knoldus.aws.models.s3

import play.api.libs.json.{ Format, JsSuccess, JsValue, Json }

import java.io.File

case class FileUploadRequest(filePath: File)

case class FileRetrieveRequest(fileName: String, key: String, versionId: Option[String])

object FileRetrieveRequest {

  implicit val format: Format[FileRetrieveRequest] = Json.format

  def apply(json: JsValue): Either[String, FileRetrieveRequest] =
    json.validate[FileRetrieveRequest] match {
      case JsSuccess(user, _) => Right(user)
      case e => Left(s"Failed to deserialize Question $e")
    }
}
