package com.knoldus.aws.services.s3

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.knoldus.common.aws.CredentialsLookup
import com.knoldus.common.models.AWSConfig
import com.knoldus.s3.models.{ Bucket, Configuration, S3Config }
import com.knoldus.s3.services.S3Service
import com.knoldus.s3.services.S3Service.buildAmazonS3Client
import com.typesafe.config.Config

class S3BucketServiceImpl(conf: Config) extends S3BucketService with S3Service {

  val accessKey: String = conf.getString("aws-access-key")
  val secretKey: String = conf.getString("aws-secret-key")
  val region: String = conf.getString("aws-region")
  val serviceEndpoint: String = conf.getString("aws-serviceEndpoint")
  val configuration = Configuration(AWSConfig(accessKey, secretKey, region), S3Config(serviceEndpoint))

  override val amazonS3Client: AmazonS3 = {
    val credentials: AWSCredentialsProvider =
      CredentialsLookup.getCredentialsProvider(accessKey, secretKey)
    buildAmazonS3Client(configuration, credentials)
  }

  implicit val s3Service: S3Service = S3Service

  override def createS3Bucket(bucketName: String): Bucket =
    s3Service
      .getBucketByName(bucketName)
      .getOrElse(s3Service.createBucket(bucketName, Some(configuration.awsConfig.awsRegion)))

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
