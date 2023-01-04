package com.knoldus.aws.routes.s3

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.knoldus.aws.services.s3.DataMigrationService

class DataMigrationAPIImpl(dataMigrationService: DataMigrationService) extends DataMigrationAPI {

  val dataMigrationAPIRoutes: Route = uploadFileToS3 ~ retrieveFile ~ copyFile ~ deleteFile()

  override def uploadFileToS3: Route = ???

  override def retrieveFile: Route = ???

  override def copyFile: Route = ???

  override def deleteFile(): Route = ???
}
