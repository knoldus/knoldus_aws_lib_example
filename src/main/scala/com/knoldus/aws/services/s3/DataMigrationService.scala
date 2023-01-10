package com.knoldus.aws.services.s3

import com.knoldus.s3.models.{ DeletedObject, PutObjectResult, S3Object }

import java.io.File

trait DataMigrationService {

  def uploadFileToS3(file: File, key: String): PutObjectResult

  def retrieveFile(key: String, versionId: Option[String]): Either[Throwable, S3Object]

  def copyFile(
    sourceBucketName: String,
    sourceKey: String,
    destinationBucketName: String,
    destinationKey: String
  ): PutObjectResult

  def deleteFile(key: String): DeletedObject

}
object DataMigrationService
