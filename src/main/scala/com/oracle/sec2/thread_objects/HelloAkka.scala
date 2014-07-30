package com.oracle.sec2.thread_objects

import akka.actor.{ ActorRef, ActorSystem, Props, Actor }

case object Greet

class Greeter extends Actor {
  def receive = {
    case Greet => println("Hello from an actor!")
  }
}

object HelloAkka extends App {
  val system = ActorSystem("helloakka")
  val greeter = system.actorOf(Props[Greeter], "greeter")
  greeter.tell(Greet, ActorRef.noSender)
}