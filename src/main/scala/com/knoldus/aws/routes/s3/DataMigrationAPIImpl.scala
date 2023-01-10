package com.knoldus.aws.routes.s3

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.util.ByteString
import com.knoldus.aws.models.s3.{CopyObjectRequest, FileRetrieveRequest}
import com.knoldus.aws.services.s3.DataMigrationServiceImpl
import com.knoldus.aws.utils.JsonSupport
import com.knoldus.s3.models.Bucket
import com.typesafe.scalalogging.LazyLogging

class DataMigrationAPIImpl(dataMigrationService: DataMigrationServiceImpl)
    extends DataMigrationAPI
    with JsonSupport
    with LazyLogging {

  val dataMigrationAPIRoutes: Route = uploadFileToS3 ~ retrieveFile ~ copyFile ~ deleteFile()

  implicit val noSuchElementExceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: NoSuchElementException =>
      complete(StatusCodes.NotFound, e.getMessage)
  }

  override def uploadFileToS3: Route = ???
  //    path("bucket" / "create") {
  //      pathEnd {
  //        (post & entity(as[Multipart.FormData])) { fileUploadRequest =>
  //          logger.info("Making request for uploading file to S3 bucket")
  //          fileUploadRequest match {
  //            case data: FormData => data
  //          }
  //
  //          complete(response)
  //        }
  //      }
  //    }

  override def retrieveFile: Route =
    path("bucket" / "retrieveObject") {
      pathEnd {
        (get & entity(as[FileRetrieveRequest])) { fileRetrieveRequest =>
          handleExceptions(noSuchElementExceptionHandler) {
            logger.info(s"Making request for searching the S3 bucket.")
            implicit val bucket: Bucket = Bucket(fileRetrieveRequest.bucketName)
            dataMigrationService.retrieveFile(fileRetrieveRequest.key, fileRetrieveRequest.versionId) match {
              case Left(ex) =>
                complete(
                  HttpResponse(
                    StatusCodes.NotFound,
                    entity = HttpEntity(ContentTypes.`application/json`, ByteString("S3 Object not found."))
                  )
                )
              case Right(s3Object) =>
                complete(
                  HttpResponse(
                    StatusCodes.OK,
                    entity = HttpEntity(ContentTypes.`application/json`, ByteString(s3Object.toString))
                  )
                )
            }
          }
        }
      }
    }

  override def copyFile: Route =
    path("bucket" / "copyObject") {
      pathEnd {
        (get & entity(as[CopyObjectRequest])) { copyObjectRequest =>
          handleExceptions(noSuchElementExceptionHandler) {
            logger.info(s"Making request for copying object from the S3 bucket.")
            dataMigrationService.copyFile(
              copyObjectRequest.sourceBucketName,
              copyObjectRequest.sourceKey,
              copyObjectRequest.destinationBucketName,
              copyObjectRequest.destinationKey
            ) match {
              case Left(ex) =>
                complete(
                  HttpResponse(
                    StatusCodes.NotFound,
                    entity = HttpEntity(
                      ContentTypes.`application/json`,
                      ByteString(s"Unable to copy S3 object: ${ex.getMessage}")
                    )
                  )
                )
              case Right(putObjectResult) =>
                complete(
                  HttpResponse(
                    StatusCodes.OK,
                    entity = HttpEntity(ContentTypes.`application/json`, ByteString(putObjectResult.toString))
                  )
                )
            }
          }
        }
      }
    }

  override def deleteFile(): Route = ???
}
