package com.knoldus.aws.routes.s3

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.util.ByteString
import com.knoldus.aws.models.s3.FileRetrieveRequest
import com.knoldus.aws.services.s3.DataMigrationService
import com.knoldus.aws.utils.JsonSupport
import com.typesafe.scalalogging.LazyLogging

class DataMigrationAPIImpl(dataMigrationService: DataMigrationService)
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
    path("bucket" / "retrieve") {
      pathEnd {
        (get & entity(as[FileRetrieveRequest])) { fileRetrieveRequest =>
          handleExceptions(noSuchElementExceptionHandler) {
            logger.info(s"Making request for searching the S3 bucket.")
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

  override def copyFile: Route = ???

  override def deleteFile(): Route = ???
}
