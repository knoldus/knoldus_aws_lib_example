package com.knoldus.aws.routes.kinesis

import akka.http.scaladsl.server.Route
import com.knoldus.aws.services.kinesis.BankAccountEventGenerator
import akka.http.scaladsl.server.Directives._
import com.knoldus.aws.models.kinesis.BankAccountCreationEventRequest
import com.knoldus.aws.utils.JsonSupport
import com.typesafe.scalalogging.LazyLogging

class BankAccountEventGeneratorRoutes(bankAccountEventGenerator: BankAccountEventGenerator)
    extends JsonSupport
    with LazyLogging {
  val routes: Route = createBankAccountEvent ~ creditBankAccountEvent ~ debitBankAccountEvent

  def createBankAccountEvent: Route =
    path("event/bank-account" / "create") {
      pathEnd {
        (post & entity(as[BankAccountCreationEventRequest])) { bankAccountCreationRequest =>
          logger.info("Making event request for bank account creation")
          val response = bankAccountEventGenerator.createBankAccountEvent(bankAccountCreationRequest)
          complete(response)
        }
      }
    }

  def creditBankAccountEvent: Route = ???

  def debitBankAccountEvent: Route = ???
}
