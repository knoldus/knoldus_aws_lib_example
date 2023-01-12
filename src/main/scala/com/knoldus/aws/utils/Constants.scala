package com.knoldus.aws.utils

object Constants {

  final val ZERO = 0
  final val TEN = 10

  final val BUCKET_CREATED = "The S3 bucket has been successfully created."
  final val BUCKET_DELETED = "The S3 bucket has been successfully deleted."
  final val BUCKET_FOUND = "The specified S3 bucket exists."
  final val BUCKET_NOT_FOUND = "The specified S3 bucket does not exist."
  final val NO_BUCKETS_FOUND = "No S3 buckets found for specified S3 configuration."
  final val OBJECT_UPLOADED = "The specified object is uploaded to the S3 bucket successfully."

  final val QUEUE_DELETED = "The queue has been successfully deleted."
  final val MESSAGE_SENT = "The message has been successfully sent."
  final val MESSAGES_SENT = "The messages have been successfully sent."
  final val MESSAGE_DELETED = "The message has been successfully deleted."
  final val MESSAGES_DELETED = "All the messages has been successfully deleted."
  final val QUEUE_NOT_FOUND = "The specified queue is does not exist."
  final val NO_QUEUES_FOUND = "No queues found for specified SQS configuration."
}
