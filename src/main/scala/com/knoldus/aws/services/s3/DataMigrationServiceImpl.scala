package com.knoldus.aws.services.s3

import com.knoldus.s3.models.{ Bucket, DeletedObject, PutObjectResult, S3Object }
import com.knoldus.s3.services.S3Service

import java.io.File
import scala.util.{ Failure, Success, Try }

class DataMigrationServiceImpl extends DataMigrationService {

  implicit val s3Service: S3Service = S3Service

  override def uploadFileToS3(file: File, key: String)(implicit bucket: Bucket): PutObjectResult =
    s3Service.putObject(bucket, key, file)

  override def retrieveFile(key: String, versionId: Option[String])(implicit
    bucket: Bucket
  ): Either[Throwable, S3Object] =
    s3Service.getS3Object(bucket, key, versionId)

  override def copyFile(
    sourceBucketName: String,
    sourceKey: String,
    destinationBucketName: String,
    destinationKey: String
  ): Either[Throwable, PutObjectResult] =
    Try(s3Service.copyS3Object(sourceBucketName, sourceKey, destinationBucketName, destinationKey)) match {
      case Failure(exception) => Left(exception)
      case Success(putObjectResult) => Right(putObjectResult)
    }

  override def deleteFile(key: String)(implicit bucket: Bucket): Either[Throwable, DeletedObject] =
    Try(s3Service.deleteObject(bucket, key)) match {
      case Failure(exception) => Left(exception)
      case Success(deletedObject) => Right(deletedObject)
    }
}
