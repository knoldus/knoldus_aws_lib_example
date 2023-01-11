package com.knoldus.aws.utils

object Constants {

  final val EMPTY_STRING = ""
  final val FIFO = ".fifo"

  final val BUCKET_CREATED = "The S3 bucket has been successfully created."
  final val BUCKET_DELETED = "The S3 bucket has been successfully deleted."
  final val BUCKET_FOUND = "The specified S3 bucket exists."
  final val BUCKET_DOES_NOT_EXIST = "The specified S3 bucket does not exist."
  final val NO_BUCKETS_FOUND = "No S3 buckets found for specified S3 configuration."

  final val QUEUE_DELETED = "The queue has been successfully deleted."
  final val MESSAGE_SENT = "The message has been successfully sent."
  final val MESSAGES_SENT = "The messages have been successfully sent."
  final val MESSAGE_DELETED = "The message has been successfully deleted."
  final val NO_QUEUES_FOUND = "No queues found for specified SQS configuration."

  final val ZERO = 0
  final val TEN = 10

}
