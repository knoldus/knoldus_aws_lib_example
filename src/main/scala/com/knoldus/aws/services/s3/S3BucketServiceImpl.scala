package com.knoldus.aws.services.s3

import com.amazonaws.auth.{ AWSCredentialsProvider, DefaultAWSCredentialsProviderChain }
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.{ AmazonS3, AmazonS3ClientBuilder }
import com.knoldus.common.aws.CredentialsLookup
import com.knoldus.s3.models.{ Bucket, Configuration }
import com.knoldus.s3.services.S3Service
import com.knoldus.s3.services.S3Service.buildAmazonS3Client

class S3BucketServiceImpl(s3config: Configuration) extends S3BucketService with S3Service {

  /*
  override val amazonS3Client: AmazonS3 = {
    val credentials: AWSCredentialsProvider =
      CredentialsLookup.getCredentialsProvider(s3config.awsConfig.awsAccessKey, s3config.awsConfig.awsSecretKey)
    buildAmazonS3Client(s3config, credentials)
  }
   */

//  override val amazonS3Client: AmazonS3 = AmazonS3ClientBuilder
//    .standard()
//    .withEndpointConfiguration(
//      new EndpointConfiguration("http://localhost:4566", "us-east-1")
//    )
//    .withCredentials(new DefaultAWSCredentialsProviderChain())
//    .withPathStyleAccessEnabled(true)
//    .build()

  val amazonS3Client: AmazonS3 = AmazonS3ClientBuilder
    .standard()
    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1"))
    .build()

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
