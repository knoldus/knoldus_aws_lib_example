package com.knoldus.aws.services.s3

import com.knoldus.s3.models.{ Bucket, DeletedObject, PutObjectResult, S3Object }

import java.io.File

trait DataMigrationService {

  def uploadFileToS3(file: File, key: String)(implicit bucket: Bucket): PutObjectResult

  def retrieveFile(key: String, versionId: Option[String])(implicit bucket: Bucket): Either[Throwable, S3Object]

  def copyFile(
    sourceBucketName: String,
    sourceKey: String,
    destinationBucketName: String,
    destinationKey: String
  ): Either[Throwable, PutObjectResult]

  def deleteFile(key: String)(implicit bucket: Bucket): Either[Throwable, DeletedObject]
}
