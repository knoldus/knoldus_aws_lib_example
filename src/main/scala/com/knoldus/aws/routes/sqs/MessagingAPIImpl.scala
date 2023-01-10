package com.knoldus.aws.routes.sqs

import akka.http.scaladsl.server.Route
import com.knoldus.aws.utils.JsonSupport
import com.typesafe.scalalogging.LazyLogging
import akka.http.scaladsl.server.Directives._

class MessagingAPIImpl extends MessagingAPI with JsonSupport with LazyLogging {

  val routes: Route =
    createQueue ~ listQueues ~ deleteQueue() ~ sendMsgToFIFOQueue ~ sendMsgToStandardQueue ~
        sendMultipleMsgToFIFOQueue ~ sendMultipleMsgToStandardQueue ~ receiveMessage ~
        receiveMultipleMessages ~ deleteMessage() ~ deleteMultipleMessages()

  override def createQueue: Route = ???

  override def listQueues: Route = ???

  override def deleteQueue(): Route = ???

  override def sendMsgToFIFOQueue: Route = ???

  override def sendMsgToStandardQueue: Route = ???

  override def sendMultipleMsgToFIFOQueue: Route = ???

  override def sendMultipleMsgToStandardQueue: Route = ???

  override def receiveMessage: Route = ???

  override def receiveMultipleMessages: Route = ???

  override def deleteMessage(): Route = ???

  override def deleteMultipleMessages(): Route = ???
}
