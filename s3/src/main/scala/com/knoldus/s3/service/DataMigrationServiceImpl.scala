package com.knoldus.s3.service

import com.knoldus.s3.models.{Bucket, DeletedObject, PutObjectResult, S3Object}
import com.knoldus.s3.services.S3Service
import java.io.File

class DataMigrationServiceImpl(bucket: Bucket) extends DataMigrationService {

  implicit val s3Service: S3Service = S3Service

  override def uploadFileToS3(file: File, key: String): PutObjectResult = {
    s3Service.putObject(bucket,key,file)
  }

  override def retrieveFile(key: String, versionId: Option[String]): S3Object = {
    s3Service.getS3Object(bucket, key, versionId)
  }

  override def copyFile(sourceBucketName: String, sourceKey: String, destinationBucketName: String, destinationKey: String): PutObjectResult = {
    s3Service.copyS3Object(sourceBucketName, sourceKey, destinationBucketName, destinationKey)
  }

  override def deleteFile(key: String): DeletedObject = s3Service.deleteObject(bucket,key)
}
