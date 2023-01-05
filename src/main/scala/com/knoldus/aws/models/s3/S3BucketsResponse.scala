package com.knoldus.aws.models.s3

import play.api.libs.json.{Format, JsSuccess, JsValue, Json}

case class S3BucketsResponse(buckets: List[S3Bucket])

object S3BucketsResponse {

  implicit val format: Format[S3BucketsResponse] = Json.format

  def apply(json: JsValue): Either[String, S3BucketsResponse] =
    json.validate[S3BucketsResponse] match {
      case JsSuccess(user, _) => Right(user)
      case e => Left(s"Failed to deserialize Question $e")
    }
}
