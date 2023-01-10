package com.knoldus.aws.services.kinesis

import com.knoldus.aws.models.kinesis.{ BankAccountCreationRequest, BankAccountEvent }

import java.util.UUID
import scala.concurrent.Future

class BankAccountEventGenerator(bankAccountEventPublisher: BankAccountEventPublisher) {

  def createBankAccount(bankAccountDetails: BankAccountCreationRequest): Future[BankAccountEvent] = ???

  def creditBankAccount(accountNumber: UUID, amountToCredit: Double): Future[BankAccountEvent] = ???

  def debitBankAccount(accountNumber: UUID, amountToDebit: Double): Future[BankAccountEvent] = ???

}
