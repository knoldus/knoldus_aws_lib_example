package com.knoldus.aws.services.kinesis

import com.knoldus.aws.models.kinesis.{BankAccountCreationRequest, BankAccountEvent, CreateBankAccountEvent}
import com.typesafe.scalalogging.LazyLogging
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BankAccountEventGenerator(bankAccountEventPublisher: BankAccountEventPublisher) extends LazyLogging{
  val stream: String = bankAccountEventPublisher.config.getString("kinesis-data-stream-name")

  def createBankAccountEvent(bankAccountDetails: BankAccountCreationRequest): Future[BankAccountEvent] = {
    val newAccountNumber = UUID.randomUUID()
    val createBankAccountEvent = CreateBankAccountEvent(newAccountNumber, bankAccountDetails.accountOwner, bankAccountDetails.accountOwner, bankAccountDetails.securityCode, bankAccountDetails.initialBalance)
    val publishEventResultFuture = bankAccountEventPublisher.publishBankAccountEvent(stream, createBankAccountEvent)
    logger.info(s"Generated an event for creating a new bank account")
    checkAndGetPublishedEvent(publishEventResultFuture, createBankAccountEvent)
  }

  def creditBankAccountEvent(accountNumber: UUID, amountToCredit: Double): Future[BankAccountEvent] = ???

  def debitBankAccountEvent(accountNumber: UUID, amountToDebit: Double): Future[BankAccountEvent] = ???

  private def checkAndGetPublishedEvent(publishEventResult: Future[Boolean], event: BankAccountEvent): Future[BankAccountEvent] =
    publishEventResult.map { isEventPublished =>
      if (isEventPublished) {
        logger.info(s"Event published successfully to the $stream data stream")
        event
      }
      else {
        logger.error(s"Failure occurs publishing the event to $stream data stream")
        throw new Exception("Event not published")
      }
    }

}
