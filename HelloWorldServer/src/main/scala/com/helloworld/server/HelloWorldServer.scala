package com.helloworld.server

import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.{ AuthorizationFailedRejection, RejectionHandler, Route }
import akka.stream.ActorMaterializer

import scala.concurrent.duration.Duration
import scala.concurrent.Await

object HelloWorldServer extends App with PersonRoutes with CorsHandler {

  implicit def rejectionHandler: RejectionHandler = RejectionHandler.newBuilder().handle {
    case AuthorizationFailedRejection => complete(StatusCodes.Unauthorized -> None)
  }.result().mapRejectionResponse(addCORSHeaders)

  implicit val system: ActorSystem = ActorSystem("akkaHttpServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val personRegistryActor: ActorRef = system.actorOf(PersonRegistryActor.props, "personRegistryActor")

  lazy val routes: Route = corsHandler(personRoutes)

  Http().bindAndHandle(routes, "0.0.0.0", 9000)

  println(s"HelloWorldServer online at http://localhost:9000/")

  Await.result(system.whenTerminated, Duration.Inf)
}
