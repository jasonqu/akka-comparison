package com.oracle.sec2.thread_objects

import akka.actor.{ ActorRef, ActorSystem, Props,
 Actor, Inbox, ReceiveTimeout, Terminated, Cancellable}
import scala.concurrent.duration._
import akka.dispatch.Foreach

class SimpleMessageLoop extends Actor {
  val importantInfo = Array("Mares eat oats", "Does eat oats",
    "Little lambs eat ivy", "A kid will eat ivy too");
  var index = 0
  
  def receive = {
    case Print =>
      if(index < importantInfo.length) {
        println(self.path + " " + importantInfo(index))
        index += 1
      } else {
        sender ! Done
      }
  }
}

class SimpleMessageMainActor extends Actor {
  val loop = context.actorOf(Props[SimpleMessageLoop], "loop")
  var cancellable : Option[Cancellable] = None
  def receive = {
    case Begin =>
      import context.dispatcher
      cancellable = Some(context.system.scheduler.schedule(0.seconds, 4.seconds) {
        loop ! Print
      })
    case Done =>
      cancellable.foreach(_.cancel)
  }
}

object SimpleMessagesInAkka extends App {
  val system = ActorSystem("SimpleMessagesInAkka")
  val main = system.actorOf(Props[SimpleMessageMainActor], "main")
  main ! Begin
}
