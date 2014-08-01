package com.oracle.sec2.thread_objects

import akka.actor.{ ActorRef, ActorSystem, Props,
 Actor, Inbox, ReceiveTimeout, Terminated, Cancellable}
import scala.concurrent.duration._
import akka.dispatch.Foreach

class SimpleMessageLoop2 extends Actor {
  val importantInfo = Array("Mares eat oats", "Does eat oats",
    "Little lambs eat ivy", "A kid will eat ivy too");
  var index = 0
  var cancellable : Option[Cancellable] = None

  def receive = {
    case Begin =>
      import context.dispatcher
      cancellable = Some(context.system.scheduler.schedule(0.seconds, 1.seconds) {
        self ! Print
      })
    case Print =>
      if(index < importantInfo.length) {
        println(self.path + " " + System.currentTimeMillis() + " " + importantInfo(index))
        index += 1
        if (index == importantInfo.length) {
          cancellable.foreach(_.cancel)
          println(self.path + " " + System.currentTimeMillis() + " done")
        }
      }
  }
}

object SimpleMessagesInAkka2 extends App {
  val system = ActorSystem("SimpleMessagesInAkka")
  val loop = system.actorOf(Props[SimpleMessageLoop2], "loop")
  loop! Begin
}
