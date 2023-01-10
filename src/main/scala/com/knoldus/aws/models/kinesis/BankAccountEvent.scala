package com.knoldus.aws.models.kinesis

import play.api.libs.json.{ Format, Json }
import java.util.UUID

sealed trait BankAccountEvent {
  def accountNumber: UUID
}

case class CreateBankAccountEvent(
  accountNumber: UUID,
  accountOwner: String,
  accountType: String,
  securityCode: String,
  balance: Double
) extends BankAccountEvent

object CreateBankAccount {
  implicit val format: Format[CreateBankAccountEvent] = Json.format
}

case class UpdateBankAccountEvent(accountNumber: UUID, updatedBalance: Double) extends BankAccountEvent

object UpdateBankAccount {
  implicit val format: Format[UpdateBankAccountEvent] = Json.format
}
