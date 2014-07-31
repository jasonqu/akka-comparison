package com.oracle.sec2.thread_objects

import akka.actor.{ ActorRef, ActorSystem, Props, Actor,
  Inbox, ReceiveTimeout, Terminated, Cancellable}
import scala.concurrent.duration._

case object Begin
case object Print
case object Interrupt
case object Done

class MessageLoop extends Actor {
  val importantInfo = Array("Mares eat oats", "Does eat oats",
    "Little lambs eat ivy", "A kid will eat ivy too");
  var index = 0
  
  def receive = {
    case Print if index == 4 => 
      sender ! Done
    case Print => 
      println(self.path + " " + importantInfo(index))
      index += 1
    case Interrupt =>
      println(self.path + " I wasn't done!")
  }
}

class MainActor(val timeoutInSeconds : Int) extends Actor {
  //val messageLoop = context.actorOf(
  val loop = context.actorOf(Props[MessageLoop], "loop")
  var cancellables : List[Cancellable] = Nil
  def receive = {
    case Begin =>
      import context.dispatcher
      context.system.scheduler.scheduleOnce(timeoutInSeconds.seconds, self, ReceiveTimeout)
      cancellables = context.system.scheduler.schedule(0.seconds, 4.seconds) {
        loop ! Print
      } :: cancellables
      cancellables = context.system.scheduler.schedule(0.seconds, 1.seconds) {
        self ! Print
      } :: cancellables
    case Print =>
      println(self.path + " Still waiting...")
    case ReceiveTimeout =>
      println(self.path + " Tired of waiting!")
      loop ! Interrupt
      cancellables.foreach(_.cancel)
      println(self.path + " Finally!")
    case Done =>
      println(self.path + " Finally!")
  }
}

object SimpleThreadsInAkka extends App {
  val system = ActorSystem("SimpleThreadsInAkka")
  val main = system.actorOf(Props(new MainActor(6)), "main")
  main ! Begin
}
