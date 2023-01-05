package com.knoldus.aws.routes.s3

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import com.knoldus.aws.exceptions.NotFoundException
import com.knoldus.aws.models.s3.{S3Bucket, S3BucketResponse}
import com.knoldus.aws.services.s3.S3BucketService
import com.knoldus.aws.utils.Constants._
import com.knoldus.aws.utils.JsonSupport
import com.typesafe.scalalogging.LazyLogging

class S3BucketAPIImpl(s3BucketService: S3BucketService) extends S3BucketAPI with JsonSupport with LazyLogging {

  val S3BucketAPIRoutes: Route = createS3Bucket ~ searchS3Bucket ~ listAllBuckets ~ deleteS3Bucket()

  implicit val noSuchElementExceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: NoSuchElementException =>
      complete(StatusCodes.NotFound, e.getMessage)
  }

  override def createS3Bucket: Route =
    path("bucket" / "create") {
      pathEnd {
        (post & entity(as[S3Bucket])) { bucketCreationRequest =>
          logger.info("Making request for S3 bucket creation")
          val bucket = s3BucketService.createS3Bucket(bucketCreationRequest.bucketName)
          val response = S3BucketResponse(BUCKET_CREATED, bucket.name)
          complete(response)
        }
      }
    }

  override def deleteS3Bucket(): Route = path("bucket" / "delete") {
    pathEnd {
      (delete & entity(as[S3Bucket])) { bucketDeletionRequest =>
        logger.info("Making request for S3 bucket deletion")
        val response = s3BucketService.searchS3Bucket(bucketDeletionRequest.bucketName) match {
          case None => throw new NotFoundException(BUCKET_DOES_NOT_EXIST)
          case Some(bucket) =>
            s3BucketService.deleteS3Bucket(bucket)
            S3BucketResponse(BUCKET_DELETED, bucket.name)
        }
        complete(response)
      }
    }
  }

  override def searchS3Bucket: Route = path("bucket" / "search") {
    pathEnd {
      (get & entity(as[S3Bucket])){ searchBucketRequest =>
        handleExceptions(noSuchElementExceptionHandler) {
          logger.info(s"Making request for searching the S3 bucket.")
          val response = s3BucketService.searchS3Bucket(searchBucketRequest.bucketName) match {
            case Some(bucket) => S3BucketResponse(BUCKET_FOUND, bucket.name)
            case None => throw new NotFoundException(NO_BUCKETS_FOUND)
          }
          complete(response)
        }
      }
    }
  }

  override def listAllBuckets: Route = path("bucket" / "allBuckets") {
    pathEnd {
      get {
        handleExceptions(noSuchElementExceptionHandler) {
          logger.info(s"Making request for getting all the S3 buckets.")
          val response = s3BucketService.listAllBuckets match {
            case Some(buckets) => buckets.map { bucket =>
              S3Bucket(bucket.name)
            }
            case None => throw new NotFoundException(NO_BUCKETS_FOUND)
          }
          complete(response)
        }
      }
    }
  }
}
