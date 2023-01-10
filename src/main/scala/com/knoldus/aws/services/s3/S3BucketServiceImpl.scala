package com.knoldus.aws.services.s3

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.knoldus.common.aws.CredentialsLookup
import com.knoldus.s3.models.{ Bucket, Configuration }
import com.knoldus.s3.services.S3Service
import com.knoldus.s3.services.S3Service.buildAmazonS3Client

class S3BucketServiceImpl(s3config: Configuration) extends S3BucketService with S3Service {

  override val amazonS3Client: AmazonS3 = {
    val credentials: AWSCredentialsProvider =
      CredentialsLookup.getCredentialsProvider(s3config.awsConfig.awsAccessKey, s3config.awsConfig.awsSecretKey)
    buildAmazonS3Client(s3config, credentials)
  }

  implicit val s3Service: S3Service = S3Service

  override def createS3Bucket(bucketName: String): Bucket =
    s3Service
      .getBucketByName(bucketName)
      .getOrElse(s3Service.createBucket(bucketName, Some(s3config.awsConfig.awsRegion)))

  override def deleteS3Bucket(bucket: Bucket): Unit = {
    val summaries = bucket.objectSummaries().toList
    summaries foreach { o =>
      amazonS3Client.deleteObject(bucket.name, o.getKey)
    }
    bucket.destroy()
  }

  override def listAllBuckets: Option[List[Bucket]] =
    Option(s3Service.getAllBuckets.toList)

  override def searchS3Bucket(name: String): Option[Bucket] =
    s3Service.getBucketByName(name)
}
