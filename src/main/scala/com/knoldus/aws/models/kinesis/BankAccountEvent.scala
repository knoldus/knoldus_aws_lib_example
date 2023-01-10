package com.knoldus.aws.models.kinesis

import play.api.libs.json.{Format, Json}
import java.util.UUID

sealed trait BankAccountEvent {
  def accountNumber: UUID
}

case class CreateBankAccount(accountNumber: UUID, accountOwner: String, securityCode: String, balance: Double) extends BankAccountEvent

object CreateBankAccount {
  implicit val format: Format[CreateBankAccount] = Json.format
}

case class UpdateBankAccount(accountNumber: UUID, updatedBalance: Double) extends BankAccountEvent

object UpdateBankAccount {
  implicit val format: Format[UpdateBankAccount] = Json.format
}
