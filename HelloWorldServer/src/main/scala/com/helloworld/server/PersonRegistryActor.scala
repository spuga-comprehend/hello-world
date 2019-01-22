package com.helloworld.server

import akka.actor.{ Actor, ActorLogging, Props }

final case class Person(name: String)

object PersonRegistryActor {

  final case class ActionPerformed(message: String)

  final case class CreatePerson(person: Person)

  def props: Props = Props[PersonRegistryActor]
}

class PersonRegistryActor extends Actor with ActorLogging {
  import PersonRegistryActor._

  var persons = Set.empty[Person]

  def receive: Receive = {
    case CreatePerson(person) =>
      persons += person
      sender() ! ActionPerformed(s"Welcome to the system, ${person.name}!")
  }
}