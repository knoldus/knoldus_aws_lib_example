package com.knoldus.aws.models.kinesis

import play.api.libs.json.{ Format, Json }
import java.util.UUID

sealed trait BankAccountEvent {
  def accountNumber: UUID
  def toJsonString: String
}

case class CreateBankAccountEvent(
  accountNumber: UUID,
  accountOwner: String,
  accountType: String,
  securityCode: String,
  balance: Double
) extends BankAccountEvent {

  override def toJsonString: String = Json.stringify(Json.toJson(this))
}

object CreateBankAccountEvent {

  implicit val format: Format[CreateBankAccountEvent] = Json.format
}

case class UpdateBankAccountEvent(accountNumber: UUID, updateType: String, amount: Double) extends BankAccountEvent {

  override def toJsonString: String = Json.stringify(Json.toJson(this))
}

object UpdateBankAccountEvent {
  implicit val format: Format[UpdateBankAccountEvent] = Json.format
}
