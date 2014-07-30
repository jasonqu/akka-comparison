package com.oracle.sec2.thread_objects

import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }

case object Print

class MessageLoop extends Actor {
  val importantInfo = Array("Mares eat oats", "Does eat oats",
    "Little lambs eat ivy", "A kid will eat ivy too");
  var index = 0

  
  
  
  def receive = {
    case Print if index == 4 => 
      println(importantInfo(index))
      index += 1
    case Print => 
      println(importantInfo(index))
      index += 1
  }
}

object SimpleThreadsInAkka extends App {
  val system = ActorSystem("helloakka")
  val greeter = system.actorOf(Props[Greeter], "greeter")
  greeter.tell(Greet, ActorRef.noSender)
}