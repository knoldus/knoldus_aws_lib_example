package com.knoldus.s3.service

import com.knoldus.s3.models.Bucket

trait S3BucketService {

  def createS3Bucket(bucketName: String): Bucket

  def deleteS3Bucket(bucket: Bucket): Unit

  def listAllBuckets: Option[List[Bucket]]

  def listS3Bucket(name: String): Option[Bucket]

}
