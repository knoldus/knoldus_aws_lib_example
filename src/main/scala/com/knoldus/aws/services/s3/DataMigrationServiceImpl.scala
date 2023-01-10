package com.knoldus.aws.services.s3

import com.knoldus.s3.models.{ Bucket, DeletedObject, PutObjectResult, S3Object }
import com.knoldus.s3.services.S3Service

import java.io.File
import scala.util.{ Failure, Success, Try }

class DataMigrationServiceImpl(bucket: Bucket) extends DataMigrationService {

  implicit val s3Service: S3Service = S3Service

  override def uploadFileToS3(file: File, key: String): PutObjectResult =
    s3Service.putObject(bucket, key, file)

  override def retrieveFile(key: String, versionId: Option[String]): Either[Throwable, S3Object] =
    Try(s3Service.getS3Object(bucket, key, versionId)) match {
      case Failure(exception) => Left(exception)
      case Success(s3Object) => Right(s3Object)
    }

  override def copyFile(
    sourceBucketName: String,
    sourceKey: String,
    destinationBucketName: String,
    destinationKey: String
  ): PutObjectResult =
    s3Service.copyS3Object(sourceBucketName, sourceKey, destinationBucketName, destinationKey)

  override def deleteFile(key: String): DeletedObject = s3Service.deleteObject(bucket, key)
}
