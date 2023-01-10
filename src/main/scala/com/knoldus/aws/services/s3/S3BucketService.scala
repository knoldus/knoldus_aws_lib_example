package com.knoldus.aws.services.s3

import com.knoldus.s3.models.Bucket

trait S3BucketService {

  def createS3Bucket(bucketName: String): Bucket

  def deleteS3Bucket(bucket: Bucket): Unit

  def listAllBuckets: Option[List[Bucket]]

  def searchS3Bucket(name: String): Option[Bucket]
}
