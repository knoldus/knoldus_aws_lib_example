package com.knoldus.aws.services.kinesis

import com.knoldus.aws.models.kinesis.BankAccountEvent
import com.knoldus.kinesis.producer.KinesisDataProducer
import com.typesafe.config.Config

import scala.concurrent.Future

class BankAccountEventPublisher(config: Config) {

  val kinesisDataProducer = new KinesisDataProducer(config)

  def publishBankAccountEvent(stream: String, event: BankAccountEvent): Future[Boolean] = ???

}
