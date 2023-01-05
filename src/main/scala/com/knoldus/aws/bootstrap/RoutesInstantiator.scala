package com.knoldus.aws.bootstrap

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import com.knoldus.aws.routes.dynamodb.QuestionAPIImpl
import com.knoldus.aws.routes.s3.S3BucketAPIImpl

class RoutesInstantiator(
  services: ServiceInstantiator
) {

//  private val questionAPIRoutes =
//    new QuestionAPIImpl(services.questionService)

  private val s3BucketRoutes =
    new S3BucketAPIImpl(services.s3BucketService)

  val routes: Route = cors(CorsSettings.defaultSettings) {
    concat(
      //questionAPIRoutes.routes,
      s3BucketRoutes.routes
    )
  }
}
