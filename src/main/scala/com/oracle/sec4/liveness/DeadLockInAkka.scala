package com.oracle.sec4.liveness

import akka.actor.{ ActorRef, ActorSystem, Props, Actor }

case object Bow
case object BowBack

class Friend extends Actor {
  def receive = {
    case Bow =>
      println(s"${self.path.name} : ${sender.path.name} has bowed to me!")
      sender ! BowBack
    case BowBack =>
      println(s"${self.path.name} : ${sender.path.name} has bowed back to me!")
  }
}

object DeadLockInAkka extends App {
  val system = ActorSystem("DeadLockInAkka")
  val alphonse = system.actorOf(Props[Friend], "Alphonse")
  val gaston   = system.actorOf(Props[Friend], "Gaston")
  alphonse.tell(Bow, gaston)
  gaston.tell(Bow, alphonse)
}