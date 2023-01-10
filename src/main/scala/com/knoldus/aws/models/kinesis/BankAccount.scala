package com.knoldus.aws.models.kinesis

import play.api.libs.json.{ Format, Json }

import java.util.UUID

case class BankAccount(
  accountNumber: UUID,
  accountOwner: String,
  accountType: String,
  securityCode: String,
  balance: Double
)

object BankAccount {
  implicit val format: Format[BankAccount] = Json.format
}
