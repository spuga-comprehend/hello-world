package com.helloworld.server

import akka.actor.ActorRef
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

class PersonRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest with PersonRoutes {

  override val personRegistryActor: ActorRef = system.actorOf(PersonRegistryActor.props, "personRegistry")

  lazy val routes = personRoutes

  //#testing-get
  "personRoutes" should {
    "GET /hello)" in {

      val request = HttpRequest(uri = "/hello")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)

        contentType should ===(ContentTypes.`application/json`)

        entityAs[String] should ===("""{"message":"Hello world, what's your name ?"}""")
      }
    }

    //#testing-post
    "POST /hello" in {
      val person = Person("Sergei")
      val personEntity = Marshal(person).to[MessageEntity].futureValue

      // using the RequestBuilding DSL:
      val request = Post("/hello").withEntity(personEntity)

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)

        contentType should ===(ContentTypes.`application/json`)

        entityAs[String] should ===("""{"message":"Welcome to the system, Sergei!"}""")
      }
    }
  }
}
