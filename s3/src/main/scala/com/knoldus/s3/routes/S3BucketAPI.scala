package com.knoldus.s3.routes

import akka.http.scaladsl.server.Route

trait S3BucketAPI {

  def createS3Bucket: Route

  def deleteS3Bucket(): Route

  def listS3Bucket: Route

  def listAllBuckets: Route

}
