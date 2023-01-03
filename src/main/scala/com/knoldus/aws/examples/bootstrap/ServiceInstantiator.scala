package com.knoldus.aws.examples.bootstrap

import com.knoldus.aws.examples.models.QuestionTable
import com.knoldus.aws.examples.services.QuestionServiceImpl
import com.typesafe.config.Config

class ServiceInstantiator(conf: Config) {
  private val tableName = conf.getString("dynamodb.table.name")
  val questionTable: QuestionTable = QuestionTable(tableName)

  lazy val questionService = new QuestionServiceImpl(questionTable)
}
