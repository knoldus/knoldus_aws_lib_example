package com.knoldus.aws.models.kinesis

case class BankAccountCreationRequest(
  accountOwner: String,
  accountType: String,
  securityCode: String,
  balance: Double
)
