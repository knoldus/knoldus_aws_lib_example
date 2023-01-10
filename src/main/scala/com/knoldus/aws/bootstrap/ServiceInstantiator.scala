package com.knoldus.aws.bootstrap

import com.knoldus.aws.models.dynamodb.QuestionTable
import com.knoldus.aws.routes.s3.{ DataMigrationAPIImpl, S3BucketAPIImpl }
import com.knoldus.aws.services.dynamodb.QuestionServiceImpl
import com.knoldus.aws.services.s3.{
  DataMigrationService,
  DataMigrationServiceImpl,
  S3BucketService,
  S3BucketServiceImpl
}
import com.softwaremill.macwire.wire
import com.typesafe.config.Config

class ServiceInstantiator(conf: Config) {
  //private val tableName = conf.getString("dynamodb-table-name")
  // val questionTable: QuestionTable = QuestionTable(tableName)

  //lazy val questionService = new QuestionServiceImpl(questionTable)
  lazy val s3BucketService = new S3BucketServiceImpl(conf)
  lazy val S3BucketAPIImpl: S3BucketAPIImpl = wire[S3BucketAPIImpl]
  lazy val dataMigrationServiceImpl: DataMigrationServiceImpl = wire[DataMigrationServiceImpl]
  lazy val dataMigrationAPIImpl: DataMigrationAPIImpl = wire[DataMigrationAPIImpl]

}
