package com.knoldus.aws.examples.bootstrap

import akka.actor.ActorSystem
import com.knoldus.aws.examples.models.QuestionTable
import com.knoldus.aws.examples.services.QuestionServiceImpl
import com.typesafe.config.Config

import scala.concurrent.ExecutionContext

class ServiceInstantiator(
  conf: Config
)(implicit actorSystem: ActorSystem) {
  implicit val ec: ExecutionContext = actorSystem.dispatcher
  private val tableName = conf.getString("dynamodb.table.name")
  val questionTable: QuestionTable = QuestionTable(tableName)

  lazy val questionService = new QuestionServiceImpl(questionTable)
}
