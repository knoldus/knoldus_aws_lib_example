package com.knoldus.aws.models.sqs

import play.api.libs.json.{ Format, JsSuccess, JsValue, Json }

case class ReceiveMessageRequest(queueName: String)

object ReceiveMessageRequest {

  implicit val format: Format[ReceiveMessageRequest] = Json.format

  def apply(json: JsValue): Either[String, ReceiveMessageRequest] =
    json.validate[ReceiveMessageRequest] match {
      case JsSuccess(user, _) => Right(user)
      case e => Left(s"Failed to deserialize ReceiveMessageRequest $e")
    }
}
