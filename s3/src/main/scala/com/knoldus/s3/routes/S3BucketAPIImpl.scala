package com.knoldus.s3.routes
import akka.http.scaladsl.server.Route
import com.knoldus.s3.service.S3BucketService

class S3BucketAPIImpl(s3BucketService: S3BucketService) extends S3BucketAPI {

  //val S3BucketAPIRoutes: Route = createS3Bucket ~ listS3Bucket ~ listAllBuckets ~ deleteS3Bucket()

  override def createS3Bucket: Route = ???

  override def deleteS3Bucket(): Route = ???

  override def listS3Bucket: Route = ???

  override def listAllBuckets: Route = ???
}
