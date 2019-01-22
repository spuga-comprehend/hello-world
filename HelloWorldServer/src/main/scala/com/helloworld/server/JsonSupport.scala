package com.helloworld.server

import com.helloworld.server.PersonRegistryActor.{ActionPerformed}

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val personJsonFormat = jsonFormat1(Person)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
