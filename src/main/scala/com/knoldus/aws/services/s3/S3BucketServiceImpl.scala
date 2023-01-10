package com.knoldus.aws.services.s3

import com.amazonaws.auth.{ AWSCredentialsProvider, DefaultAWSCredentialsProviderChain }
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.{ AmazonS3, AmazonS3ClientBuilder }
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
  val s3ServiceEndpoint: String = conf.getString("aws-serviceEndpoint")
  val configuration = Configuration(AWSConfig(accessKey, secretKey, region), S3Config(s3ServiceEndpoint))

  /*  override val amazonS3Client: AmazonS3 = {
    val credentials: AWSCredentialsProvider =
      CredentialsLookup.getCredentialsProvider(accessKey, secretKey)
    buildAmazonS3Client(configuration, credentials)
  }*/
  override val amazonS3Client: AmazonS3 = AmazonS3ClientBuilder
    .standard()
    .withEndpointConfiguration(
      new EndpointConfiguration("http://localhost:4566", "us-east-1")
    )
    .withCredentials(new DefaultAWSCredentialsProviderChain())
    .withPathStyleAccessEnabled(true)
    .build()

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
