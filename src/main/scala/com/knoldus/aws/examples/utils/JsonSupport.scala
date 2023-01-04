package com.knoldus.aws.examples.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.aws.examples.models.Question
import spray.json._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val questionFormat: RootJsonFormat[Question] = jsonFormat4(Question.apply)
}
