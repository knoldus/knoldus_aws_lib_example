package com.knoldus.aws.routes.kinesis

import akka.http.scaladsl.server.Route
import com.knoldus.aws.services.kinesis.BankAccountEventGenerator

class BankAccountEventGeneratorRoutes(bankAccountEventGenerator: BankAccountEventGenerator) {

  def createBankAccount: Route = ???

  def creditBankAccount: Route = ???

  def debitBankAccount(): Route = ???
}
