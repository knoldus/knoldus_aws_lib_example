package com.knoldus.aws.services.kinesis

import com.knoldus.aws.models.kinesis.BankAccountEvent
import com.knoldus.kinesis.model.KinesisUserRecord
import com.knoldus.kinesis.producer.KinesisDataProducer
import com.typesafe.config.Config

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BankAccountEventPublisher(config: Config) {

  private val kinesisDataProducer = new KinesisDataProducer(config)

  def publishBankAccountEvent(stream: String, event: BankAccountEvent): Future[Boolean] = {
    val kinesisUserRecord = KinesisUserRecord(stream, event.accountNumber.toString, event.toJsonString)
    kinesisDataProducer.putRecord(kinesisUserRecord).map(_.isSuccessful)
  }

}
