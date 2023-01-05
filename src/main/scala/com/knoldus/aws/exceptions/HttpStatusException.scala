package com.knoldus.aws.exceptions

import play.api.libs.json.{ Format, JsSuccess, JsValue, Json }

class HttpStatusException(msg: String, cause: Throwable) extends RuntimeException(msg, cause) {
  @SuppressWarnings(Array("NullParameter"))
  def this(msg: String) = this(msg, null) //scalastyle:ignore
  implicit def toJson(errorMsg: String): Converter = new Converter(errorMsg)
}

@SuppressWarnings(Array("IncorrectlyNamedExceptions"))
class Converter(errorMsg: String) extends HttpStatusException(errorMsg) {
  def convertToJson: JsValue = Json.parse(errorMsg)

  val result: Either[JsValue, NotFoundException] = convertToJson.validate[NotFoundException] match {
    case JsSuccess(value, _) => Right(value)
    case _ => Left(convertToJson)
  }
}
final case class NotFoundException(msg: String) extends Converter(msg)

object NotFoundException {
  implicit val defaultFormat: Format[NotFoundException] = Json.format

  def apply(json: JsValue): Either[String, NotFoundException] =
    json.validate[NotFoundException] match {
      case JsSuccess(notFoundException, _) => Right(notFoundException)
      case x => Left(s"Failed to deserialize NotFoundException $x")
    }
}

final case class BadRequestException(msg: String) extends Converter(msg)

object BadRequestException {
  implicit val defaultFormat: Format[BadRequestException] = Json.format

  def apply(json: JsValue): Either[String, BadRequestException] =
    json.validate[BadRequestException] match {
      case JsSuccess(badRequestException, _) => Right(badRequestException)
      case x => Left(s"Failed to deserialize BadRequestException $x")
    }
}

final class ServiceUnavailableException(msg: String) extends HttpStatusException(msg)
