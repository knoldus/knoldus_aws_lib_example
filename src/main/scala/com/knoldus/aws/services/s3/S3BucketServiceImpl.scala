package com.knoldus.aws.services.s3

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.knoldus.common.aws.CredentialsLookup
import com.knoldus.s3.models.Bucket
import com.knoldus.s3.services.S3Service
import com.knoldus.s3.services.S3Service.buildAmazonS3Client

class S3BucketServiceImpl extends S3BucketService with S3Service {

  override val amazonS3Client: AmazonS3 = {
    val credentials: AWSCredentialsProvider =
      CredentialsLookup.getCredentialsProvider(awsConfig.accessKey, awsConfig.secretKey)
    buildAmazonS3Client(config, credentials)
  }

  implicit val s3Service: S3Service = S3Service

  override def createS3Bucket(bucketName: String): Bucket =
    s3Service.getBucketByName(bucketName).getOrElse(s3Service.createBucket(bucketName, Some(config.awsConfig.region)))

  override def deleteS3Bucket(bucket: Bucket): Unit = {
    val summaries = bucket.objectSummaries().toList
    summaries foreach { o =>
      amazonS3Client.deleteObject(bucket.name, o.getKey)
    }
    bucket.destroy()
  }

  override def listAllBuckets: Option[List[Bucket]] =
    Option(s3Service.getAllBuckets.toList)

  override def listS3Bucket(name: String): Option[Bucket] =
    s3Service.getBucketByName(name)
}
