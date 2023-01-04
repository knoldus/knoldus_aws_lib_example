package com.knoldus.s3.routes
import akka.http.scaladsl.server.Route
import com.knoldus.s3.service.DataMigrationService

class DataMigrationAPIImpl(dataMigrationService: DataMigrationService) extends DataMigrationAPI {

  //val dataMigrationAPIRoutes: Route = uploadFileToS3 ~ retrieveFile ~ copyFile ~ deleteFile()

  override def uploadFileToS3: Route = ???

  override def retrieveFile: Route = ???

  override def copyFile: Route = ???

  override def deleteFile(): Route = ???
}
