package com.knoldus.aws.bootstrap

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import com.knoldus.aws.routes.dynamodb.QuestionAPIImpl
import com.knoldus.aws.routes.kinesis.BankAccountEventGeneratorRoutes

class RoutesInstantiator(
  services: ServiceInstantiator
) {

  private val questionAPIRoutes =
    new QuestionAPIImpl(services.questionService)

  private val bankAccountEventRoutes = new BankAccountEventGeneratorRoutes(services.bankAccountEventGeneratorService)

  val routes: Route = cors(CorsSettings.defaultSettings) {
    concat(
      questionAPIRoutes.routes,
      bankAccountEventRoutes.routes
    )
  }
}
