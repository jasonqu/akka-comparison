package com.oracle.sec5.guarded_blocks

import akka.actor.{ ActorRef, ActorSystem, Props, Actor }

case object Start
case object Produce
case object Consumed
case object Done
case class Payload(val msg : String)

class Master extends Actor {
  val producer = context.actorOf(Props[ProducerActor], "producer")
  val consumer = context.actorOf(Props[ConsumerActor], "consumer")
  def receive = {
    case Start | Consumed =>
      producer ! Produce
    case x : Payload =>
      consumer ! x
    case Done =>
      println("Done")// do nothing
  }
}

class ProducerActor extends Actor {
  val importantInfo = Array("Mares eat oats", "Does eat oats",
    "Little lambs eat ivy", "A kid will eat ivy too");
  var index = 0

  def receive = {
    case Produce =>
      Thread.sleep(util.Random.nextInt(1000)) // should not block in actor
      if (index < importantInfo.length) {
        sender ! Payload(importantInfo(index))
        index += 1
      } else {
        sender ! Done
      }
  }
}

class ConsumerActor extends Actor {
  def receive = {
    case Payload(msg) =>
      println(s"MESSAGE RECEIVED: $msg");
      Thread.sleep(util.Random.nextInt(1000)) // should not block in actor
      sender ! Consumed
  }
}

object ProducerConsumerExampleInAkka extends App {
  val system = ActorSystem("ProducerConsumerExampleInAkka")
  val master = system.actorOf(Props[Master], "master")
  master ! Start
}