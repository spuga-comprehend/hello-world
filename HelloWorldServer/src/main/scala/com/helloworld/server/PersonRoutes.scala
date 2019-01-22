package com.helloworld.server

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging

import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.MethodDirectives.post
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path

import scala.concurrent.Future
import com.helloworld.server.PersonRegistryActor._
import akka.pattern.ask
import akka.util.Timeout

trait PersonRoutes extends JsonSupport {

  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[PersonRoutes])

  def personRegistryActor: ActorRef

  implicit lazy val timeout = Timeout(5.seconds)

  lazy val personRoutes: Route =
    path("hello") {
      get {
        complete((StatusCodes.OK, ActionPerformed("Hello world, what's your name ?")))
      } ~
      post {
        entity(as[Person]) { person =>
          val personCreated: Future[ActionPerformed] =
            (personRegistryActor ? CreatePerson(person)).mapTo[ActionPerformed]
          onSuccess(personCreated) { performed =>
            log.info("[{}]: {}", person.name, performed.message)
            complete((StatusCodes.OK, performed))
          }
        }
      }
    }
}
