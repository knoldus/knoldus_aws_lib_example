package com.knoldus.s3.routes

import akka.http.scaladsl.server.Route

trait DataMigrationAPI {

  def uploadFileToS3: Route

  def retrieveFile: Route

  def copyFile: Route

  def deleteFile(): Route
}
